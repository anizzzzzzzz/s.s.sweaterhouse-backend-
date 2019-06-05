package com.anizzzz.product.sssweaterhouse.repository.user;

import com.anizzzz.product.sssweaterhouse.model.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByName(String name);
}
