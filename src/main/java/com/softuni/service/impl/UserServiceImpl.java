package com.softuni.service.impl;

import com.softuni.exception.EmailAlreadyExistsException;
import com.softuni.exception.UserAlreadyExistsException;
import com.softuni.exception.UserNotFoundException;
import com.softuni.model.entity.User;
import com.softuni.model.service.LogServiceModel;
import com.softuni.model.service.UserServiceModel;
import com.softuni.repository.UserRepository;
import com.softuni.service.LogService;
import com.softuni.service.RoleService;
import com.softuni.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.List;

import static com.softuni.Constants.Constants.*;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder encoder;
    private final RoleService roleService;
    private final LogService logService;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, BCryptPasswordEncoder encoder, RoleService roleService, LogService logService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.encoder = encoder;
        this.roleService = roleService;
        this.logService = logService;
    }


    @Override
    public void registerUser(UserServiceModel userServiceModel) {
        this.roleService.seedRolesInDb();
        if (this.userRepository.count() == 0){
            userServiceModel.setAuthorities(this.roleService.findAllRoles());
        } else {
            userServiceModel.setAuthorities(new LinkedHashSet<>());
            userServiceModel.getAuthorities().add(this.roleService.findByAuthority("ROLE_USER"));
        }
        User user = this.modelMapper.map(userServiceModel, User.class);
        checkIfUsernameAndEmailAreFree(user.getUsername(), user.getEmail());
        this.saveLog(user.getUsername(), "Registered", LocalDateTime.now());
        user.setPassword(this.encoder.encode(userServiceModel.getPassword()));
            this.userRepository.saveAndFlush(user);

    }

    @Override
    public UserServiceModel findByUsername(String name) {
        User user = this.userRepository.findByUsername(name).orElseThrow(() -> new UsernameNotFoundException("Username not found!"));
        return this.modelMapper.map(user, UserServiceModel.class);

    }

    @Override
    public List<User> findAllUsers() {
        return this.userRepository.findAll();
    }

    @Override
    public UserServiceModel editUserProfile(UserServiceModel userServiceModel, String password) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!auth.getName().equals(userServiceModel.getUsername())){
            throw new AccessDeniedException(ACCESS_DENIED);
        }
        User user = this.userRepository.findByUsername(userServiceModel.getUsername()).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
        if (!this.encoder.matches(password, user.getPassword())){
            throw new IllegalArgumentException("Password is incorrect!");
        }
        user.setPassword(this.encoder.encode(userServiceModel.getPassword()));
        user.setEmail(userServiceModel.getEmail());
        this.saveLog(user.getUsername(), "Change profile", LocalDateTime.now());
        return this.modelMapper.map(this.userRepository.saveAndFlush(user), UserServiceModel.class);
    }


    @Override
    public void setRoleToUser(String id, String role) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))){
            throw new AccessDeniedException(ACCESS_DENIED);
        }
        User user = this.userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
        UserServiceModel userServiceModel = this.modelMapper.map(user, UserServiceModel.class);
        if (userServiceModel.getUsername().equals(auth.getName())){
            throw new IllegalArgumentException("You can't change your permission!");
        }
        userServiceModel.getAuthorities().clear();
        if (role.equals("user")){
            userServiceModel.getAuthorities().add(this.roleService.findByAuthority("ROLE_USER"));
            this.saveLog(userServiceModel.getUsername(), "set User role", LocalDateTime.now());
        } else if (role.equals("moderator")){
            userServiceModel.getAuthorities().add(this.roleService.findByAuthority("ROLE_USER"));
            userServiceModel.getAuthorities().add(this.roleService.findByAuthority("ROLE_MODERATOR"));
            this.saveLog(userServiceModel.getUsername(), "set Moderator role", LocalDateTime.now());
        } else if(role.equals("admin")){
            userServiceModel.getAuthorities().add(this.roleService.findByAuthority("ROLE_USER"));
            userServiceModel.getAuthorities().add(this.roleService.findByAuthority("ROLE_MODERATOR"));
            userServiceModel.getAuthorities().add(this.roleService.findByAuthority("ROLE_ADMIN"));
            this.saveLog(userServiceModel.getUsername(), "set Admin role", LocalDateTime.now());
        }
        System.out.println();
        this.userRepository.saveAndFlush(this.modelMapper.map(userServiceModel, User.class));
    }

    @Override
    public UserServiceModel findById(String id) {
        User user = this.userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
        return this.modelMapper.map(user,UserServiceModel.class);
    }

    @Override
    public void checkIfUsernameAndEmailAreFree(String username, String email) {
        User user = this.userRepository.findByUsername(username).orElse(null);
        if (user != null){
            throw new UserAlreadyExistsException(USER_ALREADY_EXISTS);
        }
        User findUserByMail = this.userRepository.findByEmail(email).orElse(null);
        if (findUserByMail != null){
            throw new EmailAlreadyExistsException(EMAIL_ALREADY_EXISTS);
        }
    }

    @Override
    public void saveLog(String user, String description, LocalDateTime time) {
        LogServiceModel logServiceModel = new LogServiceModel();
        logServiceModel.setUsername(user);
        logServiceModel.setDescription(description);
        logServiceModel.setTime(time);
        this.logService.saveInDb(logServiceModel);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(s).orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));
    }


}
