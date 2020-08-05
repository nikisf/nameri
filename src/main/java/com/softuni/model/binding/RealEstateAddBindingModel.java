package com.softuni.model.binding;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import java.math.BigDecimal;

import static com.softuni.Constants.Constants.*;

public class RealEstateAddBindingModel extends BaseOfferBindingModel {
    private String realEstateType;
    private Double area;
    private MultipartFile image;
    private BigDecimal price;

    public RealEstateAddBindingModel() {
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
    @NotNull(message = NULL_REAL_ESTATE_IMAGE_MESSAGE)
    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
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
