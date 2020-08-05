package com.softuni.model.binding;


import javax.validation.constraints.Positive;

import java.math.BigDecimal;

import static com.softuni.Constants.Constants.VALUE_MUST_BE_POSITIVE;

public class RealEstateEditBindingModel extends BaseOfferBindingModel {

    private String realEstateType;
    private Double area;
    private BigDecimal price;


    public RealEstateEditBindingModel() {
    }

    public String getRealEstateType() {
        return realEstateType;
    }

    public void setRealEstateType(String realEstateType) {
        this.realEstateType = realEstateType;
    }

    @Positive(message = VALUE_MUST_BE_POSITIVE)
    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    @Positive(message = VALUE_MUST_BE_POSITIVE)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
