package com.example.nirupam.actyv;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Random;

//                              THIS IS THE SIGN UP ACTIVITY

public class MainActivity extends AppCompatActivity implements View.OnClickListener  {
    private EditText email;
    private EditText pwd1;
    private EditText pwd2;

    private Button signUp;
    private Button Login;

    private FirebaseAuth firebaseAuth;

    private int randNum = (new Random()).nextInt(100000) +1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        //checking for existing login
        if(firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(this,ViewActivity.class));
        }
        email = ((EditText) findViewById(R.id.EmailET));
        pwd1 = (EditText) findViewById(R.id.PwdET);
        pwd2 = (EditText) findViewById(R.id.RPwdET);

        signUp = (Button) findViewById(R.id.signupButton);
        Login = (Button) findViewById(R.id.loginButton);
        signUp.setOnClickListener(this);
        Login.setOnClickListener(this);

    }

    public void registerUser(){
        String Email = email.getText().toString().trim();
        String Pwd1 = pwd1.getText().toString().trim();
        String Pwd2 = pwd2.getText().toString().trim();

        if(TextUtils.isEmpty(Email) || TextUtils.isEmpty(Pwd1) || TextUtils.isEmpty(Pwd2)){
            Toast.makeText(this, "One or more fields is empty", Toast.LENGTH_LONG).show();
        }

        else{
            if(Pwd1.equals(Pwd2)){
                firebaseAuth.createUserWithEmailAndPassword(Email,Pwd1).addOnCompleteListener(this,
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()) {
                                    Toast.makeText(MainActivity.this, "SIGN UP COMPLETE !", Toast.LENGTH_LONG).show();
                                    finish();
                                    startActivity(new Intent(getBaseContext(),ViewActivity.class));
                                    //@Todo implement sending a hash code with each user sign in and verify with existing code for xtra security.
                                }
                                else{

                                    Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();

                                }

                                //intent_profile.putExtra("RANDOM_NUMBER", Integer.toString(randNum));
                                startActivity(new Intent(getBaseContext(),ProfileActivity.class));
                            }
                        });


            }
            else{
                Toast.makeText(this, "outer ELSE error", Toast.LENGTH_SHORT).show();
            }
        }


    }


    @Override
    public void onClick(View view) {
        //add this in an async task
        new SignupAsyncTask().execute(view);
    }

    private class SignupAsyncTask extends AsyncTask<View,Void,Void>{


        @Override
        protected Void doInBackground(View... views) {
            View receviedView = views[0];
            if(receviedView == signUp){
                registerUser();
            }
            else if (receviedView == Login){
                Intent intent_login = new Intent(getBaseContext(),LoginActivity.class);
                startActivity(intent_login);
            }


            return null;
        }
    }
}
