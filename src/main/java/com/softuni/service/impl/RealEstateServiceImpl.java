package com.softuni.service.impl;

import com.softuni.exception.RealEstateNotFoundException;
import com.softuni.model.entity.RealEstate;
import com.softuni.model.service.RealEstateServiceModel;
import com.softuni.model.service.UserServiceModel;
import com.softuni.repository.RealEstateRepository;
import com.softuni.service.RealEstateService;
import com.softuni.service.UserService;
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
public class RealEstateServiceImpl implements RealEstateService {

    private final ModelMapper modelMapper;
    private final RealEstateRepository realEstateRepository;
    private final UserService userService;

    public RealEstateServiceImpl(ModelMapper modelMapper, RealEstateRepository realEstateRepository, UserService userService) {
        this.modelMapper = modelMapper;
        this.realEstateRepository = realEstateRepository;
        this.userService = userService;
    }

    @Override
    public void addRealEstate(RealEstateServiceModel realEstateServiceModel, String user) {
        realEstateServiceModel.setActive(true);
        realEstateServiceModel.setAddedOn(LocalDate.now());
        realEstateServiceModel.setExpireOn(realEstateServiceModel.getAddedOn().plus(10, ChronoUnit.DAYS));
        realEstateServiceModel.setUser(this.userService.findByUsername(user));
        RealEstate realEstate = this.modelMapper.map(realEstateServiceModel, RealEstate.class);
        realEstate.setUsername(user);
        this.realEstateRepository.saveAndFlush(realEstate);
    }

    @Override
    public List<RealEstateServiceModel> findAll() {
        return this.realEstateRepository.findAll().stream().map(realEstate -> this.modelMapper.map(realEstate, RealEstateServiceModel.class)).collect(Collectors.toList());

    }

    @Override
    public RealEstateServiceModel findById(String id, String cyrillic) {
        RealEstate realEstate = this.realEstateRepository.findById(id).orElseThrow(() -> new RealEstateNotFoundException(REAL_ESTATE_NOT_FOUND));
        RealEstateServiceModel realEstateServiceModel = this.modelMapper.map(realEstate, RealEstateServiceModel.class);
        if (!cyrillic.equals("yes")){
            realEstateServiceModel.setRegion(realEstate.getRegion().toString());
            realEstateServiceModel.setRealEstateType(realEstate.getRealEstateType().toString());
        }
        return realEstateServiceModel;
    }

    @Override
    public void deleteById(String id) {
        RealEstate realEstate = this.realEstateRepository.findById(id).orElseThrow(() -> new RealEstateNotFoundException(REAL_ESTATE_NOT_FOUND));
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (realEstate.getUser().getUsername().equals(auth.getName()) || auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_MODERATOR"))){
            this.realEstateRepository.deleteById(id);
        } else {
            throw new AccessDeniedException(ACCESS_DENIED);
        }
    }

    @Override
    public void editRealEstate(RealEstateServiceModel realEstateServiceModel, String id) {
        RealEstate realEstate = this.realEstateRepository.findById(id).orElseThrow(() -> new RealEstateNotFoundException(REAL_ESTATE_NOT_FOUND));
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (realEstate.getUser().getUsername().equals(auth.getName()) || auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_MODERATOR"))){
            realEstateServiceModel.setAddedOn(realEstate.getAddedOn());
            realEstateServiceModel.setExpireOn(realEstate.getExpireOn());
            realEstateServiceModel.setActive(true);
            realEstateServiceModel.setUser(this.modelMapper.map(realEstate.getUser(), UserServiceModel.class));
            realEstateServiceModel.setImageUrl(realEstate.getImageUrl());
            this.realEstateRepository.saveAndFlush(this.modelMapper.map(realEstateServiceModel, RealEstate.class));
        } else {
            throw new AccessDeniedException(ACCESS_DENIED);
        }

    }

    @Override
    public List<RealEstateServiceModel> findAllRealEstateForUsername(String username) {
        return this.realEstateRepository.findAllByUsername(username).stream()
                .map(realEstate -> this.modelMapper.map(realEstate, RealEstateServiceModel.class)).collect(Collectors.toList());
    }

    @Override
    public void renew(String id) {
        RealEstate realEstate = this.realEstateRepository.findById(id).orElseThrow(() -> new RealEstateNotFoundException(JOB_NOT_FOUND));
        RealEstateServiceModel realEstateServiceModel = this.modelMapper.map(realEstate, RealEstateServiceModel.class);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println();
        if (realEstateServiceModel.getUser().getUsername().equals(auth.getName()) || auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {

            if (LocalDate.now().compareTo(realEstateServiceModel.getExpireOn()) > 0) {
                System.out.println();
                realEstateServiceModel.setExpireOn(realEstateServiceModel.getExpireOn().plus(10, ChronoUnit.DAYS));
                realEstateServiceModel.setActive(true);
                realEstateServiceModel.setRealEstateType(realEstate.getRealEstateType().name());
                realEstateServiceModel.setRegion(realEstate.getRegion().name());
                realEstateServiceModel.setUsername(realEstate.getUsername());
                this.realEstateRepository.saveAndFlush(this.modelMapper.map(realEstateServiceModel, RealEstate.class));
            }
        } else {
            throw new AccessDeniedException(ACCESS_DENIED);
        }
    }

    @Override
    public void saveAll(List<RealEstateServiceModel> realEstates) {
        List<RealEstate> toRealEstate = realEstates.stream().map(realEstate -> this.modelMapper.map(realEstate, RealEstate.class)).collect(Collectors.toList());
        this.realEstateRepository.saveAll(toRealEstate);
    }

}
