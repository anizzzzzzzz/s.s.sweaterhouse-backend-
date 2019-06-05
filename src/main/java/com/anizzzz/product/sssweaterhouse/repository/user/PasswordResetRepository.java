package com.anizzzz.product.sssweaterhouse.repository.user;

import com.anizzzz.product.sssweaterhouse.model.user.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetRepository extends JpaRepository<PasswordResetToken,String>{
    Optional<PasswordResetToken> findByToken(String token);
}
