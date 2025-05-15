package com.zup.gestaodeativos.services;

import com.zup.gestaodeativos.models.Notification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService{
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
