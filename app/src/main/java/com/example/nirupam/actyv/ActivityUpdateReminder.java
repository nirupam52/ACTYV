package com.example.nirupam.actyv;

import android.content.Context;

//creating a task to display the notification which will be executed by the service in background

public class ActivityUpdateReminder {
    public static  final  String GENERATE_NOTIFICATION = "generate-notification";
    public static void executeTask(Context context, String action){
        if(GENERATE_NOTIFICATION.equals(action)){
            Notifications.notificationReminder(context);
        }

    }
}
