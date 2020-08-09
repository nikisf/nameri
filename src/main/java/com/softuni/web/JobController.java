package com.softuni.web;

import com.softuni.model.binding.JobAddBindingModel;
import com.softuni.model.binding.VehicleAddBindingModel;
import com.softuni.model.binding.VehicleEditBindingModel;
import com.softuni.model.service.JobServiceModel;
import com.softuni.model.service.VehicleServiceModel;
import com.softuni.model.view.JobViewModel;
import com.softuni.model.view.VehicleViewModel;
import com.softuni.service.JobService;
import com.softuni.web.annotation.PageTitle;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;

@Controller
@RequestMapping("/jobs")
public class JobController {

    private final ModelMapper modelMapper;
    private final JobService jobService;

    public JobController(ModelMapper modelMapper, JobService jobService) {
        this.modelMapper = modelMapper;
        this.jobService = jobService;
    }

    @PageTitle("add job")
    @GetMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public String add(Model model) {
        if (!model.containsAttribute("jobAddBindingModel")) {
            model.addAttribute("jobAddBindingModel", new JobAddBindingModel());
        }
        return "jobs/job-add";
    }

    @PostMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView addConfirm(@Valid @ModelAttribute("jobAddBindingModel") JobAddBindingModel jobAddBindingModel,
                                   BindingResult bindingResult, Principal principal, ModelAndView modelAndView) {
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("jobAddBindingModel", jobAddBindingModel);
            modelAndView.setViewName("jobs/job-add");
            return modelAndView;
        }
        String name = principal.getName();
        JobServiceModel jobServiceModel = this.modelMapper.map(jobAddBindingModel, JobServiceModel.class);
        this.jobService.addJob(jobServiceModel, name);
        modelAndView.setViewName("redirect:/ads");
        return modelAndView;
    }

    @PageTitle("job details")
    @GetMapping("/details/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView details(@PathVariable String id,
                                    ModelAndView modelAndView) {
        JobViewModel jobViewModel = this.modelMapper.map(jobService.findById(id, "yes"), JobViewModel.class);
        modelAndView.addObject("jobViewModel", jobViewModel);
        modelAndView.setViewName("jobs/job-details");
        return modelAndView;
    }

    @PageTitle("job edit")
    @GetMapping("/edit/{id}")
    @PreAuthorize("isAuthenticated()")
    public String edit(@PathVariable String id, Model model) {
        JobServiceModel jobServiceModel = this.jobService.findById(id, "no");
        JobAddBindingModel jobAddBindingModel = this.modelMapper.map(jobServiceModel, JobAddBindingModel.class);
        model.addAttribute("jobAddBindingModel", jobAddBindingModel);

        return "jobs/job-edit";
    }


    @PostMapping("/edit/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView editConfirm(@PathVariable String id,
                                    ModelAndView modelAndView, JobAddBindingModel jobAddBindingModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("jobAddBindingModel", jobAddBindingModel);
            modelAndView.setViewName("jobs/job-edit");
            return modelAndView;
        }
        JobServiceModel jobServiceModel = this.modelMapper.map(jobAddBindingModel, JobServiceModel.class);
        this.jobService.editJob(jobServiceModel, id);
        modelAndView.setViewName("redirect:/my-jobs");
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView deleteJob(@PathVariable String id, ModelAndView modelAndView) {
        this.jobService.deleteById(id);
        modelAndView.setViewName("redirect:/my-jobs");
        return modelAndView;
    }

    @GetMapping("/renew/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView renew(@PathVariable String id, ModelAndView modelAndView){
        this.jobService.renew(id);
        modelAndView.setViewName("redirect:/my-jobs");
        return modelAndView;
    }
}
