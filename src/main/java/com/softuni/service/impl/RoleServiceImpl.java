package com.softuni.service.impl;

import com.softuni.model.entity.Role;
import com.softuni.model.service.RoleServiceModel;
import com.softuni.repository.RoleRepository;
import com.softuni.service.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    public RoleServiceImpl(RoleRepository roleRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedRolesInDb() {
        if (this.roleRepository.count() == 0){
            Role admin = new Role();
            admin.setAuthority("ROLE_ADMIN");
            Role moderator = new Role();
            moderator.setAuthority("ROLE_MODERATOR");
            Role user = new Role();
            user.setAuthority("ROLE_USER");
            this.roleRepository.saveAndFlush(user);
            this.roleRepository.saveAndFlush(moderator);
            this.roleRepository.saveAndFlush(admin);
        }
    }

    @Override
    public RoleServiceModel findByAuthority(String name) {
        return this.modelMapper.map(this.roleRepository.findByAuthority(name), RoleServiceModel.class);
    }

    @Override
    public Set<RoleServiceModel> findAllRoles() {
        return this.roleRepository.findAll()
                .stream()
                .map(r -> this.modelMapper.map(r, RoleServiceModel.class))
                .collect(Collectors.toSet());
    }
}
