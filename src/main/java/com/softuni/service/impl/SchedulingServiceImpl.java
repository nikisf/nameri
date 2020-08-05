package com.softuni.service.impl;

import com.softuni.model.entity.Enum.Region;
import com.softuni.model.entity.Enum.jobEnum.JobLevel;
import com.softuni.model.entity.Enum.jobEnum.JobType;
import com.softuni.model.entity.Enum.realEstateEnum.RealEstateType;
import com.softuni.model.entity.Enum.vehicleEnum.Engine;
import com.softuni.model.entity.Enum.vehicleEnum.Transmission;
import com.softuni.model.service.JobServiceModel;
import com.softuni.model.service.RealEstateServiceModel;
import com.softuni.model.service.VehicleServiceModel;
import com.softuni.service.JobService;
import com.softuni.service.RealEstateService;
import com.softuni.service.SchedulingService;
import com.softuni.service.VehicleService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SchedulingServiceImpl implements SchedulingService {

    private final JobService jobService;
    private final VehicleService vehicleService;
    private final RealEstateService realEstateService;


    public SchedulingServiceImpl(JobService jobService, VehicleService vehicleService, RealEstateService realEstateService) {
        this.jobService = jobService;
        this.vehicleService = vehicleService;
        this.realEstateService = realEstateService;
    }

    @Scheduled(cron = "0 30 13 * * ?")
    @Override
    public void findOldAds() {
        LocalDate date = LocalDate.now();
        List<JobServiceModel> jobServiceModel = this.jobService.findAll().stream().filter(e -> date.compareTo(e.getExpireOn()) > 0).
                peek(e -> {
                    e.setRegion(Region.findByString(e.getRegion()).toString());
                    e.setJobType(JobType.findByString(e.getJobType()).toString());
                    e.setJobLevel(JobLevel.findByString(e.getJobLevel()).toString());
                    e.setActive(false);
                }).collect(Collectors.toList());

        this.jobService.saveAll(jobServiceModel);

        List<VehicleServiceModel> vehicleServiceModel = this.vehicleService.findAll().stream().filter(e -> date.compareTo(e.getExpireOn()) > 0).collect(Collectors.toList());
        vehicleServiceModel = vehicleServiceModel.stream().peek(e -> {
            e.setTransmission(Transmission.findByString(e.getTransmission()).toString());
            e.setEngine(Engine.findByString(e.getEngine()).toString());
            e.setRegion(Region.findByString(e.getRegion()).toString());
            e.setActive(false);
        }).collect(Collectors.toList());

        this.vehicleService.saveAll(vehicleServiceModel);

        List<RealEstateServiceModel> realEstateServiceModel = this.realEstateService.findAll().stream().filter(e -> date.compareTo(e.getExpireOn()) > 0).
                peek(e -> {
                    e.setRealEstateType(RealEstateType.findByString(e.getRealEstateType()).toString());
                    e.setRegion(Region.findByString(e.getRegion()).toString());
                    e.setActive(false);
                }).collect(Collectors.toList());

        this.realEstateService.saveAll(realEstateServiceModel);
    }
}
