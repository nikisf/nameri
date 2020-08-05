package com.softuni.model.entity;

import com.softuni.model.entity.Enum.jobEnum.JobLevel;
import com.softuni.model.entity.Enum.jobEnum.JobType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import static com.softuni.Constants.Constants.*;

@Entity
@Table(name = "jobs")
public class Job extends BaseOffer{

    private String username;
    private JobType jobType;
    private JobLevel jobLevel;
    private int salary;

    public Job() {
    }

    @NotNull
    @Column(name="username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "job_type")
    @NotNull(message = NULL_JOB_TYPE_MESSAGE)
    @Enumerated(EnumType.STRING)
    public JobType getJobType() {
        return jobType;
    }

    public void setJobType(JobType jobType) {
        this.jobType = jobType;
    }

    @Column(name = "job_level")
    @NotNull(message = NULL_JOB_LEVEL_MESSAGE)
    @Enumerated(EnumType.STRING)
    public JobLevel getJobLevel() {
        return jobLevel;
    }

    public void setJobLevel(JobLevel jobLevel) {
        this.jobLevel = jobLevel;
    }

    @Positive(message = VALUE_MUST_BE_POSITIVE)
    @Column(name = "salary", nullable = false)
    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
}
