package com.walladog.walladog.notifications;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.walladog.walladog.R;
import com.walladog.walladog.controllers.activities.SplashActivity;
import com.walladog.walladog.models.WDNotification;
import com.walladog.walladog.utils.DBAsyncTasks;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hadock on 15/12/15.
 *
 */

public class WDGcmIntentService extends IntentService {

    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    private final static String TAG = "WDGcmIntentService";

    public WDGcmIntentService() {
        super("WDGcmIntentService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {

        Bundle extras = intent.getExtras();
        Log.d(TAG, "Notification Data Json :" + extras.getString("message"));

        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty()) {
            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
                sendNotification("Send error: " + extras.toString());
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
                sendNotification("Deleted messages on server: " + extras.toString());   // If it's a regular GCM message, do some work.
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) { // This loop represents the service doing some work.
                /*for (int i = 0; i < 5; i++) {
                    Log.d(TAG," Working... " + (i + 1) + "/5 @ "
                            + SystemClock.elapsedRealtime());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                }
                Log.i(TAG, "Completed work @ " + SystemClock.elapsedRealtime());*/
                WDNotification noti = new WDNotification(extras.getString("title"),extras.getString("message"),extras.getString("author"));
                saveNotificationToDb(noti);
                sendNotification(extras.getString("message"));
            }
        } // Release the wake lock provided by the WakefulBroadcastReceiver.
        WDGcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    private void sendNotification(String msg) {
        mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, SplashActivity.class), 0);

        NotificationCompat.Builder mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.walladogsmall)
                .setContentTitle("Walladog!")
                .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                .setContentText(msg)
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }

    private void saveNotificationToDb(WDNotification notification){
        List<WDNotification> mNotiList = new ArrayList<WDNotification>();
        mNotiList.add(notification);
        DBAsyncTasks<WDNotification> task = new DBAsyncTasks<WDNotification>(DBAsyncTasks.TASK_SAVE_LIST,new WDNotification(), getApplicationContext(), mNotiList, new DBAsyncTasks.OnItemsSavedToDBListener() {
            @Override
            public void onItemsSaved(Boolean saved) {
                Log.v(TAG,"New WDNotification saved to DB");
            }
        });
        task.execute();
    }
}