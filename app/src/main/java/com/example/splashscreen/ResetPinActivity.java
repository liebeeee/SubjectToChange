package com.example.splashscreen;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ResetPinActivity extends AppCompatActivity {
    private EditText pin1, pin2, pin3, pin4, repin1, repin2, repin3, repin4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reset_pin);

        pin1 = findViewById(R.id.pin1);
        pin2 = findViewById(R.id.pin2);
        pin3 = findViewById(R.id.pin3);
        pin4 = findViewById(R.id.pin4);
        repin1 = findViewById(R.id.repin1);
        repin2 = findViewById(R.id.repin2);
        repin3 = findViewById(R.id.repin3);
        repin4 = findViewById(R.id.repin4);
        Button submitButton = findViewById(R.id.button);

        setupPinInputs(pin1, pin2, pin3, pin4);
        setupPinInputs(repin1, repin2, repin3, repin4);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResetPinActivity.this, LogIn.class);
            }
        });
        submitButton.setOnClickListener(view -> {
            String pin = pin1.getText().toString() + pin2.getText().toString() + pin3.getText().toString() + pin4.getText().toString();
            String repin = repin1.getText().toString() + repin2.getText().toString() + repin3.getText().toString() + repin4.getText().toString();

            if (pin.length() != 4) {
                Toast.makeText(ResetPinActivity.this, "Please enter your 4-digit PIN", Toast.LENGTH_SHORT).show();
            } else if (repin.length() != 4) {
                Toast.makeText(ResetPinActivity.this, "Please re-enter your 4-digit PIN", Toast.LENGTH_SHORT).show();
            } else if (!pin.equals(repin)) {
                Toast.makeText(ResetPinActivity.this, "PINs do not match", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ResetPinActivity.this, "PIN matched", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ResetPinActivity.this, OTP_Sign.class);
                startActivity(intent);

            }
            ;

        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;




        });


    }
    private void setupPinInputs (EditText...editTexts){
        for (int i = 0; i < editTexts.length; i++) {
            final int index = i;
            editTexts[i].addTextChangedListener(new TextWatcher() {
                @Override
                public void afterTextChanged(Editable s) {
                    if (s != null && s.length() == 1) {
                        editTexts[index].setBackgroundResource(R.drawable.edit_text_underline_filled);
                        if (index < editTexts.length - 1) {
                            editTexts[index + 1].requestFocus();
                        }
                    } else if (s != null && s.length() == 0) {
                        editTexts[index].setBackgroundResource(R.drawable.edit_text_underline_empty);
                        if (index > 0) {
                            editTexts[index - 1].requestFocus();
                        }
                    }
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }
            });
        }
    }
}