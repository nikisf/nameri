package com.softuni.model.view;

import com.softuni.model.service.BaseOfferServiceModel;

public class JobViewModel extends BaseOfferServiceModel {

    private String jobType;
    private String jobLevel;
    private int salary;

    public JobViewModel() {
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getJobLevel() {
        return jobLevel;
    }

    public void setJobLevel(String jobLevel) {
        this.jobLevel = jobLevel;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
}
