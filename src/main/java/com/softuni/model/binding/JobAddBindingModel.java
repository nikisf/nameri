package com.softuni.model.binding;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import static com.softuni.Constants.Constants.*;

public class JobAddBindingModel extends BaseOfferBindingModel {

    private String jobType;
    private String jobLevel;
    private int salary;

    public JobAddBindingModel() {
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
