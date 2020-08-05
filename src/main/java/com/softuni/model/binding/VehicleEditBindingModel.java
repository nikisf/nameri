package com.softuni.model.binding;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

import static com.softuni.Constants.Constants.*;
import static com.softuni.Constants.Constants.NULL_VEHICLE_MODEL_MESSAGE;

public class VehicleEditBindingModel extends BaseOfferBindingModel {


    private String brand;
    private String model;
    private int year;
    private String engine;
    private String transmission;
    private BigDecimal price;

    public VehicleEditBindingModel() {
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

    @Positive(message = VALUE_MUST_BE_POSITIVE)
    @NotNull(message = NULL_VEHICLE_PRICE_MESSAGE)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }


}
