package com.anizzzz.product.sssweaterhouse.service.user;

import com.anizzzz.product.sssweaterhouse.model.user.Role;

import java.util.List;

public interface IRoleService {
    void saveAll(List<Role> roles);

    List<Role> findAll();

    Role findByName(String name);
}
