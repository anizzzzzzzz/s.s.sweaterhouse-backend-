package com.anizzzz.product.sssweaterhouse.repository.user;

import com.anizzzz.product.sssweaterhouse.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken,String> {
    Optional<VerificationToken> findByToken(String token);
}
