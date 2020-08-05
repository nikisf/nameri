package com.softuni.model.service;

import org.hibernate.validator.constraints.Length;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

import static com.softuni.Constants.Constants.*;

public class BaseOfferServiceModel extends BaseServiceModel {

    private String title;
    private String description;
    private String region;
    private String phoneNumber;
    private LocalDate addedOn;
    private LocalDate expireOn;
    private UserServiceModel user;
    private boolean isActive;

    public BaseOfferServiceModel() {
    }



    @Length(min = 3, max = 30, message = LENGTH_VEHICLE_TITLE_MESSAGE)
    @NotNull(message = NULL_VEHICLE_TITLE_MESSAGE)
    @NotEmpty(message = EMPTY_VEHICLE_TITLE_MESSAGE)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @NotNull(message = NULL_BASE_DESCRIPTION_MESSAGE)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotNull(message = NULL_BASE_REGION_MESSAGE)
    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @NotNull(message = NULL_BASE_PHONE_NUMBER_MESSAGE)
    @NotEmpty(message = EMPTY_BASE_PHONE_NUMBER_MESSAGE)
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(LocalDate addedOn) {
        this.addedOn = addedOn;
    }

    public LocalDate getExpireOn() {
        return expireOn;
    }

    public void setExpireOn(LocalDate expireOn) {
        this.expireOn = expireOn;
    }

    public UserServiceModel getUser() {
        return user;
    }

    public void setUser(UserServiceModel user) {
        this.user = user;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
