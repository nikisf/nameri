package com.softuni.service;

import com.softuni.model.entity.Vehicle;
import com.softuni.model.service.VehicleServiceModel;
import com.softuni.repository.VehicleRepository;
import com.softuni.service.impl.VehicleServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class VehicleServiceTest {

    @Mock
    VehicleRepository vehicleRepository;

    VehicleService vehicleService;

    ModelMapper modelMapper;

    Vehicle vehicle;
    @Mock
    UserService userService;

    @BeforeEach
    public void setupTest() {
        this.modelMapper = new ModelMapper();
        vehicle = new Vehicle();
        vehicle.setModel("asd");
        vehicle.setBrand("asd");
        vehicle.setImageUrl("asd");
        vehicle.setPrice(new BigDecimal(3));
        vehicleService = new VehicleServiceImpl(modelMapper, vehicleRepository, userService);
        this.vehicleRepository.saveAndFlush(vehicle);


    }

    @Test
    public void shouldReturnAllVehicles(){
        Mockito.when(this.vehicleRepository.findAll()).thenReturn(List.of(vehicle));

        List<Vehicle> expected = List.of(vehicle);
        Mockito.when(vehicleRepository.findAll()).thenReturn(List.of(vehicle));
        List<VehicleServiceModel> actual = vehicleService.findAll();
        Assert.assertEquals(expected.get(0).getId(), actual.get(0).getId());
        Assert.assertEquals(expected.get(0).getModel(), actual.get(0).getModel());
        Assert.assertEquals(expected.get(0).getBrand(), actual.get(0).getBrand());
        Assert.assertEquals(expected.get(0).getImageUrl(), actual.get(0).getImageUrl());
    }
}
