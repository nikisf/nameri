package com.softuni.service.impl;

import com.softuni.model.entity.Log;
import com.softuni.model.service.LogServiceModel;
import com.softuni.repository.LogRepository;
import com.softuni.service.LogService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class LogServiceImpl implements LogService {
    private final LogRepository logRepository;
    private final ModelMapper modelMapper;


    public LogServiceImpl(LogRepository logRepository, ModelMapper modelMapper) {
        this.logRepository = logRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public void saveInDb(LogServiceModel logServiceModel) {
        this.logRepository.saveAndFlush(this.modelMapper.map(logServiceModel, Log.class));
    }
}
