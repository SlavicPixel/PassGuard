package com.upi.passguard;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.navigation.ui.AppBarConfiguration;

import com.upi.passguard.databinding.ActivityVaultViewBinding;

import java.util.List;

public class VaultView extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityVaultViewBinding binding;
    Button logOutButton;
    FloatingActionButton newEntryButton;
    ListView entriesListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityVaultViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        newEntryButton = (FloatingActionButton) findViewById(R.id.newEntryButton);
        logOutButton = findViewById(R.id.logOutButton);
        entriesListView = findViewById(R.id.entriesView);
        DataBaseHelper dataBaseHelper = new DataBaseHelper(VaultView.this, "passguard.db", null, 1);
        SessionManagement sessionManagement = new SessionManagement(VaultView.this);
        List<VaultModel> entries = dataBaseHelper.getEntries(sessionManagement.getSession());
        ArrayAdapter<VaultModel> entryArrayAdapter = new ArrayAdapter<VaultModel>(VaultView.this, android.R.layout.simple_list_item_1, entries);
        entriesListView.setAdapter(entryArrayAdapter);

        newEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VaultView.this, AddEntry.class);
                startActivity(intent);
            }
        });

        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManagement.removeSession();

                Intent intent = new Intent(VaultView.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }
}