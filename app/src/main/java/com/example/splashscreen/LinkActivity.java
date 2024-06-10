package com.example.splashscreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class LinkActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link);
        // Your LinkActivity setup code
        Button backToLogin = findViewById(R.id.backToLogIn);
        backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LinkActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        Button resetP = findViewById(R.id.resetP);
        resetP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LinkActivity.this, ResetPinActivity.class);
                startActivity(intent);
            }
        });
    }
}