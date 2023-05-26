package com.upi.passguard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.upi.passguard.databinding.ActivityEditEntryBinding;
import com.upi.passguard.databinding.ActivityEntryViewBinding;

import java.util.List;
import java.util.Objects;

public class EditEntry extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private @NonNull ActivityEditEntryBinding binding;
    int id;
    String title, username, password, url, notes;
    TextView titleTV, usernameTV, passwordTV, urlTV, notesTV;
    FloatingActionButton updateEntryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_entry);
        binding = ActivityEditEntryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Edit item");

        id = getIntent().getIntExtra("ID", 0);
        title = getIntent().getStringExtra("TITLE");
        username = getIntent().getStringExtra("USERNAME");
        password = getIntent().getStringExtra("PASSWORD");
        url = getIntent().getStringExtra("URL");
        notes = getIntent().getStringExtra("NOTES");

        titleTV = findViewById(R.id.titleTextView);
        usernameTV = findViewById(R.id.usernameTextView);
        passwordTV = findViewById(R.id.passwordTextView);
        urlTV = findViewById(R.id.urlTextView);
        notesTV = findViewById(R.id.notesTextView);
        updateEntryButton = findViewById(R.id.updateEntryButton);

        titleTV.setText(title);
        usernameTV.setText(username);
        passwordTV.setText(password);
        urlTV.setText(url);
        notesTV.setText(notes);

        updateEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = titleTV.getText().toString();
                username = usernameTV.getText().toString();
                password = passwordTV.getText().toString();
                url = urlTV.getText().toString();
                notes = notesTV.getText().toString();

                DataBaseHelper dataBaseHelper = new DataBaseHelper(EditEntry.this, "passguard.db", null, 1);
                SessionManagement sessionManagement = new SessionManagement(EditEntry.this);

                dataBaseHelper.editEntry(id, sessionManagement.getSession(), title, username, password, url, notes);

                Toast.makeText(EditEntry.this, "Item updated", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EditEntry.this, VaultView.class);
                startActivity(intent);
            }
        });

    }
}