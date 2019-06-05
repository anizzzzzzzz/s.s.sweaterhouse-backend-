package com.anizzzz.product.sssweaterhouse.service.user.impl;

import com.anizzzz.product.sssweaterhouse.model.user.Role;
import com.anizzzz.product.sssweaterhouse.repository.user.RoleRepository;
import com.anizzzz.product.sssweaterhouse.service.user.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService implements IRoleService{
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void saveAll(List<Role> roles) {
        roleRepository.saveAll(roles);
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }
}
