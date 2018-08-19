package com.anizzzz.product.sssweaterhouse.service;

import com.anizzzz.product.sssweaterhouse.model.Role;

import java.util.List;

public interface IRoleService {
    Role findOne(int id);

    void saveAll(List<Role> roles);

    List<Role> findAll();
}
