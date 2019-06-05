package com.anizzzz.product.sssweaterhouse.service.user;

import com.anizzzz.product.sssweaterhouse.model.PasswordResetToken;

import java.util.Optional;

public interface IPasswordResetService {
    Optional<PasswordResetToken> findByToken(String token);

    void save(PasswordResetToken passwordResetToken);
}
