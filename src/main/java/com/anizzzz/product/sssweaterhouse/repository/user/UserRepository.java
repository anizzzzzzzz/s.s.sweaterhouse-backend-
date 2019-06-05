package com.anizzzz.product.sssweaterhouse.repository.user;

import com.anizzzz.product.sssweaterhouse.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users,String> {
    Optional<Users> findByUsername(String username);

    Optional<Users> findByUserIdAndAccountId(String userId, String accountId);

    Optional<Users> findByUserId(String userId);

    Optional<Users> findByVerificationToken_Token(String token);
}
