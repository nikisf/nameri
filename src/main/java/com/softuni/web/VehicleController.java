package com.softuni.web;

import com.softuni.model.binding.VehicleAddBindingModel;
import com.softuni.model.binding.VehicleEditBindingModel;
import com.softuni.model.service.VehicleServiceModel;
import com.softuni.model.view.VehicleViewModel;
import com.softuni.service.VehicleService;
import com.softuni.service.CloudinaryService;
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
@RequestMapping("/vehicles")
public class VehicleController {

    private final ModelMapper modelMapper;
    private final VehicleService vehicleService;
    private final CloudinaryService cloudinaryService;


    public VehicleController(ModelMapper modelMapper, VehicleService vehicleService, CloudinaryService cloudinaryService) {
        this.modelMapper = modelMapper;
        this.vehicleService = vehicleService;
        this.cloudinaryService = cloudinaryService;
    }

    @PageTitle("add vehicle")
    @GetMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public String add(Model model) {
        if (!model.containsAttribute("vehicleAddBindingModel")) {
            model.addAttribute("vehicleAddBindingModel", new VehicleAddBindingModel());
        }
        return "vehicles/vеhicle-add";
    }


    @PostMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView addConfirm(@Valid @ModelAttribute("vehicleAddBindingModel") VehicleAddBindingModel vehicleAddBindingModel,
                                   BindingResult bindingResult, Principal principal, ModelAndView modelAndView) throws IOException {

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("vehicleAddBindingModel", vehicleAddBindingModel);
            modelAndView.setViewName("vehicles/vеhicle-add");
            return modelAndView;
        }
        String name = principal.getName();
        VehicleServiceModel vehicleServiceModel = this.modelMapper.map(vehicleAddBindingModel, VehicleServiceModel.class);
        vehicleServiceModel.setImageUrl(this.cloudinaryService.uploadImage(vehicleAddBindingModel.getImage()));
        this.vehicleService.addVehicle(vehicleServiceModel, name);
        modelAndView.setViewName("redirect:/ads");
        return modelAndView;
    }

    @PageTitle("vehicle details")
    @GetMapping("/details/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView editConfirm(@PathVariable String id,
                                    ModelAndView modelAndView) {
        VehicleViewModel vehicleViewModel = this.modelMapper.map(vehicleService.findById(id, "yes"), VehicleViewModel.class);
        System.out.println();
        modelAndView.addObject("vehicleViewModel", vehicleViewModel);
        modelAndView.setViewName("vehicles/vehicle-details");
        return modelAndView;
    }

    @PageTitle("edit vehicle")
    @GetMapping("/edit/{id}")
    @PreAuthorize("isAuthenticated()")
    public String edit(@PathVariable String id, Model model) {
        if (!model.containsAttribute("vehicleEditBindingModel")) {
            model.addAttribute("vehicleEditBindingModel", new VehicleEditBindingModel());
        }
        VehicleServiceModel vehicleServiceModel = this.vehicleService.findById(id, "no");
        VehicleEditBindingModel vehicleEditBindingModel = this.modelMapper.map(vehicleServiceModel, VehicleEditBindingModel.class);
        model.addAttribute("vehicleEditBindingModel", vehicleEditBindingModel);

        return "vehicles/vеhicle-edit";
    }


    @PostMapping("/edit/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView editConfirm(@PathVariable String id,
                                    ModelAndView modelAndView, VehicleEditBindingModel vehicleEditBindingModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("vehicleEditBindingModel", vehicleEditBindingModel);
            modelAndView.setViewName("vehicles/vеhicle-edit");
            return modelAndView;
        }
        VehicleServiceModel vehicleServiceModel = this.modelMapper.map(vehicleEditBindingModel, VehicleServiceModel.class);
        this.vehicleService.editVehicle(vehicleServiceModel, id);
        modelAndView.setViewName("redirect:/my-vehicles");
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView clothDelete(@PathVariable String id, ModelAndView modelAndView) {
        vehicleService.deleteById(id);
        modelAndView.setViewName("redirect:/my-vehicles");
        return modelAndView;
    }

    @GetMapping("/renew/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView renew(@PathVariable String id, ModelAndView modelAndView){
        this.vehicleService.renew(id);
        modelAndView.setViewName("redirect:/my-vehicles");
        return modelAndView;
    }
}
