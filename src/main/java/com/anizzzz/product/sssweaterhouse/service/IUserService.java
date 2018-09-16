package com.anizzzz.product.sssweaterhouse.service;

import com.anizzzz.product.sssweaterhouse.dto.ResponseMessage;
import com.anizzzz.product.sssweaterhouse.exceptionHandling.exceptions.DuplicateUserNameException;
import com.anizzzz.product.sssweaterhouse.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.social.connect.Connection;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public interface IUserService {
    Optional<User> findByUsername(String username);

    ResponseMessage save(User user, HttpServletRequest request);

    ResponseMessage resendVerificationToken(String username, HttpServletRequest request);

    ResponseMessage activateUser(String token);

    ResponseMessage sendResetPasswordToken(String username, HttpServletRequest request);

    ResponseMessage resetUserPassword(String token, String username,String password);

    //--------------Social login--------------------------

    User findByUserId(String userId);

    User findByUserIdAndAccountId(String userId,String accountId);

    String findAvailableUserName(String userName_prefix, String accountProviderId);

    User createAppUser(Connection<?> connection) throws DuplicateUserNameException;
}
