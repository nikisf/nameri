package com.softuni.web;


import com.softuni.model.entity.Enum.Region;
import com.softuni.model.service.JobServiceModel;
import com.softuni.service.JobService;
import com.softuni.web.annotation.PageTitle;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Controller
public class HomeController {

    private final JobService jobService;

    public HomeController(JobService jobService) {
        this.jobService = jobService;
    }


    @GetMapping("/")
    @PreAuthorize("isAnonymous()")
    @PageTitle("index")
    public String index(){
        List<JobServiceModel> jobServiceModel = this.jobService.findAll().stream().filter(e -> LocalDate.now().compareTo(e.getExpireOn()) > 0).collect(Collectors.toList());
        System.out.println(jobServiceModel.size());
        return "index";
    }

    @GetMapping("/index")
    @PreAuthorize("isAnonymous()")
    @PageTitle("index")
    public String index2(){
        return "index";
    }


    @GetMapping("/home")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("home")
    public ModelAndView home(ModelAndView modelAndView){
        modelAndView.setViewName("home");
        return modelAndView;
    }





}
