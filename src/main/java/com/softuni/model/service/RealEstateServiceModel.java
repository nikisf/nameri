package com.softuni.model.service;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import java.math.BigDecimal;

import static com.softuni.Constants.Constants.*;

public class RealEstateServiceModel extends BaseOfferServiceModel {

    private String username;
    private String realEstateType;
    private Double area;
    private String imageUrl;
    private BigDecimal price;

    public RealEstateServiceModel() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotNull(message = NULL_REAL_ESTATE_TYPE_MESSAGE)
    public String getRealEstateType() {
        return realEstateType;
    }

    public void setRealEstateType(String realEstateType) {
        this.realEstateType = realEstateType;
    }

    @Positive(message = VALUE_MUST_BE_POSITIVE)
    @NotNull(message = NULL_REAL_ESTATE_AREA_MESSAGE)
    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Positive(message = VALUE_MUST_BE_POSITIVE)
    @NotNull(message = NULL_REAL_ESTATE_PRICE_MESSAGE)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
