package com.anizzzz.product.sssweaterhouse.service.serviceImpl;

import com.anizzzz.product.sssweaterhouse.model.User;
import com.anizzzz.product.sssweaterhouse.repository.UserRepository;
import com.anizzzz.product.sssweaterhouse.security.jwtutil.JwtUserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username){
        Optional<User> user = userRepository.findByUsername(username);

        if(user.isPresent()){
            return JwtUserFactory.create(user.get());
        }
        else{
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        }
    }
}
