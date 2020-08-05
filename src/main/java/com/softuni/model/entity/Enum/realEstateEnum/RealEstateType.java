package com.softuni.model.entity.Enum.realEstateEnum;

import com.softuni.model.entity.Enum.vehicleEnum.Transmission;

import java.util.HashMap;
import java.util.Map;

public enum RealEstateType {
    OFFICE("Oфис"),
    SHOP("Магазин"),
    APARTMENT("Апартамент"),
    WAREHOUSE("Склад"),
    HOTEL("Хотел"),
    HOUSE("Къща"),
    GARAGE("Гараж"),
    OTHER("Друго");


    private String realEstateType;


    RealEstateType(String realEstateType) {
        this.realEstateType = realEstateType;
    }

    public String getRealEstateType() {
        return realEstateType;
    }

    private static final Map<String, RealEstateType> map;
    static {
        map = new HashMap<>();
        for (RealEstateType v : RealEstateType.values()) {
            map.put(v.realEstateType, v);
        }
    }
    public static RealEstateType findByString(String name) {
        return map.get(name);
    }
}
