package com.anizzzz.product.sssweaterhouse.security.jwtutil;

import com.anizzzz.product.sssweaterhouse.model.user.Role;
import com.anizzzz.product.sssweaterhouse.model.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

public class JwtUserFactory {
    private JwtUserFactory(){}

    public static JwtUser create(User user){
        return new JwtUser(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                mapToGrantedAuthorities(user.getRoles()),
                user.isActive(),
                user.getCreatedDate(),
                user.getFirstname(),
                user.getLastname(),
                user.getActivatedDate(),
                user.getPasswordStamp()
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> roles){
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}
