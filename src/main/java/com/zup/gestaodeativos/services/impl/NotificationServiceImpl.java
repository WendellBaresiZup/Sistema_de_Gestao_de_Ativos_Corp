package com.zup.gestaodeativos.services.impl;

import com.zup.gestaodeativos.models.Notification;
import com.zup.gestaodeativos.services.NotificationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Override
    public List<Notification> findAll() {
        return List.of();
    }

    @Override
    public Notification sendEmail(Notification notification) {
        return null;
    }

    @Override
    public Notification sendSNS(Notification notification) {
        return null;
    }
}
