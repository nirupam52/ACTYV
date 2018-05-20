package com.example.nirupam.actyv;

public class ActivityTemplate {

    String activity;
    String id;
    String timestamp;
    ActivityTemplate(){

    }

    ActivityTemplate(String Id,String Activity, String Timestamp){
        this.activity = Activity;
        this.timestamp = Timestamp;
        this.id = Id;

    }


}
