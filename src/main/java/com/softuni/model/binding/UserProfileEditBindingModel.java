package com.softuni.model.binding;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import static com.softuni.Constants.Constants.*;
import static com.softuni.Constants.Constants.EMAIL_EMPTY_MESSAGE;

public class UserProfileEditBindingModel extends BaseBindingModel{
    private String username;
    private String oldPassword;
    private String password;
    private String confirmPassword;
    private String email;

    public UserProfileEditBindingModel() {
    }

    @Length(min = 2, max = 20, message = INVALID_USERNAME_LENGTH_MESSAGE)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Length(min = 3, max = 20, message = INVALID_PASSWORD_LENGTH_MESSAGE)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    @Length(min = 3, max = 20, message = INVALID_PASSWORD_LENGTH_MESSAGE)
    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @NotNull(message = EMAIL_NULL_MESSAGE)
    @NotEmpty(message = EMAIL_EMPTY_MESSAGE)
    @Email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
