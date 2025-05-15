package com.zup.gestaodeativos.services;

import com.zup.gestaodeativos.models.Notification;

import java.util.List;


public interface NotificationService {
    List<Notification> findAll();
    Notification sendEmail(Notification notification);
    Notification sendSNS(Notification notification);
}
