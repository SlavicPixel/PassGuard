package com.upi.passguard;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.navigation.ui.AppBarConfiguration;

import com.upi.passguard.databinding.ActivityAddEntryBinding;

public class AddEntry extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityAddEntryBinding binding;
    Button addEntryButton;
    EditText titleText, usernameText, passwordText, urlText, notesText;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddEntryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        addEntryButton = findViewById(R.id.addEntryButton);
        titleText = findViewById(R.id.titleEntry);
        usernameText = findViewById(R.id.usernameEntry);
        passwordText = findViewById(R.id.passwordEntry);
        urlText = findViewById(R.id.urlEntry);
        notesText = findViewById(R.id.notesEntry);

        addEntryButton.setOnClickListener(v -> {
            String title = titleText.getText().toString();
            String username = usernameText.getText().toString();
            String password = passwordText.getText().toString();
            String url = urlText.getText().toString();
            String notes = notesText.getText().toString();

            VaultModel vaultModel = new VaultModel(-1, title, username, password, url, notes);
            DataBaseHelper dataBaseHelper = new DataBaseHelper(AddEntry.this, "passguard.db", null, 1);
            dataBaseHelper.addEntry(vaultModel);
            Toast.makeText(AddEntry.this, "Entry successfully added", Toast.LENGTH_SHORT).show();

        });

    }


}