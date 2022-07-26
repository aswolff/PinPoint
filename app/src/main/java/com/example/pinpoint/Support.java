package com.example.pinpoint;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Support extends AppCompatActivity {
    TextView tv_header;
    Button submit;
    EditText support;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);

        submit = findViewById(R.id.btn_submit);
        support = findViewById(R.id.et_support);
        tv_header = findViewById(R.id.tv_shopheader);

        tv_header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Support.this, Welcome.class));
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Support.this, "Support ticket will be reviewed within 1-3 business days", Toast.LENGTH_SHORT).show();
                support.setText("");
                support.setHint("What do you need help with?");
            }
        });


    }

}