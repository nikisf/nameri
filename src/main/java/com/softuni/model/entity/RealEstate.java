package com.softuni.model.entity;

import com.softuni.model.entity.Enum.realEstateEnum.RealEstateType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import java.math.BigDecimal;

import static com.softuni.Constants.Constants.NULL_REAL_ESTATE_TYPE_MESSAGE;
import static com.softuni.Constants.Constants.VALUE_MUST_BE_POSITIVE;

@Entity
@Table(name = "real_estates")
public class RealEstate extends BaseOffer{

    private String username;
    private RealEstateType realEstateType;
    private Double area;
    private String imageUrl;
    private BigDecimal price;

    public RealEstate() {
    }

    @NotNull
    @Column(name="username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name="real_estate_type")
    @NotNull(message = NULL_REAL_ESTATE_TYPE_MESSAGE)
    @Enumerated(EnumType.STRING)
    public RealEstateType getRealEstateType() {
        return realEstateType;
    }

    public void setRealEstateType(RealEstateType realEstateType) {
        this.realEstateType = realEstateType;
    }

    @Column(name = "area", nullable = false)
    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
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
