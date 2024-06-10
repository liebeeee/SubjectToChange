package com.example.splashscreen;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;


public class SignUp extends AppCompatActivity {

    private EditText editTextPhone;
    private EditText pin1;
    private EditText pin2;
    private EditText pin3;
    private EditText pin4;
    private EditText repin1;
    private EditText repin2;
    private EditText repin3;
    private EditText repin4;
    private CheckBox tAndP;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editTextPhone = findViewById(R.id.editTextPhone);
        pin1 = findViewById(R.id.pin1);
        pin2 = findViewById(R.id.pin2);
        pin3 = findViewById(R.id.pin3);
        pin4 = findViewById(R.id.pin4);
        repin1 = findViewById(R.id.repin1);
        repin2 = findViewById(R.id.repin2);
        repin3 = findViewById(R.id.repin3);
        repin4 = findViewById(R.id.repin4);
        tAndP = findViewById(R.id.tAndP);
        Button submitButton = findViewById(R.id.signUp);

        setupPinInputs(pin1, pin2, pin3, pin4);
        setupPinInputs(repin1, repin2, repin3, repin4);

        submitButton.setOnClickListener(v -> {
            String phone = editTextPhone.getText().toString().trim();
            String pin = pin1.getText().toString() + pin2.getText().toString() + pin3.getText().toString() + pin4.getText().toString();
            String repin = repin1.getText().toString() + repin2.getText().toString() + repin3.getText().toString() + repin4.getText().toString();

            if (phone.isEmpty()) {
                Toast.makeText(SignUp.this, "Please enter your phone number", Toast.LENGTH_SHORT).show();
            } else if (pin.length() != 4) {
                Toast.makeText(SignUp.this, "Please enter your 4-digit PIN", Toast.LENGTH_SHORT).show();
            } else if (repin.length() != 4) {
                Toast.makeText(SignUp.this, "Please re-enter your 4-digit PIN", Toast.LENGTH_SHORT).show();
            } else if (!pin.equals(repin)) {
                Toast.makeText(SignUp.this, "PINs do not match", Toast.LENGTH_SHORT).show();
            } else if (!tAndP.isChecked()) {
                Toast.makeText(SignUp.this, "Please agree to the terms and conditions", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(SignUp.this, "PIN matched", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SignUp.this, OTP_Sign.class);
                startActivity(intent);
            }
        });
    }

    private void setupPinInputs(EditText... editTexts) {
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
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}
            });
        }
    }
}