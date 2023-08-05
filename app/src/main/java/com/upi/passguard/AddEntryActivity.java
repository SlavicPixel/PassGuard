package com.upi.passguard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.navigation.ui.AppBarConfiguration;

import com.google.android.material.appbar.MaterialToolbar;
import com.upi.passguard.databinding.ActivityAddEntryBinding;

import java.util.Objects;

public class AddEntryActivity extends AppCompatActivity {
    private ActivityAddEntryBinding binding;
    Button addEntryButton;
    EditText titleText, usernameText, passwordText, urlText, notesText;
    MaterialToolbar toolbar;
    ImageView passwordGeneratorIV;
    String title, username, password, url, notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddEntryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("New Item");

        addEntryButton = findViewById(R.id.addEntryButton);
        titleText = findViewById(R.id.titleEntry);
        usernameText = findViewById(R.id.usernameEntry);
        passwordText = findViewById(R.id.passwordEntry);
        urlText = findViewById(R.id.urlEntry);
        notesText = findViewById(R.id.notesEntry);
        toolbar = findViewById(R.id.toolbar);
        passwordGeneratorIV = findViewById(R.id.GeneratePassword);

        addEntryButton.setOnClickListener(v -> {
            title = titleText.getText().toString();
            username = usernameText.getText().toString();
            password = passwordText.getText().toString();
            url = urlText.getText().toString();
            notes = notesText.getText().toString();
            SessionManagement sessionManagement = new SessionManagement(AddEntryActivity.this);

            VaultModel vaultModel = new VaultModel(-1, title, username, password, url, notes);
            DataBaseHelper dataBaseHelper = new DataBaseHelper(AddEntryActivity.this, sessionManagement.getSession(true) + ".db", null, 1, sessionManagement.getSession(false));
            dataBaseHelper.addEntry(vaultModel);
            Toast.makeText(AddEntryActivity.this, "Entry successfully added", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(AddEntryActivity.this, VaultViewActivity.class);
            startActivity(intent);

        });

        ActivityResultLauncher<Intent> PasswordGeneratorActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();

                        if (data != null) {
                            passwordText.setText(data.getStringExtra("Generated Password"));
                            password = data.getStringExtra("Generated Password");
                        }

                    }
                });

        passwordGeneratorIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddEntryActivity.this, PasswordGeneratorActivity.class);
                PasswordGeneratorActivityResultLauncher.launch(intent);
            }
        });

    }


}