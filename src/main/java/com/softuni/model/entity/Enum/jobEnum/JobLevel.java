package com.softuni.model.entity.Enum.jobEnum;

import com.softuni.model.entity.Enum.realEstateEnum.RealEstateType;

import java.util.HashMap;
import java.util.Map;

public enum JobLevel {

    All("Всички"),
    Management("Мениджмънт"),
    Experts("Експерти/Специалисти"),
    Employees("Служители/Работници");


    private String jobLevel;


    JobLevel(String jobLevel) {
        this.jobLevel = jobLevel;
    }

    public String getJobLevel() {
        return jobLevel;
    }


    private static final Map<String, JobLevel> map;
    static {
        map = new HashMap<>();
        for (JobLevel v : JobLevel.values()) {
            map.put(v.jobLevel, v);
        }
    }
    public static JobLevel findByString(String name) {
        return map.get(name);
    }
}


