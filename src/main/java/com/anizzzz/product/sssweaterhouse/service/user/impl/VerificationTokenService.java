package com.anizzzz.product.sssweaterhouse.service.user.impl;

import com.anizzzz.product.sssweaterhouse.model.VerificationToken;
import com.anizzzz.product.sssweaterhouse.repository.user.VerificationTokenRepository;
import com.anizzzz.product.sssweaterhouse.service.user.IVerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VerificationTokenService implements IVerificationTokenService {
    private final VerificationTokenRepository verificationTokenRepository;

    @Autowired
    public VerificationTokenService(VerificationTokenRepository verificationTokenRepository) {
        this.verificationTokenRepository = verificationTokenRepository;
    }

    @Override
    public Optional<VerificationToken> findByToken(String token) {
        return verificationTokenRepository.findByToken(token);
    }

    @Override
    public void save(VerificationToken verificationToken) {
        verificationTokenRepository.save(verificationToken);
    }
}
