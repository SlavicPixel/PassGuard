package com.upi.passguard;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.ui.AppBarConfiguration;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.upi.passguard.databinding.ActivityPasswordGeneratorBinding;
import com.upi.passguard.models.LowerCaseGenerator;
import com.upi.passguard.models.NumericGenerator;
import com.upi.passguard.models.PasswordGenerator;
import com.upi.passguard.models.SpecialCharGenerator;
import com.upi.passguard.models.UpperCaseGenerator;

import java.util.Objects;

public class PasswordGeneratorActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityPasswordGeneratorBinding binding;
    TextView generatedPW_TV, passwordLength_TV;
    ImageView generatePW_IV, copyPW_IV;
    SeekBar seekBarPWlength;
    SwitchMaterial uppercaseSwitch, lowercaseSwitch, numbersSwitch, specialCharsSwitch;
    Button savePassword_Btn;
    int passwordLength = 12;
    String password;

    private static final String MY_PREFS = "switch_prefs";
    private static final String uppercaseSwitch_STATUS = "uppercaseSwitch_status";
    private static final String lowercaseSwitch_STATUS = "lowercaseSwitch_status";
    private static final String numbersSwitch_STATUS = "numbersSwitch_status";
    private static final String specialCharsSwitch_STATUS = "specialCharsSwitch_status";
    private static final String passwordLengthSeekBar_STATUS = "passwordLengthSeekBar_status";
    private static final String passwordLengthTextView_STATUS = "passwordLengthTextView_status";

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPasswordGeneratorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Password Generator");

        generatedPW_TV = findViewById(R.id.generatedPWTV);
        passwordLength_TV = findViewById(R.id.lengthNumTV);
        generatePW_IV = findViewById(R.id.generatePW);
        copyPW_IV = findViewById(R.id.copyPW);
        seekBarPWlength = findViewById(R.id.seekBarPWlength);
        uppercaseSwitch = findViewById(R.id.uppercaseSwitch);
        lowercaseSwitch = findViewById(R.id.lowercaseSwitch);
        numbersSwitch = findViewById(R.id.numbersSwitch);
        specialCharsSwitch = findViewById(R.id.specialCharsSwitch);
        savePassword_Btn = findViewById(R.id.savePassword);


        sharedPreferences = getSharedPreferences(MY_PREFS, MODE_PRIVATE);
        editor = getSharedPreferences(MY_PREFS, MODE_PRIVATE).edit();

        passwordLength_TV.setText(String.valueOf(sharedPreferences.getInt(passwordLengthTextView_STATUS, passwordLength)));
        seekBarPWlength.setProgress(sharedPreferences.getInt(passwordLengthSeekBar_STATUS, 12));
        uppercaseSwitch.setChecked(sharedPreferences.getBoolean(uppercaseSwitch_STATUS, false));
        lowercaseSwitch.setChecked(sharedPreferences.getBoolean(lowercaseSwitch_STATUS, true));
        numbersSwitch.setChecked(sharedPreferences.getBoolean(numbersSwitch_STATUS, true));
        specialCharsSwitch.setChecked(sharedPreferences.getBoolean(specialCharsSwitch_STATUS, false));


        setPassword(passwordLength);

        seekBarPWlength.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                passwordLength = progress;
                passwordLength_TV.setText(String.format("%d", progress));

                editor.putInt(passwordLengthTextView_STATUS, passwordLength);
                editor.putInt(passwordLengthSeekBar_STATUS, passwordLength);
                editor.apply();

                setPassword(passwordLength);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        uppercaseSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPassword(passwordLength);
                editor.putBoolean(uppercaseSwitch_STATUS, uppercaseSwitch.isChecked());
                editor.apply();
            }
        });

        lowercaseSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPassword(passwordLength);
                editor.putBoolean(lowercaseSwitch_STATUS, lowercaseSwitch.isChecked());
                editor.apply();
            }
        });

        numbersSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPassword(passwordLength);
                editor.putBoolean(numbersSwitch_STATUS, numbersSwitch.isChecked());
                editor.apply();
            }
        });

        specialCharsSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPassword(passwordLength);
                editor.putBoolean(specialCharsSwitch_STATUS, specialCharsSwitch.isChecked());
                editor.apply();
            }
        });

        generatePW_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPassword(passwordLength);
            }
        });

        copyPW_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("Generated Password", password);
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(PasswordGeneratorActivity.this, "Password copied", Toast.LENGTH_SHORT).show();
            }
        });

        savePassword_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("Generated Password", password);
                setResult(RESULT_OK, intent);
                PasswordGeneratorActivity.super.onBackPressed();
            }
        });

    }

    private void setPassword(int passwordLength) {
        PasswordGenerator.clear();

        if(uppercaseSwitch.isChecked()) PasswordGenerator.add(new UpperCaseGenerator());
        if(lowercaseSwitch.isChecked()) PasswordGenerator.add(new LowerCaseGenerator());
        if(numbersSwitch.isChecked()) PasswordGenerator.add(new NumericGenerator());
        if(specialCharsSwitch.isChecked()) PasswordGenerator.add(new SpecialCharGenerator());

        if(PasswordGenerator.isEmpty()) {
            lowercaseSwitch.setChecked(true);
            PasswordGenerator.add(new LowerCaseGenerator());
        }

        password = PasswordGenerator.generatePassword(passwordLength);
        generatedPW_TV.setText(password);
    }


}