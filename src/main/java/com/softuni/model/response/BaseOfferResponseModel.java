package com.softuni.model.response;

import java.time.LocalDate;

public class BaseOfferResponseModel extends BaseResponseModel {
    private String title;
    private String description;
    private LocalDate addedOn;
    private LocalDate expireOn;
    private String region;
    private String phoneNumber;

    public BaseOfferResponseModel() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRegion() {
        return region;
    }

    public LocalDate getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(LocalDate addedOn) {
        this.addedOn = addedOn;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getExpireOn() {
        return expireOn;
    }

    public void setExpireOn(LocalDate expireOn) {
        this.expireOn = expireOn;
    }
}
