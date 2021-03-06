package com.anizzzz.product.sssweaterhouse.security.jwtutil;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;

public class JwtUser implements UserDetails {
    private final String id;
    private final String username;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;
    private final boolean enabled;
    private final Date createdDate;
    private final String firstname;
    private final String lastname;
    private final Date activatedDate;
    private final Date passwordStamp;

    public JwtUser(String id,
                   String username,
                   String password,
                   Collection<? extends GrantedAuthority> authorities,
                   boolean enabled,
                   Date createdDate,
                   String firstname,
                   String lastname,
                   Date activatedDate,
                   Date passwordStamp){
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.enabled = enabled;
        this.createdDate = createdDate;
        this.firstname = firstname;
        this.lastname = lastname;
        this.activatedDate=activatedDate;
        this.passwordStamp=passwordStamp;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public Date getCreatedDate(){return createdDate;}

    public String getFirstname(){return firstname;}

    public String getLastname(){return lastname;}

    public Date getActivatedDate(){return activatedDate;}

    public Date getPasswordStamp(){return passwordStamp;}

    public String getId() {
        return id;
    }
}
