package com.anizzzz.product.sssweaterhouse.repository;

import com.anizzzz.product.sssweaterhouse.model.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetRepository extends JpaRepository<PasswordResetToken,Long>{
    Optional<PasswordResetToken> findByToken(String token);
}
