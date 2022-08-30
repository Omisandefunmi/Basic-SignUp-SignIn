package com.example.BasicSignUpSignIn.data.repositories;

import com.example.BasicSignUpSignIn.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String username);
}
