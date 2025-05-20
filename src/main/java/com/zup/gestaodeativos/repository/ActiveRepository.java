package com.zup.gestaodeativos.repository;

import com.zup.gestaodeativos.models.Active;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActiveRepository extends JpaRepository<Active, Long> {
    Optional<Active> findBySerialNumber(String serialNumber);
    Optional<Active> findByIdAndAvailableTrue(Long id);
}