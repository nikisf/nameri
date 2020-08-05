package com.softuni.model.service;

import javax.validation.constraints.*;
import java.math.BigDecimal;

import static com.softuni.Constants.Constants.*;
import static com.softuni.Constants.Constants.NULL_VEHICLE_MODEL_MESSAGE;

public class VehicleServiceModel extends BaseOfferServiceModel {

    private String username;
    private String brand;
    private String model;
    private int year;
    private String engine;
    private String transmission;
    private String imageUrl;
    private BigDecimal price;

    public VehicleServiceModel() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotEmpty(message = EMPTY_VEHICLE_BRAND_MESSAGE)
    @NotNull(message = NULL_VEHICLE_BRAND_MESSAGE)
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @NotEmpty(message = EMPTY_VEHICLE_MODEL_MESSAGE)
    @NotNull(message = NULL_VEHICLE_MODEL_MESSAGE)
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Min(1930)
    @Max(2020)
    @NotNull(message = NULL_VEHICLE_YEAR_MESSAGE)
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @NotNull(message = NULL_VEHICLE_ENGINE_MESSAGE)
    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    @NotNull(message = NULL_VEHICLE_TRANSMISSION_MESSAGE)
    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    @Positive(message = VALUE_MUST_BE_POSITIVE)
    @NotNull(message = NULL_VEHICLE_PRICE_MESSAGE)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
