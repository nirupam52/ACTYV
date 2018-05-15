package com.example.nirupam.actyv;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;


/**
 * Created by Yugansh Tyagi on 15-05-2018.
 */

public class Notifications {

    private static final int ACTIVITY_UPDATE__PENDING_INTENT_ID = 6666;
    private static final String ACTIVITY_UPDATE_NOTIFICATION_CHANNEL_ID = "activity_update_reminder_channel";

    private static PendingIntent contentIntent(Context context) {

        Intent startProfileActivity = new Intent(context, ProfileActivity.class);
        return PendingIntent.getActivity(context, ACTIVITY_UPDATE__PENDING_INTENT_ID, startProfileActivity, PendingIntent.FLAG_UPDATE_CURRENT);

    }

    public static void notificationReminder(Context context) {
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    ACTIVITY_UPDATE_NOTIFICATION_CHANNEL_ID, "Some Name",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(mChannel);
        }


        NotificationCompat.Builder notificationBuilder = new NotificationCompat
                .Builder(context, ACTIVITY_UPDATE_NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Test Notification")
                .setContentText("Some big text for notification");

        notificationManager.notify(ACTIVITY_UPDATE__PENDING_INTENT_ID, notificationBuilder.build());
    }

}

