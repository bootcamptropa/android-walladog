package com.walladog.walladog.utils;

/**
 * Created by hadock on 10/01/16.
 *
 */

public class WDEventNotification<T> {
    public final int mNotificationType;
    public final String mMessage;
    public final T mNotificationObject;

    public int getNotificationType() {
        return mNotificationType;
    }

    public String getMessage() {
        return mMessage;
    }

    public T getNotificationObject() {
        return mNotificationObject;
    }

    public WDEventNotification(int notificationType1, String message, T object) {
        mNotificationType = notificationType1;
        mMessage = message;
        mNotificationObject = object;
    }
}
