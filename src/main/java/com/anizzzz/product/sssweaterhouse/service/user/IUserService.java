package com.anizzzz.product.sssweaterhouse.service.user;

import com.anizzzz.product.sssweaterhouse.dto.ResponseMessage;
import com.anizzzz.product.sssweaterhouse.exceptionHandling.exceptions.DuplicateUserNameException;
import com.anizzzz.product.sssweaterhouse.model.Users;
import org.springframework.social.connect.Connection;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public interface IUserService {
    Optional<Users> findByUsername(String username);

    ResponseMessage saveUser(Users users, HttpServletRequest request);

    ResponseMessage saveAdmin(Users users);

    ResponseMessage resendVerificationToken(String username, HttpServletRequest request);

    ResponseMessage activateUser(String token);

    ResponseMessage sendResetPasswordToken(String username, HttpServletRequest request);

    ResponseMessage resetUserPassword(String token, String username,String password);

    //--------------Social login--------------------------

    Users findByUserId(String userId);

    Users findByUserIdAndAccountId(String userId, String accountId);

    String findAvailableUserName(String userName_prefix, String accountProviderId);

    Users createAppUser(Connection<?> connection) throws DuplicateUserNameException;
}
