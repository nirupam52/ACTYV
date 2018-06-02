package com.example.nirupam.actyv;


import android.os.AsyncTask;

//the below libraries are the integral part of this class. without them there is complete chaos.

import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.firebase.jobdispatcher.RetryStrategy;

//creating a job service to perform the task in the background using asynctask

public class ActivityNotificationJobService extends JobService {
    private AsyncTask backgrounTask;
    @Override
    public boolean onStartJob(final JobParameters jobParameters) {
        backgrounTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                ActivityUpdateReminder.executeTask(getBaseContext(),ActivityUpdateReminder.GENERATE_NOTIFICATION);
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                jobFinished(jobParameters,false);
            }


        };
        backgrounTask.execute();
        return true;

    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        if(backgrounTask != null) return backgrounTask.cancel(true);
        return true;
    }
}
