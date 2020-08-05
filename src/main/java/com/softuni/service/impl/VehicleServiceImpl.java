package com.softuni.service.impl;

import com.softuni.exception.VehicleNotFoundException;
import com.softuni.model.entity.Vehicle;
import com.softuni.model.service.UserServiceModel;
import com.softuni.model.service.VehicleServiceModel;
import com.softuni.repository.VehicleRepository;
import com.softuni.service.UserService;
import com.softuni.service.VehicleService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import static com.softuni.Constants.Constants.*;

@Service
public class VehicleServiceImpl implements VehicleService {

    private final ModelMapper modelMapper;
    private final VehicleRepository vehicleRepository;
    private final UserService userService;

    public VehicleServiceImpl(ModelMapper modelMapper, VehicleRepository vehicleRepository, UserService userService) {
        this.modelMapper = modelMapper;
        this.vehicleRepository = vehicleRepository;
        this.userService = userService;
    }


    @Override
    public void addVehicle(VehicleServiceModel vehicleServiceModel, String user) {
        vehicleServiceModel.setActive(true);
        vehicleServiceModel.setAddedOn(LocalDate.now());
        vehicleServiceModel.setExpireOn(vehicleServiceModel.getAddedOn().plus(10, ChronoUnit.DAYS));

        vehicleServiceModel.setUser(this.userService.findByUsername(user));
        vehicleServiceModel.setUsername(user);
        Vehicle vehicle = this.modelMapper.map(vehicleServiceModel, Vehicle.class);
        this.vehicleRepository.saveAndFlush(vehicle);
    }

    @Override
    public List<VehicleServiceModel> findAll() {
        return this.vehicleRepository.findAll().stream().map(vehicle -> this.modelMapper.map(vehicle, VehicleServiceModel.class)).collect(Collectors.toList());

    }

    @Override
    public VehicleServiceModel findById(String id, String cyrillic) {
        Vehicle vehicle = this.vehicleRepository.findById(id).orElseThrow(() -> new VehicleNotFoundException(VEHICLE_NOT_FOUND));
        VehicleServiceModel vehicleServiceModel = this.modelMapper.map(vehicle, VehicleServiceModel.class);
        if (!cyrillic.equals("yes")) {
            vehicleServiceModel.setRegion(vehicle.getRegion().toString());
            vehicleServiceModel.setTransmission(vehicle.getTransmission().toString());
            vehicleServiceModel.setEngine(vehicle.getEngine().toString());
        }
        return vehicleServiceModel;


    }

    @Override
    public void deleteById(String id) {
        Vehicle vehicle = this.vehicleRepository.findById(id).orElseThrow(() -> new VehicleNotFoundException(VEHICLE_NOT_FOUND));
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (vehicle.getUser().getUsername().equals(auth.getName()) || auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_MODERATOR"))) {
            this.vehicleRepository.deleteById(id);
        } else {
            throw new AccessDeniedException(ACCESS_DENIED);
        }
    }

    @Override
    public void editVehicle(VehicleServiceModel vehicleServiceModel, String id) {
        Vehicle vehicle = this.vehicleRepository.findById(id).orElseThrow(() -> new VehicleNotFoundException(VEHICLE_NOT_FOUND));
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (vehicle.getUser().getUsername().equals(auth.getName()) || auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_MODERATOR"))) {
            vehicleServiceModel.setAddedOn(vehicle.getAddedOn());
            vehicleServiceModel.setExpireOn(vehicle.getExpireOn());
            vehicleServiceModel.setActive(vehicle.isActive());
            vehicleServiceModel.setUsername(vehicle.getUsername());
            vehicleServiceModel.setUser(this.modelMapper.map(vehicle.getUser(), UserServiceModel.class));
            vehicleServiceModel.setImageUrl(vehicle.getImageUrl());
            this.vehicleRepository.saveAndFlush(this.modelMapper.map(vehicleServiceModel, Vehicle.class));
        } else {
            throw new AccessDeniedException(ACCESS_DENIED);
        }

    }

    @Override
    public List<VehicleServiceModel> findAllVehiclesForUsername(String username) {
        return this.vehicleRepository.findAllByUsername(username).stream()
                .map(vehicle -> this.modelMapper.map(vehicle, VehicleServiceModel.class)).collect(Collectors.toList());
    }

    @Override
    public void renew(String id) {
        Vehicle vehicle = this.vehicleRepository.findById(id).orElseThrow(() -> new VehicleNotFoundException(JOB_NOT_FOUND));
        VehicleServiceModel vehicleServiceModel = this.modelMapper.map(vehicle, VehicleServiceModel.class);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (vehicleServiceModel.getUser().getUsername().equals(auth.getName()) || auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_MODERATOR"))) {
            if (LocalDate.now().compareTo(vehicleServiceModel.getExpireOn()) > 0) {
                vehicleServiceModel.setExpireOn(vehicleServiceModel.getExpireOn().plus(10, ChronoUnit.DAYS));
                vehicleServiceModel.setAddedOn(LocalDate.now());
                vehicleServiceModel.setActive(true);
                vehicleServiceModel.setEngine(vehicle.getEngine().name());
                vehicleServiceModel.setTransmission(vehicle.getTransmission().name());
                vehicleServiceModel.setRegion(vehicle.getRegion().name());
                vehicleServiceModel.setUsername(vehicle.getUsername());
                this.vehicleRepository.saveAndFlush(this.modelMapper.map(vehicleServiceModel, Vehicle.class));
            }
        } else {
            throw new AccessDeniedException(ACCESS_DENIED);
        }
    }

    @Override
    public void saveAll(List<VehicleServiceModel> vehicles) {
            List<Vehicle> toVehicle = vehicles.stream().map(vehicle -> this.modelMapper.map(vehicle, Vehicle.class)).collect(Collectors.toList());
            this.vehicleRepository.saveAll(toVehicle);
    }

}
