package com.upi.passguard;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.TextView;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.upi.passguard.databinding.ActivityEntryViewBinding;

public class EntryView extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityEntryViewBinding binding;
    String title, username, password, url, notes;
    TextView titleTV, usernameTV, passwordTV, urlTV, notesTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_view);
        binding = ActivityEntryViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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

        titleTV.setText(title);
        usernameTV.setText(username);
        passwordTV.setText(password);
        urlTV.setText(url);
        notesTV.setText(notes);

        setSupportActionBar(binding.toolbar);




    }

}