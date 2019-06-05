package com.anizzzz.product.sssweaterhouse.service.user.impl;

import com.anizzzz.product.sssweaterhouse.model.PasswordResetToken;
import com.anizzzz.product.sssweaterhouse.repository.user.PasswordResetRepository;
import com.anizzzz.product.sssweaterhouse.service.user.IPasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PasswordResetService implements IPasswordResetService {
    private final PasswordResetRepository passwordResetRepository;

    @Autowired
    public PasswordResetService(PasswordResetRepository passwordResetRepository) {
        this.passwordResetRepository = passwordResetRepository;
    }

    @Override
    public Optional<PasswordResetToken> findByToken(String token) {
        return passwordResetRepository.findByToken(token);
    }

    @Override
    public void save(PasswordResetToken passwordResetToken) {
        passwordResetRepository.save(passwordResetToken);
    }
}
