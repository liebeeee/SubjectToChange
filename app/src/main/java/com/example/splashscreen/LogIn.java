package com.example.splashscreen;

import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LogIn extends AppCompatActivity {
    private EditText num1, num2, num3, num4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        num1 = findViewById(R.id.num1);
        num2 = findViewById(R.id.num2);
        num3 = findViewById(R.id.num3);
        num4 = findViewById(R.id.num4);

        setupOtpInputs();

        Button loginBut = findViewById(R.id.loginBut);
        loginBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click event here
                Intent intent = new Intent(LogIn.this, HomeScreen.class);
                startActivity(intent);

            }
        });
        Button resetButt = findViewById(R.id.resetButt);
        resetButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click event here
                Intent intent = new Intent(LogIn.this, ResetPinActivity.class);

            }
        });
    }
    private void setupOtpInputs() {
        EditText[] otps = {num1, num2, num3, num4};

        for (int i = 0; i < otps.length; i++) {
            final int index = i;
            otps[i].addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s != null && s.length() == 1 && index < otps.length - 1) {
                        otps[index + 1].requestFocus();
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {}
            });

            otps[i].setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL) {
                        if (otps[index].getText().toString().isEmpty() && index > 0) {
                            otps[index - 1].requestFocus();
                            otps[index - 1].setText("");
                        }
                    }
                    return false;
                }
            });
        }

    }
}