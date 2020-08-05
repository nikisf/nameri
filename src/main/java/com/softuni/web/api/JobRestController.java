package com.softuni.web.api;

import com.softuni.model.response.JobResponseModel;
import com.softuni.model.response.VehicleResponseModel;
import com.softuni.model.service.BaseOfferServiceModel;
import com.softuni.service.JobService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class JobRestController {

    private final JobService jobService;
    private final ModelMapper modelMapper;

    public JobRestController(JobService jobService, ModelMapper modelMapper) {
        this.jobService = jobService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/api/jobs")
    @PreAuthorize("isAuthenticated()")
    public List<JobResponseModel> getAllJobs() {
        return this.jobService.findAll().stream().sorted(Comparator.comparing(BaseOfferServiceModel::getAddedOn).reversed()).filter(BaseOfferServiceModel::isActive)
                .map(jobServiceModel -> this.modelMapper.map(jobServiceModel, JobResponseModel.class)).collect(Collectors.toList());
    }

}
