package com.softuni.model.entity.Enum.vehicleEnum;

import com.softuni.model.entity.Enum.Region;

import java.util.HashMap;
import java.util.Map;

public enum Transmission {

    Manual("Ръчна"),
    Automatic("Автоматична"),
    SemiAutomatic("Полуавтоматична");


    private String transmission;


    Transmission(String transmission) {
        this.transmission = transmission;
    }

    public String getTransmission() {
        return transmission;
    }


    private static final Map<String, Transmission> map;
    static {
        map = new HashMap<>();
        for (Transmission v : Transmission.values()) {
            map.put(v.transmission, v);
        }
    }
    public static Transmission findByString(String name) {
        return map.get(name);
    }
}
