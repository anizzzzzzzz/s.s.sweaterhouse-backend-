package com.anizzzz.product.sssweaterhouse.security.jwtutil;

import com.anizzzz.product.sssweaterhouse.model.Role;
import com.anizzzz.product.sssweaterhouse.model.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

public class JwtUserFactory {
    private JwtUserFactory(){}

    public static JwtUser create(Users users){
        return new JwtUser(
                users.getId(),
                users.getUsername(),
                users.getPassword(),
                mapToGrantedAuthorities(users.getRoles()),
                users.isActive(),
                users.getCreatedDate(),
                users.getFirstname(),
                users.getLastname(),
                users.getActivatedDate(),
                users.getPasswordStamp()
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> roles){
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}
