package com.example.pinpoint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private TextView tv_header;
    private EditText edit_name, edit_age, edit_email, edit_password;
    private Button btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        mAuth = FirebaseAuth.getInstance();

        tv_header = (TextView) findViewById(R.id.tv_registerheader);
        tv_header.setOnClickListener(this);

        btn_register = (Button) findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);

        edit_name = (EditText) findViewById(R.id.edit_registername);
        edit_age = (EditText) findViewById(R.id.edit_age);
        edit_email = (EditText) findViewById(R.id.edit_registeremail);
        edit_password = (EditText) findViewById(R.id.edit_registerpassword);

    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.tv_registerheader:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.btn_register:
                registerUser();
                break;
        }
    }

    private void registerUser(){
        String name = edit_name.getText().toString().trim();
        String age = edit_age.getText().toString().trim();
        String email = edit_email.getText().toString().trim();
        String password = edit_password.getText().toString().trim();

        if(name.isEmpty()){
            edit_name.setError("Please enter your full name!");
            edit_name.requestFocus();
            return;
        }

        if(age.isEmpty()){
            edit_age.setError("Please enter your age!");
            edit_age.requestFocus();
            return;
        }

        if(email.isEmpty()){
            edit_email.setError("Please enter your email!");
            edit_email.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edit_email.setError("Email address invalid!");
            edit_email.requestFocus();
            return;
        }

        if(password.length() < 6){
            edit_password.setError("Password needs to be longer than 6 characters!");
            edit_password.requestFocus();
            return;
        }

        if(password.isEmpty()){
            edit_age.setError("Please enter a password!");
            edit_age.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(name, age, email);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(RegisterUser.this, "New User has been registered!", Toast.LENGTH_LONG).show();
                                            }else{
                                                Toast.makeText(RegisterUser.this, "Registration failed.", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                        }else{
                            Toast.makeText(RegisterUser.this, "Registration failed.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
