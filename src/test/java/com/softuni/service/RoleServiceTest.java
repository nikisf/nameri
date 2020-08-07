package com.softuni.service;

import com.softuni.model.entity.Role;
import com.softuni.model.entity.User;
import com.softuni.model.service.RoleServiceModel;
import com.softuni.repository.RoleRepository;
import com.softuni.service.impl.RoleServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {

    private Role role;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private ModelMapper fakeMapper;
    private ModelMapper modelMapper;
    RoleServiceModel roleServiceModel;
    RoleService roleService;

    @BeforeEach
    public void setupTest() {
    this.modelMapper = new ModelMapper();
    role = new Role();
    role.setAuthority("ROLE_USER");
    role.setId("test");
    roleServiceModel = new RoleServiceModel();
    roleServiceModel.setAuthority("ROLE_USER");
    role.setId("roleTest");

    roleService = new RoleServiceImpl(roleRepository, modelMapper);
    }

    @Test
    public void shouldReturnAllRoles() {
        Mockito.when(this.roleRepository.findAll()).thenReturn(List.of(role));

        List<Role> expected = List.of(role);
        Set<RoleServiceModel> actualSet = roleService.findAllRoles();
        List<RoleServiceModel> actual = new ArrayList<>(actualSet);


        Assert.assertEquals(expected.get(0).getId(), actual.get(0).getId());
        Assert.assertEquals(expected.get(0).getAuthority(), actual.get(0).getAuthority());
        Assert.assertEquals(expected.size(), actual.size());
    }

    @Test
    public void shouldReturnAuthorityByString(){
        Mockito.when(roleRepository.findByAuthority("test"))
                .thenReturn(role);
        RoleServiceModel result = roleService.findByAuthority("test");
        Assert.assertEquals(role.getId(), result.getId());
    }

    @Test
    public void shouldSeedRolesToDb(){
        when(this.roleRepository.saveAndFlush(Mockito.any(Role.class)))
                .thenReturn(new Role());
        this.roleService.seedRolesInDb();
        Mockito.verify(this.roleRepository, times(3)).saveAndFlush(Mockito.any(Role.class));

    }


}
