package com.softuni.model.view;

import com.softuni.model.entity.User;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class BaseOfferViewModel extends BaseViewModel {

    private String title;
    private String description;
    private LocalDate addedOn;
    private String region;
    private String phoneNumber;

    public BaseOfferViewModel() {
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
}
