package com.softuni.service;

import com.softuni.model.service.JobServiceModel;
import com.softuni.model.service.RealEstateServiceModel;
import com.softuni.model.service.VehicleServiceModel;

import java.util.List;

public interface JobService {

    void addJob(JobServiceModel jobServiceModel, String user);

    List<JobServiceModel> findAll();

    JobServiceModel findById(String id, String cyrillic);

    void deleteById(String id);


    void editJob(JobServiceModel jobServiceModel, String id);

    List<JobServiceModel> findAllJobsForUsername(String username);

    void renew(String id);

    void saveAll(List<JobServiceModel> jobs);
}
