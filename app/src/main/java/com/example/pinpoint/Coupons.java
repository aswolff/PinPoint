package com.example.pinpoint;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class Coupons extends AppCompatActivity {

    TextView tv_header;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupons);

        tv_header = (TextView) findViewById(R.id.tv_couponsheader);
        tv_header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Coupons.this, Welcome.class));
            }
        });
        webView = (WebView) findViewById(R.id.wv_coupons);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://www.coupons.com/");
    }
}