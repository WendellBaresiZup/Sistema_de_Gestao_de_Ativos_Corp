package com.zup.gestaodeativos.repository;

import com.zup.gestaodeativos.models.AssetAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface AssetAssignmentRepository extends JpaRepository<AssetAssignment, Long> {
    Optional<AssetAssignment> findByActiveIdAndIsActiveTrue(Long activeId);
    List<AssetAssignment> findByIsActiveTrue();
    List<AssetAssignment> findByUserId(Long userId);
}