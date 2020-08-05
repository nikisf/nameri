package com.softuni.model.response;

import java.math.BigDecimal;

public class RealEstateResponseModel extends BaseOfferResponseModel{
    private String realEstateType;
    private Double area;
    private String imageUrl;
    private BigDecimal price;

    public RealEstateResponseModel() {
    }

    public String getRealEstateType() {
        return realEstateType;
    }

    public void setRealEstateType(String realEstateType) {
        this.realEstateType = realEstateType;
    }

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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
