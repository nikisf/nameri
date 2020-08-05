package com.softuni.service;

import com.softuni.model.entity.User;
import com.softuni.model.service.UserServiceModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.time.LocalDateTime;
import java.util.List;

public interface UserService extends UserDetailsService {

    void registerUser(UserServiceModel userServiceModel);

    UserServiceModel findByUsername(String name);

    List<User> findAllUsers();

    UserServiceModel editUserProfile(UserServiceModel userServiceModel, String password);


    void setRoleToUser(String id, String role);

    UserServiceModel findById(String id);

     void checkIfUsernameAndEmailAreFree(String username, String email);

     void saveLog(String user, String description, LocalDateTime time);
}
