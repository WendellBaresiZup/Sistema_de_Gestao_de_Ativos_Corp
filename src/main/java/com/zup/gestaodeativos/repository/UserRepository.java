package com.zup.gestaodeativos.repository;

import com.zup.gestaodeativos.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByNomeIgnoreCase(String nome);
}