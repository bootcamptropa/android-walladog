package com.walladog.walladog.utils;

import com.walladog.walladog.models.WDNotification;

import java.util.List;

/**
 * Created by hadock on 10/01/16.
 *
 */

public class NotificationDataEvent {
    public final String message;
    public final int notificationType;

    public NotificationDataEvent(String message, int notificationType) {
        this.message = message;
        this.notificationType = notificationType;
    }
}
