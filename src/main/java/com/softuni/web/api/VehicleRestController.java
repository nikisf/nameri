package com.softuni.web.api;

import com.softuni.model.response.VehicleResponseModel;
import com.softuni.model.service.BaseOfferServiceModel;
import com.softuni.service.VehicleService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class VehicleRestController {

    private final VehicleService vehicleService;
    private final ModelMapper modelMapper;

    public VehicleRestController(VehicleService vehicleService, ModelMapper modelMapper) {
        this.vehicleService = vehicleService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/api/vehicles")
    @PreAuthorize("isAuthenticated()")
    public List<VehicleResponseModel> getAllVehicles() {
        return this.vehicleService.findAll().stream().sorted(Comparator.comparing(BaseOfferServiceModel::getAddedOn).reversed()).filter(BaseOfferServiceModel::isActive)
                .map(vehicleServiceModel -> this.modelMapper.map(vehicleServiceModel, VehicleResponseModel.class)).collect(Collectors.toList());
    }

}
