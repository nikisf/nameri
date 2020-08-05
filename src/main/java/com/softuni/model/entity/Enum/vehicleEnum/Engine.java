package com.softuni.model.entity.Enum.vehicleEnum;

import com.softuni.model.entity.Enum.Region;

import java.util.HashMap;
import java.util.Map;

public enum Engine {

    Gasoline("Бензинов"),
    Diesel("Дизелов"),
    Electric("Електрически"),
    Hybrid("Хибриден");


    private String engine;


    Engine(String engine) {
        this.engine = engine;
    }

    public String getEngine() {
        return engine;
    }

    private static final Map<String, Engine> map;
    static {
        map = new HashMap<>();
        for (Engine v : Engine.values()) {
            map.put(v.engine, v);
        }
    }
    public static Engine findByString(String name) {
        return map.get(name);
    }


}
