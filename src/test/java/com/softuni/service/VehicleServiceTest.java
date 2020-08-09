package com.softuni.service;


import com.softuni.model.entity.Enum.Region;
import com.softuni.model.entity.Enum.vehicleEnum.Engine;
import com.softuni.model.entity.Enum.vehicleEnum.Transmission;
import com.softuni.model.entity.User;
import com.softuni.model.entity.Vehicle;
import com.softuni.model.service.UserServiceModel;
import com.softuni.model.service.VehicleServiceModel;
import com.softuni.repository.VehicleRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static java.util.Optional.ofNullable;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class VehicleServiceTest {

    @MockBean
    UserService userService;

    @MockBean
    VehicleRepository vehicleRepository;
    Vehicle vehicle;
    User user;

    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);

        vehicle = new Vehicle();
        vehicle.setBrand("da");
        vehicle.setId("asd");
        vehicle.setRegion(Region.Burgas);
        vehicle.setEngine(Engine.Electric);
        vehicle.setTransmission(Transmission.SemiAutomatic);
        user = new User();
        user.setUsername("test");
        vehicle.setUser(user);

    }
    @Autowired
    VehicleService vehicleService;

    @Test
    public void shouldReturnAllVehicles(){
        List<Vehicle> expected = List.of(vehicle);
        when(vehicleRepository.findAll()).thenReturn(List.of(vehicle));
        List<VehicleServiceModel> actual = vehicleService.findAll();
        Assert.assertEquals(expected.get(0).getId(), actual.get(0).getId());
        Assert.assertEquals(expected.get(0).getModel(), actual.get(0).getModel());
        Assert.assertEquals(expected.get(0).getBrand(), actual.get(0).getBrand());
        Assert.assertEquals(expected.get(0).getImageUrl(), actual.get(0).getImageUrl());

    }

    @Test
    public void shouldReturnVehicleById(){
        Mockito.when(this.vehicleRepository.findById(anyString())).thenReturn(java.util.Optional.ofNullable(vehicle));
        VehicleServiceModel expected = vehicleService.findById("asd", anyString());
        Assert.assertEquals(expected.getBrand(), vehicle.getBrand());
        Assert.assertEquals(expected.getModel(), vehicle.getModel());
        Assert.assertEquals(expected.getImageUrl(), vehicle.getImageUrl());
        Assert.assertEquals(expected.getPrice(), vehicle.getPrice());
        Assert.assertEquals(expected.getTransmission(), vehicle.getTransmission().name());
    }

    @Test
    public void shouldSuccessDeleteById(){
        when(vehicleRepository.findById(anyString())).thenReturn(ofNullable(vehicle));
       Authentication authToken = new TestingAuthenticationToken(user.getUsername(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authToken);
        vehicleService.deleteById("asd");
        Mockito.verify(this.vehicleRepository, times(1)).deleteById(anyString());
    }

    @Test
    public void shouldThrowExceptionWhenTryToDeleteVehicleOfOtherUser() {
        when(vehicleRepository.findById(anyString())).thenReturn(ofNullable(vehicle));
        Authentication authToken = new TestingAuthenticationToken(new User().getUsername(), new User().getPassword());
        SecurityContextHolder.getContext().setAuthentication(authToken);
        Assert.assertThrows(AccessDeniedException.class, () -> vehicleService.deleteById(anyString()));
    }

    @Test
    public void shouldAddVehicle() {
        VehicleServiceModel vehicleServiceModel = new VehicleServiceModel();
        vehicleServiceModel.setBrand("testBrand");
        when(userService.findByUsername(anyString())).thenReturn(new UserServiceModel());
        vehicleService.addVehicle(vehicleServiceModel, "test");
        Mockito.verify(this.vehicleRepository, times(1)).saveAndFlush(Mockito.any(Vehicle.class));

    }

    @Test
    public void shouldEditVehicleSuccess(){
        VehicleServiceModel vehicleServiceModel = new VehicleServiceModel();
        vehicleServiceModel.setId("asd");
        when(vehicleRepository.findById(anyString())).thenReturn(ofNullable(vehicle));
        Authentication authToken = new TestingAuthenticationToken(user.getUsername(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authToken);
        when(vehicleRepository.saveAndFlush(Mockito.any(Vehicle.class))).thenReturn(new Vehicle());
        vehicleService.editVehicle(vehicleServiceModel, vehicle.getId());
        Assert.assertEquals(vehicleServiceModel.getUser().getUsername(), vehicle.getUser().getUsername());
        Assert.assertEquals(vehicleServiceModel.getUser().getId(), vehicle.getUser().getId());
    }

    @Test
    public void shouldThrowExceptionWhenTryToEditVehicleOfOtherUser(){
        VehicleServiceModel vehicleServiceModel = new VehicleServiceModel();
        vehicleServiceModel.setId("asd");
        when(vehicleRepository.findById(anyString())).thenReturn(ofNullable(vehicle));
        Authentication authToken = new TestingAuthenticationToken(new User().getUsername(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authToken);
        Assert.assertThrows(AccessDeniedException.class, () -> vehicleService.editVehicle(vehicleServiceModel, anyString()));
    }

    @Test
    public void shouldReturnAllVehiclesForUsername(){
        List<Vehicle> expected = List.of(vehicle);
        when(vehicleRepository.findAllByUsername(anyString())).thenReturn(List.of(vehicle));
        List<VehicleServiceModel> actual = vehicleService.findAllVehiclesForUsername("asd");
        Assert.assertEquals(expected.get(0).getId(), actual.get(0).getId());
        Assert.assertEquals(expected.get(0).getModel(), actual.get(0).getModel());
        Assert.assertEquals(expected.get(0).getBrand(), actual.get(0).getBrand());
        Assert.assertEquals(expected.get(0).getImageUrl(), actual.get(0).getImageUrl());
    }


}
