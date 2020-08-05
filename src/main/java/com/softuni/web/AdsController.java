package com.softuni.web;

import com.softuni.service.JobService;
import com.softuni.service.RealEstateService;
import com.softuni.service.VehicleService;
import com.softuni.web.annotation.PageTitle;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;



@Controller
public class AdsController {


    private final JobService jobService;
    private final RealEstateService realEstateService;
    private final VehicleService vehicleService;

    public AdsController(JobService jobService, RealEstateService realEstateService, VehicleService vehicleService) {
        this.jobService = jobService;
        this.realEstateService = realEstateService;
        this.vehicleService = vehicleService;
    }

    @PageTitle("ads")
    @GetMapping("/ads")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView ads(ModelAndView modelAndView){
        modelAndView.setViewName("ads");
        return modelAndView;
    }
    @PageTitle("my jobs")
    @GetMapping("/my-jobs")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView myJobs(ModelAndView modelAndView, Principal principal){
        modelAndView.addObject("jobs", this.jobService.findAllJobsForUsername(principal.getName()));
        modelAndView.setViewName("user/my-jobs");
        return modelAndView;
    }

    @PageTitle("my vehicles")
    @GetMapping("/my-vehicles")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView myVehicles(ModelAndView modelAndView, Principal principal){
        modelAndView.addObject("vehicles", this.vehicleService.findAllVehiclesForUsername(principal.getName()));
        modelAndView.setViewName("user/my-vehicles");
        return modelAndView;
    }
    @PageTitle("my real estates")
    @GetMapping("/my-real-estates")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView myRealEstates(ModelAndView modelAndView, Principal principal){
        modelAndView.addObject("realEstates", this.realEstateService.findAllRealEstateForUsername(principal.getName()));
        modelAndView.setViewName("user/my-realEstates");
        return modelAndView;
    }
}
