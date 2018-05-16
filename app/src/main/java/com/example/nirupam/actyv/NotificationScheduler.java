package com.example.nirupam.actyv;

import android.content.Context;


import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

import java.util.concurrent.TimeUnit;

//

public class NotificationScheduler {

    private static final int WINDOW_START = 1*60*60*2; //2 hours. yes i'm  an idiot.
    private static final int WINDOW_END = WINDOW_START*2;

    private static final String NOTIFICATION_JOB_TAG = "notification-scheduler-tag";

    private static boolean sInitialized;


    synchronized public static void scheduleNotifications(final Context context){
        if(sInitialized) return;
        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher jobDispatcher = new FirebaseJobDispatcher(driver);
        Job activityUpdateJob = jobDispatcher.newJobBuilder()
                .setService(ActivityNotificationJobService.class)
                .setTag(NOTIFICATION_JOB_TAG)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(WINDOW_START,WINDOW_END))
                .setReplaceCurrent(true)
                .build();

        jobDispatcher.schedule(activityUpdateJob);
        sInitialized = true;

    }

}
