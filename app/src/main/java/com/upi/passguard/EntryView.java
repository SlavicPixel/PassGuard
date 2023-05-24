package com.upi.passguard;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.ui.AppBarConfiguration;

import com.upi.passguard.databinding.ActivityEntryViewBinding;

public class EntryView extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityEntryViewBinding binding;
    int id;
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

        titleTV.setShowSoftInputOnFocus(false);
        usernameTV.setShowSoftInputOnFocus(false);
        passwordTV.setShowSoftInputOnFocus(false);
        urlTV.setShowSoftInputOnFocus(false);
        notesTV.setShowSoftInputOnFocus(false);

        titleTV.setText(title);
        usernameTV.setText(username);
        passwordTV.setText(password);
        urlTV.setText(url);
        notesTV.setText(notes);

        setSupportActionBar(binding.toolbar);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.entryviewtb, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(EntryView.this, "passguard.db", null, 1);
        SessionManagement sessionManagement = new SessionManagement(EntryView.this);
        int itemId = item.getItemId();

        if (itemId == R.id.deleteEntry){
            id = getIntent().getIntExtra("ID", 0);
            dataBaseHelper.deleteEntry(id, sessionManagement.getSession());
            Toast.makeText(this, "Item deleted", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(EntryView.this, VaultView.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}