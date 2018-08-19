package com.anizzzz.product.sssweaterhouse.repository;

import com.anizzzz.product.sssweaterhouse.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken,Long> {
    Optional<VerificationToken> findByToken(String token);
}
