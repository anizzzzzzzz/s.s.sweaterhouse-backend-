package com.anizzzz.product.sssweaterhouse.service.user;

import com.anizzzz.product.sssweaterhouse.model.VerificationToken;

import java.util.Optional;

public interface IVerificationTokenService {
    Optional<VerificationToken> findByToken(String token);

    void save(VerificationToken verificationToken);
}
