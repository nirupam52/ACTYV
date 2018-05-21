package com.example.nirupam.actyv;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText activityEnter;
    private Button activitySubmit;

    DatabaseReference databaseActivity;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        activityEnter = (EditText) findViewById(R.id.enter_activity);
        activitySubmit = (Button) findViewById(R.id.submit_activity);

        activitySubmit.setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        databaseActivity = FirebaseDatabase.getInstance().getReference("activities of : " + user.getUid());


    }

    private void addActivity(){
        String activityToEnter = activityEnter.getText().toString();

        if(!TextUtils.isEmpty(activityToEnter)){

            String id = databaseActivity.push().getKey();

            Long longTs = System.currentTimeMillis()/1000;
            String stringTs = longTs.toString();

            ActivityTemplate activityTemplate = new ActivityTemplate(id,activityToEnter,stringTs);

            databaseActivity.child(id).setValue(activityTemplate);

            Toast.makeText(this, "ADDED !", Toast.LENGTH_LONG).show();
            activityEnter.getText().clear();

        }
        else{
            Toast.makeText(this, "not added !", Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void onClick(View view) {
        if(view == activitySubmit){
            addActivity();
        }

    }
}
