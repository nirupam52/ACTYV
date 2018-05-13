package com.example.nirupam.actyv;

import android.content.Intent;
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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText login_email;
    private EditText login_password;
    private Button login_button;
    private Button signup_button;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        //checking for existing login. if yes then directly opens profile
        if(firebaseAuth.getCurrentUser() != null){
            startActivity(new Intent(this, ProfileActivity.class));

        }

        login_email = (EditText) findViewById(R.id.EmaileditText);
        login_password = (EditText) findViewById(R.id.pwdEditText);
        login_button = (Button) findViewById(R.id.login_button_login);
        signup_button = (Button) findViewById(R.id.signup_button_login);

        login_button.setOnClickListener(this);
        signup_button.setOnClickListener(this);
    }

    public void Login(){
        String email = login_email.getText().toString().trim();
        String pwd = login_password.getText().toString().trim();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(pwd)){
            Toast.makeText(this, "one or more fields are empty", Toast.LENGTH_LONG).show();
        }
        else{
            firebaseAuth.signInWithEmailAndPassword(email,pwd).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        //opening the profile activity after logging in

                        startActivity(new Intent(getBaseContext(), ProfileActivity.class));
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "error", Toast.LENGTH_SHORT).show();
                    }


                }
            });
        }

    }
    @Override
    public void onClick(View view) {
        if(view == login_button){
            Login();
            finish();
            startActivity(new Intent(getBaseContext(),ProfileActivity.class));
        }
        else if(view == signup_button){
            finish();;
            startActivity(new Intent(getBaseContext(),MainActivity.class));
        }
    }
}
