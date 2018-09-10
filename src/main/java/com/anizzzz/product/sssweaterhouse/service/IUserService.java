package com.anizzzz.product.sssweaterhouse.service;

import com.anizzzz.product.sssweaterhouse.dto.ResponseMessage;
import com.anizzzz.product.sssweaterhouse.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public interface IUserService {
    Page<User> findAll(Pageable pageable);

    Optional<User> findByUsername(String username);

    ResponseMessage save(User user, HttpServletRequest request);

    ResponseMessage resendVerificationToken(String username);

    ResponseMessage activateUser(String token);

    ResponseMessage sendResetPasswordToken(String username);

    ResponseMessage resetUserPassword(String token, String username,String password);
}
