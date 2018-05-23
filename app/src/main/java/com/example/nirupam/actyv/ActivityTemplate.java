package com.example.nirupam.actyv;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ActivityTemplate {

   public String activity;
    String id;
    String timestamp;
    ActivityTemplate(){

    }

    ActivityTemplate(String Id,String Activity, String Timestamp){
        this.activity = Activity;
        this.timestamp = Timestamp;
        this.id = Id;

    }

    public String getActivity() {
        return activity;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getFormattedTimestamp() {
        return (new SimpleDateFormat("dd-MM-yyyy    HH:mm").format(Long.valueOf(timestamp)));
        //convert timestamp to long, and format date from the long value
    }
}
