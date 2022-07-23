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
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity implements View.OnClickListener{

    private TextView tv_header;
    private EditText edit_email;
    private Button btn_resetPassword;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        tv_header = (TextView) findViewById(R.id.tv_resetheader);
        edit_email = (EditText) findViewById(R.id.edit_resetemail);
        btn_resetPassword = (Button) findViewById(R.id.btn_resetpassword);

        tv_header.setOnClickListener(this);

        auth = FirebaseAuth.getInstance();
    }

    private void resetPassword(){
        String email = edit_email.getText().toString().trim();

        if (email.isEmpty()){
            edit_email.setError("Please enter Email!");
            edit_email.requestFocus();
            return;
        }
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(ResetPassword.this, "Check your Email to set your password", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ResetPassword.this, "Email does not exist!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.tv_resetheader:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.btn_resetpassword:
                resetPassword();
                break;
        }
    }
}