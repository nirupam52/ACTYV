package com.example.nirupam.actyv;

import android.app.Notification;
import android.content.Intent;
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

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
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



        welcome.setText("Welcome user  " + user.getEmail()  );
        NotificationScheduler.scheduleNotifications(this);
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
                Toast.makeText(this, "this should launch settings", Toast.LENGTH_SHORT).show();
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
}
