package com.softuni.service;

import com.softuni.model.binding.VehicleEditBindingModel;
import com.softuni.model.entity.Vehicle;
import com.softuni.model.service.VehicleServiceModel;

import java.util.List;

public interface VehicleService {

    void addVehicle(VehicleServiceModel vehicleServiceModel, String user);

    List<VehicleServiceModel> findAll();

    VehicleServiceModel findById(String id, String cyrillic);

    void deleteById(String id);


    void editVehicle(VehicleServiceModel vehicleServiceModel, String id);

    List<VehicleServiceModel> findAllVehiclesForUsername(String username);

    VehicleServiceModel renew (String id);

    void saveAll(List<VehicleServiceModel> vehicles);
}
