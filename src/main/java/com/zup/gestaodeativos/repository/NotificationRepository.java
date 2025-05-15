package com.zup.gestaodeativos.repository;

import com.zup.gestaodeativos.models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
