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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    User testUser;
    User admin;
    Role roleUser;
     Role roleAdmin;
     Role roleMod;
    @Mock
    UserRepository userRepository;
    ModelMapper modelMapper;
    @Mock
    ModelMapper fakeModelMapper;
    BCryptPasswordEncoder encoder;

    @Mock
    RoleService roleService;
    @Mock
    RoleRepository roleRepository;
    @Mock
    LogService logService;
    UserService userService;
    @Mock
    UserServiceModel userModel;
    Authentication authToken;



    @BeforeEach
    public void setupTest() {
        this.modelMapper = new ModelMapper();
        encoder = new BCryptPasswordEncoder();
        roleUser = new Role();
        roleAdmin = new Role();
        admin = new User();
        roleMod = new Role();
        testUser = new User();
        testUser.setUsername("name");
        testUser.setEmail("email@email.bg");
        testUser.setPassword("1");
        testUser.setId("test");
        this.roleUser.setAuthority("ROLE_USER");

        roleMod.setAuthority("ROLE_MODERATOR");
        roleUser.setId("testRole");
        roleAdmin.setAuthority("ROLE_ADMIN");
        testUser.setAuthorities(Set.of(roleUser, roleAdmin));
        userModel = new UserServiceModel();
        roleService = new RoleServiceImpl(roleRepository, modelMapper);
        userService = new UserServiceImpl(this.userRepository, modelMapper, encoder, roleService, logService);


    }


    @Test
    public void getUserByUsername_ShouldReturnUser() {
        Mockito.when(this.userRepository.findByUsername("name")).thenReturn(Optional.ofNullable(this.testUser));

        User expected = this.testUser;
        User actual = this.modelMapper.map(userService.findByUsername("name"), User.class);
        Assert.assertEquals(expected.getUsername(), actual.getUsername());
        Assert.assertEquals(expected.getEmail(), actual.getEmail());
        Assert.assertEquals(expected.getPassword(), actual.getPassword());
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getAuthorities().size(), actual.getAuthorities().size());
    }

    @Test
    public void getUserById_ShouldReturnUser() {
        Mockito.when(this.userRepository.findById("test")).thenReturn(Optional.ofNullable(this.testUser));
        User expected = this.testUser;
        User actual = this.modelMapper.map(userService.findById("test"), User.class);

        Assert.assertEquals(expected.getUsername(), actual.getUsername());
        Assert.assertEquals(expected.getEmail(), actual.getEmail());
        Assert.assertEquals(expected.getPassword(), actual.getPassword());
        Assert.assertEquals(expected.getId(), actual.getId());
    }


    @Test
    public void shouldReturnAllUsers() {
        Mockito.when(this.userRepository.findAll()).thenReturn(List.of(testUser));

        List<User> expected = List.of(testUser);

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
        roleUser.setAuthority("ROLE_ADMIN");
        authToken = new TestingAuthenticationToken(testUser.getUsername(), testUser.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authToken);
        Assert.assertThrows(AccessDeniedException.class, () -> this.userService.setRoleToUser("test", "ROLE_ADMIN"));
    }

    @Test
    public void ShouldThrowExceptionIfAdminTryToChangeHisPermission() {
        authToken = new TestingAuthenticationToken(testUser.getUsername(), testUser.getPassword(), "ROLE_ADMIN");
        SecurityContextHolder.getContext().setAuthentication(authToken);

        when(this.userRepository.findById(testUser.getId())).thenReturn(Optional.ofNullable(testUser));
        Assert.assertThrows(IllegalArgumentException.class, () -> this.userService.setRoleToUser(testUser.getId(), "ROLE_ADMIN"));
    }

    @Test
    public void shouldSetAdminRoleToUser(){
        User admin = new User();
        admin.setId("123");
        authToken = new TestingAuthenticationToken(admin.getUsername(), admin.getPassword(), "ROLE_ADMIN");
        SecurityContextHolder.getContext().setAuthentication(authToken);
        when(this.userRepository.findById(testUser.getId())).thenReturn(Optional.ofNullable(this.testUser));
        when(this.roleRepository.findByAuthority(anyString())).thenReturn(new Role());
        User newUser = new User();
        newUser.setAuthorities(Set.of(roleUser, roleAdmin, roleMod));
        when(this.userRepository.saveAndFlush(Mockito.any(User.class)))
                .thenReturn(newUser);
        userService.setRoleToUser(testUser.getId(), "admin");
        Assert.assertEquals(3,newUser.getAuthorities().size());
    }

    @Test
    public void shouldSetModeratorRoleToUser(){
        User admin = new User();
        admin.setId("123");
        authToken = new TestingAuthenticationToken(admin.getUsername(), admin.getPassword(), "ROLE_ADMIN");
        SecurityContextHolder.getContext().setAuthentication(authToken);
        when(this.userRepository.findById(testUser.getId())).thenReturn(Optional.ofNullable(this.testUser));
        when(this.roleRepository.findByAuthority(anyString())).thenReturn(new Role());
        User newUser = new User();
        newUser.setAuthorities(Set.of(roleUser, roleMod));
        when(this.userRepository.saveAndFlush(Mockito.any(User.class)))
                .thenReturn(newUser);
        userService.setRoleToUser(testUser.getId(), "moderator");
        Assert.assertEquals(2,newUser.getAuthorities().size());
    }

    @Test
    public void shouldSetUserRoleToUser(){

        authToken = new TestingAuthenticationToken(admin.getUsername(), admin.getPassword(), "ROLE_ADMIN");
        SecurityContextHolder.getContext().setAuthentication(authToken);
        when(this.userRepository.findById(testUser.getId())).thenReturn(Optional.ofNullable(this.testUser));
        when(this.roleRepository.findByAuthority(anyString())).thenReturn(new Role());
        User newUser = new User();
        newUser.setAuthorities(Set.of(roleUser));
        when(this.userRepository.saveAndFlush(Mockito.any(User.class)))
                .thenReturn(newUser);
        userService.setRoleToUser(testUser.getId(), "user");
        Assert.assertEquals(1,newUser.getAuthorities().size());
    }

    @Test
    public void shouldThrowExceptionWhenYouTryToEditOtherProfile(){
        UserServiceModel user = new UserServiceModel();
        admin.setUsername("oes");
        user.setId("123");
        user.setUsername("asd");
        authToken = new TestingAuthenticationToken(admin.getUsername(), admin.getPassword(), "ROLE_ADMIN");
        SecurityContextHolder.getContext().setAuthentication(authToken);

        Assert.assertThrows(AccessDeniedException.class, () -> this.userService.editUserProfile(user, "pass"));
    }

    @Test
    public void shouldThrowExceptionWhenPasswordNotMatch(){
        UserServiceModel user = new UserServiceModel();
        user.setUsername("asd");
        admin.setUsername("asd");
        authToken = new TestingAuthenticationToken(admin.getUsername(), admin.getPassword(), "ROLE_ADMIN");
        SecurityContextHolder.getContext().setAuthentication(authToken);
        Mockito.when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(new User()));
        Assert.assertThrows(IllegalArgumentException.class, () -> this.userService.editUserProfile(user, "pass"));
    }


}
