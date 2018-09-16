package com.anizzzz.product.sssweaterhouse.social;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class SocialUser extends org.springframework.social.security.SocialUser {
    private static long serialVersionUID=-5246117266247684905L;

    private String userId, accountId;

    public SocialUser(String username,
                      String password,
                      String userId,
                      String accountId,
                      Collection<? extends GrantedAuthority> authorities){
        super(username, password, true, true, true, true, authorities);
        this.userId = userId;
        this.accountId = accountId;
    }

    public String getAccountId(){return accountId;}

    public String getUserId(){return userId;}
}
