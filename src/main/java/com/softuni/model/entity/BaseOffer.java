package com.softuni.model.entity;

import com.softuni.model.entity.Enum.Region;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

import static com.softuni.Constants.Constants.NULL_BASE_REGION_MESSAGE;


@MappedSuperclass
public abstract class BaseOffer extends BaseEntity {

    private String title;
    private String description;
    private Region region;
    private String phoneNumber;
    private LocalDate addedOn;
    private LocalDate expireOn;
    private User user;
    private boolean isActive;

    public BaseOffer() {
    }


    @Column(name = "title", nullable = false)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    @Column(name = "description", columnDefinition = "TEXT", nullable = false)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Column(name = "region")
    @NotNull(message = NULL_BASE_REGION_MESSAGE)
    @Enumerated(value = EnumType.STRING)
    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }


    @Column(name = "added_on", nullable = false)
    public LocalDate getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(LocalDate addedOn) {
        this.addedOn = addedOn;
    }

    @Column(name = "expire_on", nullable = false)
    public LocalDate getExpireOn() {
        return expireOn;
    }

    public void setExpireOn(LocalDate expireOn) {
        this.expireOn = expireOn;
    }

    @Column(name = "phone_number", nullable = false)
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @ManyToOne
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(name = "is_active", nullable = false)
    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
