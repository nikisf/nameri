package com.softuni.service;

import com.softuni.exception.EmailAlreadyExistsException;
import com.softuni.exception.UserAlreadyExistsException;
import com.softuni.model.entity.Role;
import com.softuni.model.entity.User;
import com.softuni.model.service.RoleServiceModel;
import com.softuni.model.service.UserServiceModel;
import com.softuni.repository.RoleRepository;
import com.softuni.repository.UserRepository;
import com.softuni.service.impl.RoleServiceImpl;
import com.softuni.service.impl.UserServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private User user;
    private Role role;
    @Mock
    private UserRepository userRepository;
    private ModelMapper modelMapper;
    @Mock
    private ModelMapper fakeModelMapper;
    private BCryptPasswordEncoder encoder;

    @Mock
    private RoleService roleService;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private LogService logService;
    UserService userService;
    @Mock
    UserServiceModel userModel;
    Authentication authToken;



    @BeforeEach
    public void setupTest() {
        this.modelMapper = new ModelMapper();
        encoder = new BCryptPasswordEncoder();
        user = new User();
        user.setUsername("name");
        user.setEmail("email@email.bg");
        user.setPassword("1");
        user.setId("test");
        role = new Role();
        Role role1 = new Role();
        role.setAuthority("USER");
        role.setId("testRole");
        role1.setAuthority("ROLE_ADMIN");
        user.setAuthorities(Set.of(role, role1));
        userModel = new UserServiceModel();
        roleService = new RoleServiceImpl(roleRepository, modelMapper);
        userService = new UserServiceImpl(this.userRepository, modelMapper, encoder, roleService, logService);


    }


    @Test
    public void getUserByUsername_ShouldReturnUser() {
        Mockito.when(this.userRepository.findByUsername("name")).thenReturn(Optional.ofNullable(this.user));

        User expected = this.user;
        User actual = this.modelMapper.map(userService.findByUsername("name"), User.class);
        Assert.assertEquals(expected.getUsername(), actual.getUsername());
        Assert.assertEquals(expected.getEmail(), actual.getEmail());
        Assert.assertEquals(expected.getPassword(), actual.getPassword());
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getAuthorities().size(), actual.getAuthorities().size());
    }

    @Test
    public void getUserById_ShouldReturnUser() {
        Mockito.when(this.userRepository.findById("test")).thenReturn(Optional.ofNullable(this.user));
        User expected = this.user;
        User actual = this.modelMapper.map(userService.findById("test"), User.class);

        Assert.assertEquals(expected.getUsername(), actual.getUsername());
        Assert.assertEquals(expected.getEmail(), actual.getEmail());
        Assert.assertEquals(expected.getPassword(), actual.getPassword());
        Assert.assertEquals(expected.getId(), actual.getId());
    }


    @Test
    public void shouldReturnAllUsers() {
        Mockito.when(this.userRepository.findAll()).thenReturn(List.of(user));

        List<User> expected = List.of(user);

        List<User> actual = userService.findAllUsers();
        Assert.assertEquals(expected.get(0).getId(), actual.get(0).getId());
        Assert.assertEquals(expected.get(0).getPassword(), actual.get(0).getPassword());
        Assert.assertEquals(expected.get(0).getEmail(), actual.get(0).getEmail());
        Assert.assertEquals(expected.get(0).getUsername(), actual.get(0).getUsername());
    }


    @Test
    public void shouldThrowExceptionWhenUsernameIsInvalid() {
        String username = "invalid";
        Mockito.when(userRepository.findByUsername(username)).thenThrow(UsernameNotFoundException.class);
        Assert.assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(username));
    }

    @Test
    public void registerUserSuccess() {
        userModel.setUsername("name1");
        userModel.setEmail("email1@email.bg");
        userModel.setId("tt1t4");
        userModel.setPassword("222");
        RoleServiceModel role = new RoleServiceModel();
        role.setAuthority("ROLE_USER");
        role.setId("testRole");

        when(this.userRepository.saveAndFlush(Mockito.any(User.class)))
                .thenReturn(new User());

        this.userService.registerUser(userModel);

        Mockito.verify(this.userRepository, times(1)).saveAndFlush(Mockito.any(User.class));
    }

    @Test
    public void ShouldThrowExceptionIfUsernameAlreadyExists() {
        userModel.setUsername("nam442342e");
        when(this.userRepository.findByUsername(userModel.getUsername())).thenReturn(Optional.of(Mockito.mock(User.class)));
        Assert.assertThrows(UserAlreadyExistsException.class, () -> this.userService.registerUser(userModel));
    }

    @Test
    public void ShouldThrowExceptionIfEmailAlreadyExists() {
        userModel.setEmail("email1@email.bg");
        when(this.userRepository.findByEmail(userModel.getEmail())).thenReturn(Optional.of(Mockito.mock(User.class)));
        Assert.assertThrows(EmailAlreadyExistsException.class, () -> this.userService.registerUser(userModel));
    }

    @Test
    public void ShouldThrowExceptionIfUserIsNotAdmin() {
        role.setAuthority("ROLE_ADMIN");
        authToken = new TestingAuthenticationToken(user.getUsername(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authToken);
        Assert.assertThrows(AccessDeniedException.class, () -> this.userService.setRoleToUser("test", "ROLE_ADMIN"));
    }

    @Test
    public void ShouldThrowExceptionIfAdminTryToChangeHisPermission() {
        authToken = new TestingAuthenticationToken(user.getUsername(), user.getPassword(), "ROLE_ADMIN");
        SecurityContextHolder.getContext().setAuthentication(authToken);

        when(this.userRepository.findById(user.getId())).thenReturn(Optional.ofNullable(user));
        Assert.assertThrows(IllegalArgumentException.class, () -> this.userService.setRoleToUser(user.getId(), "ROLE_ADMIN"));
    }

    @Test
    public void shouldSetUserRoleToUser(){
        User admin = new User();
        admin.setId("123");
        authToken = new TestingAuthenticationToken(admin.getUsername(), admin.getPassword(), "ROLE_ADMIN");
        SecurityContextHolder.getContext().setAuthentication(authToken);
        when(this.userRepository.findById(user.getId())).thenReturn(Optional.ofNullable(this.user));
        when(this.roleRepository.findByAuthority("ROLE_USER")).thenReturn(role);
        
        when(this.roleService.findByAuthority("ROLE_USER")).thenReturn(new RoleServiceModel());
        userService.setRoleToUser(user.getId(), "user");
       int size = user.getAuthorities().size();
        System.out.println();
        Assert.assertEquals(user.getAuthorities().size(), 2);






    }


}
