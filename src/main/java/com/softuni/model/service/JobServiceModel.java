package com.softuni.model.service;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import static com.softuni.Constants.Constants.*;

public class JobServiceModel extends BaseOfferServiceModel {

    private String username;
    private String jobType;
    private String jobLevel;
    private int salary;


    public JobServiceModel() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotNull(message = NULL_JOB_TYPE_MESSAGE)
    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }
    @NotNull(message = NULL_JOB_LEVEL_MESSAGE)
    public String getJobLevel() {
        return jobLevel;
    }

    public void setJobLevel(String jobLevel) {
        this.jobLevel = jobLevel;
    }


    @Positive(message = VALUE_MUST_BE_POSITIVE)
    @NotNull(message = NULL_JOB_LEVEL_MESSAGE)
    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
}
