package com.example.nirupam.actyv;

import android.app.Notification;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener, SharedPreferences.OnSharedPreferenceChangeListener {
    private TextView welcome;

    private FirebaseAuth firebaseAuth;
    private Button button;
    private FloatingActionButton fab_act;
    private Button view_act;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        firebaseAuth = FirebaseAuth.getInstance();
        //checking for user login
        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        }
        welcome = (TextView) findViewById(R.id.welcTV);
        button = (Button) findViewById(R.id.logout_button);
        fab_act = (FloatingActionButton) findViewById(R.id.add_act_fab);
        view_act = (Button) findViewById(R.id.view_act);

        button.setOnClickListener(this);
        fab_act.setOnClickListener(this);
        view_act.setOnClickListener(this);

         FirebaseUser user = firebaseAuth.getCurrentUser();

        NotificationScheduler.scheduleNotifications(this);

        welcome.setText("Welcome user  " + user.getEmail()  );
        sharedPrefSettings();

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
    }

    //something seems to be going horribly wrong down here !!
    /*
    private void notificationToggle(Boolean bool){
        if(bool == true){
            NotificationScheduler.scheduleNotifications(this);
        }
        else return;
    }
    */

    private void sharedPrefSettings(){

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        //notificationToggle(sharedPreferences.getBoolean("show_notifications",true));
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){

            case R.id.menu_settings:
                startActivity(new Intent(getBaseContext(),SettingsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onClick(View view) {
        if(view == button){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        }
        else if(view == fab_act){
            startActivity(new Intent(this, AddActivity.class));
        }

        else if(view == view_act){
            startActivity(new Intent(this, ViewActivity.class));
        }

    }
    //there's something fish here too

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        /*
        if(s.equals("show_notifications")){
            notificationToggle(sharedPreferences.getBoolean(s,true));
        }
        */
    }

}
