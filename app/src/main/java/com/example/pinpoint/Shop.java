package com.example.pinpoint;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Shop extends AppCompatActivity {
    public static String aurl = "https://www.amazon.com/ref=nav_logo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        getFragmentManager().findFragmentById(R.id.tickerlist_display);
        getFragmentManager().findFragmentById(R.id.ticker_infoWeb);

    }
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_shopheader:
                startActivity(new Intent(this, Welcome.class));
                break;
        }
    }

}