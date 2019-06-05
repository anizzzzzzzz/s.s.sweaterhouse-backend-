package com.anizzzz.product.sssweaterhouse.service.social;

import com.anizzzz.product.sssweaterhouse.model.Users;
import com.anizzzz.product.sssweaterhouse.service.user.IUserService;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;

public class ConnectionSignupImpl implements ConnectionSignUp {
    private final IUserService iUserService;

    public ConnectionSignupImpl(IUserService iUserService){
        this.iUserService=iUserService;
    }

    @Override
    public String execute(Connection<?> connection) {
        Users users = iUserService.createAppUser(connection);
        if(users !=null)
            return users.getUserId();
        else
            return null;
    }
}
