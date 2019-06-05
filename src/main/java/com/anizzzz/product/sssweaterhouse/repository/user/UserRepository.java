package com.anizzzz.product.sssweaterhouse.repository.user;

import com.anizzzz.product.sssweaterhouse.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {
    Optional<User> findByUsername(String username);

    Optional<User> findByUserIdAndAccountId(String userId, String accountId);

    Optional<User> findByUserId(String userId);

    Optional<User> findByVerificationToken_Token(String token);
}
