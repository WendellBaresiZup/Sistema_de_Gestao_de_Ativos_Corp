package com.zup.gestaodeativos.repository;

import com.zup.gestaodeativos.models.MovementLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovementLogRepository extends JpaRepository<MovementLog, Long> {

}
