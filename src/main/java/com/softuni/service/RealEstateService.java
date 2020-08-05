package com.softuni.service;

import com.softuni.model.service.RealEstateServiceModel;
import com.softuni.model.service.VehicleServiceModel;

import java.util.List;

public interface RealEstateService {

    void addRealEstate(RealEstateServiceModel realEstateServiceModel, String user);

    List<RealEstateServiceModel> findAll();

    RealEstateServiceModel findById(String id, String cyrillic);

    void deleteById(String id);

    void editRealEstate(RealEstateServiceModel realEstateServiceModel, String id);

    List<RealEstateServiceModel> findAllRealEstateForUsername(String username);

    void renew(String id);

    void saveAll(List<RealEstateServiceModel> realEstates);
}
