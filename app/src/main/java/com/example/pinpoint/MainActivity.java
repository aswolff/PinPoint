package com.example.pinpoint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tv_register, tv_forgotpassword;
    private EditText edit_email, edit_password;
    private Button btn_signIn;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        tv_register = (TextView) findViewById(R.id.tv_mainregister);
        tv_register.setOnClickListener(this);

        edit_email = (EditText) findViewById(R.id.edit_mainemail);
        edit_password = (EditText) findViewById(R.id.edit_mainpassword);

        btn_signIn = (Button) findViewById(R.id.btn_login);
        btn_signIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.tv_mainregister:
                startActivity(new Intent(this, RegisterUser.class));
                break;
            case R.id.btn_login:
                userLogin();
                break;
        }
    }

    private void userLogin(){
        String email = edit_email.getText().toString().trim();
        String password = edit_password.getText().toString().trim();

        if(email.isEmpty()){
            edit_email.setError("Please provide an email address!");
            edit_email.requestFocus();
            return;
        }

        if(password.isEmpty()){
            edit_password.setError("Please enter your password!");
            edit_password.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if(user.isEmailVerified()){
                        startActivity(new Intent(MainActivity.this, Empty.class));
                    }else{
                        user.sendEmailVerification();
                        Toast.makeText(MainActivity.this, "Check Email to verify account!", Toast.LENGTH_LONG).show();
                    }

                }else{
                    Toast.makeText(MainActivity.this, "Email or Password are incorrect", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}