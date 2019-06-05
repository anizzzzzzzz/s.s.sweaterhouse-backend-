package com.anizzzz.product.sssweaterhouse.service.social;

import com.anizzzz.product.sssweaterhouse.model.Users;
import com.anizzzz.product.sssweaterhouse.service.user.IUserService;
import com.anizzzz.product.sssweaterhouse.social.SocialUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SocialUserDetailsServiceImpl implements SocialUserDetailsService {
    private final IUserService iUserService;

    @Autowired
    public SocialUserDetailsServiceImpl(IUserService iUserService) {
        this.iUserService = iUserService;
    }

    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        Users users = iUserService.findByUserId(userId);
        if(users == null){
            throw new UsernameNotFoundException("Cannot find users by id " + userId);
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        users.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));

        return new SocialUser(
                users.getUsername(),
                users.getPassword(),
                userId,
                users.getAccountId(),
                authorities
        );
    }
}
