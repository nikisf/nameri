package com.softuni.web.api;

import com.softuni.model.response.RealEstateResponseModel;
import com.softuni.model.response.VehicleResponseModel;
import com.softuni.model.service.BaseOfferServiceModel;
import com.softuni.service.RealEstateService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class RealEstateRestController {

    private final RealEstateService realEstateService;
    private final ModelMapper modelMapper;

    public RealEstateRestController(RealEstateService realEstateService, ModelMapper modelMapper) {
        this.realEstateService = realEstateService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/api/real-estates")
    @PreAuthorize("isAuthenticated()")
    public List<RealEstateResponseModel> getAllRealEstates() {
        return this.realEstateService.findAll().stream().sorted(Comparator.comparing(BaseOfferServiceModel::getAddedOn).reversed()).filter(BaseOfferServiceModel::isActive)
                .map(realEstate -> this.modelMapper.map(realEstate, RealEstateResponseModel.class)).collect(Collectors.toList());
    }
}
