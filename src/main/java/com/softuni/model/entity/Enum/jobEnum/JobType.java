package com.softuni.model.entity.Enum.jobEnum;

import java.util.HashMap;
import java.util.Map;

public enum JobType {

    All("Всички"),
    PermanentJob("Постоянна работа"),
    TemporaryJob("Временна/сезонна работа"),
    Internship("Стаж");


    private String jobType;


    JobType(String jobType) {
        this.jobType = jobType;
    }

    public String getJobType() {
        return jobType;
    }

    private static final Map<String, JobType> map;
    static {
        map = new HashMap<>();
        for (JobType v : JobType.values()) {
            map.put(v.jobType, v);
        }
    }
    public static JobType findByString(String name) {
        return map.get(name);
    }
}
