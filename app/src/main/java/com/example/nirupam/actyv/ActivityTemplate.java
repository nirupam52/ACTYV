package com.example.nirupam.actyv;

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
}
