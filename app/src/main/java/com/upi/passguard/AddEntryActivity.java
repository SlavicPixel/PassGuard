package com.upi.passguard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.navigation.ui.AppBarConfiguration;

import com.google.android.material.appbar.MaterialToolbar;
import com.upi.passguard.databinding.ActivityAddEntryBinding;

import java.util.Objects;

public class AddEntryActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityAddEntryBinding binding;
    Button addEntryButton;
    EditText titleText, usernameText, passwordText, urlText, notesText;
    MaterialToolbar toolbar;
    SharedPreferences sharedPreferences;

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

        addEntryButton.setOnClickListener(v -> {
            String title = titleText.getText().toString();
            String username = usernameText.getText().toString();
            String password = passwordText.getText().toString();
            String url = urlText.getText().toString();
            String notes = notesText.getText().toString();
            SessionManagement sessionManagement = new SessionManagement(AddEntryActivity.this);

            VaultModel vaultModel = new VaultModel(-1, title, username, password, url, notes);
            DataBaseHelper dataBaseHelper = new DataBaseHelper(AddEntryActivity.this, "passguard.db", null, 1);
            dataBaseHelper.addEntry(vaultModel, sessionManagement.getSession());
            Toast.makeText(AddEntryActivity.this, "Entry successfully added", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(AddEntryActivity.this, VaultViewActivity.class);
            startActivity(intent);

        });

    }


}