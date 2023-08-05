package com.upi.passguard;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

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

        passwordLength_TV.setText(String.format("%d", passwordLength));
        seekBarPWlength.setProgress(passwordLength);

        setPassword(passwordLength);

        seekBarPWlength.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                passwordLength = progress;
                passwordLength_TV.setText(String.format("%d", progress));
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
            }
        });

        lowercaseSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPassword(passwordLength);
            }
        });

        numbersSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPassword(passwordLength);
            }
        });

        specialCharsSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPassword(passwordLength);
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
                ClipData clipData = ClipData.newPlainText("Generated Password", generatedPW_TV.getText());
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