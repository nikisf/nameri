package com.softuni.model.entity;

import com.softuni.model.entity.Enum.vehicleEnum.Engine;
import com.softuni.model.entity.Enum.vehicleEnum.Transmission;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import java.math.BigDecimal;

import static com.softuni.Constants.Constants.*;

@Entity
@Table(name = "vehicles")
public class Vehicle extends BaseOffer {


    private String username;
    private String brand;
    private String model;
    private int year;
    private Engine engine;
    private Transmission transmission;
    private String imageUrl;
    private BigDecimal price;


    public Vehicle() {
    }

    @NotNull
    @Column(name="username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "brand", nullable = false)
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Column(name = "model", nullable = false)
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Column(name = "year", nullable = false)
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Column(name = "engine", nullable = false)
    @NotNull(message = NULL_VEHICLE_ENGINE_MESSAGE)
    @Enumerated(EnumType.STRING)
    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }
    @Column(name = "transmission")
    @NotNull(message = NULL_VEHICLE_TRANSMISSION_MESSAGE)
    @Enumerated(EnumType.STRING)
    public Transmission getTransmission() {
        return transmission;
    }

    public void setTransmission(Transmission transmission) {
        this.transmission = transmission;
    }


    @Column(name = "image_url", nullable = false)
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Positive(message = VALUE_MUST_BE_POSITIVE)
    @Column(name = "price", nullable = false)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
