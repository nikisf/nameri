package com.softuni.web;

import com.softuni.model.binding.RealEstateAddBindingModel;
import com.softuni.model.binding.RealEstateEditBindingModel;
import com.softuni.model.service.RealEstateServiceModel;
import com.softuni.model.view.RealEstateViewModel;
import com.softuni.service.RealEstateService;
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
@RequestMapping("/real-estates")
public class RealEstateController {
    private final ModelMapper modelMapper;
    private final RealEstateService realEstateService;
    private final CloudinaryService cloudinaryService;

    public RealEstateController(ModelMapper modelMapper, RealEstateService realEstateService, CloudinaryService cloudinaryService) {
        this.modelMapper = modelMapper;
        this.realEstateService = realEstateService;
        this.cloudinaryService = cloudinaryService;
    }
    @PageTitle("add real estate")
    @GetMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public String add(Model model) {
        if (!model.containsAttribute("realEstateAddBindingModel")) {
            model.addAttribute("realEstateAddBindingModel", new RealEstateAddBindingModel());
        }
        return "real-estates/real-estate-add";
    }

    @PostMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView addConfirm(@Valid @ModelAttribute("realEstateAddBindingModel") RealEstateAddBindingModel realEstateAddBindingModel,
                                   BindingResult bindingResult, Principal principal, ModelAndView modelAndView) throws IOException {

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("realEstateAddBindingModel", realEstateAddBindingModel);
            modelAndView.setViewName("real-estates/real-estate-add");
            return modelAndView;
        }
        String name = principal.getName();
        RealEstateServiceModel realEstateServiceModel = this.modelMapper.map(realEstateAddBindingModel, RealEstateServiceModel.class);
        realEstateServiceModel.setImageUrl(this.cloudinaryService.uploadImage(realEstateAddBindingModel.getImage()));
        this.realEstateService.addRealEstate(realEstateServiceModel, name);
        modelAndView.setViewName("redirect:/ads");
        return modelAndView;
    }


    @PageTitle("real estate details")
    @GetMapping("/details/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView details(@PathVariable String id,
                                    ModelAndView modelAndView) {
        RealEstateViewModel realEstateViewModel = this.modelMapper.map(realEstateService.findById(id, "yes"), RealEstateViewModel.class);
        modelAndView.addObject("realEstateViewModel", realEstateViewModel);
        modelAndView.setViewName("real-estates/real-estate-details");
        return modelAndView;
    }

    @PageTitle("real estate edit")
    @GetMapping("/edit/{id}")
    @PreAuthorize("isAuthenticated()")
    public String edit(@PathVariable String id, Model model) {
        if (!model.containsAttribute("realEstateEditBindingModel")) {
            model.addAttribute("realEstateEditBindingModel", new RealEstateEditBindingModel());
        }
        RealEstateServiceModel realEstateServiceModel = this.realEstateService.findById(id, "no");
        RealEstateEditBindingModel realEstateEditBindingModel = this.modelMapper.map(realEstateServiceModel, RealEstateEditBindingModel.class);
        model.addAttribute("realEstateServiceModel", realEstateEditBindingModel);

        return "real-estates/real-estate-edit";
    }


    @PostMapping("/edit/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView editConfirm(@PathVariable String id,
                                    ModelAndView modelAndView, RealEstateEditBindingModel realEstateEditBindingModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("realEstateEditBindingModel", realEstateEditBindingModel);
            modelAndView.setViewName("real-estates/real-estate-edit");
            return modelAndView;
        }
        RealEstateServiceModel realEstateServiceModel = this.modelMapper.map(realEstateEditBindingModel, RealEstateServiceModel.class);
        this.realEstateService.editRealEstate(realEstateServiceModel, id);
        modelAndView.setViewName("redirect:/my-real-estates");
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView deleteRealEstate(@PathVariable String id, ModelAndView modelAndView) {
        realEstateService.deleteById(id);
        modelAndView.setViewName("redirect:/my-real-estates");
        return modelAndView;
    }


    @GetMapping("/renew/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView renew(@PathVariable String id, ModelAndView modelAndView){
        this.realEstateService.renew(id);
        modelAndView.setViewName("redirect:/my-vehicles");
        return modelAndView;
    }
}
