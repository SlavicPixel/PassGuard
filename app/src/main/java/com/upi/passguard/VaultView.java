package com.upi.passguard;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.upi.passguard.databinding.ActivityVaultViewBinding;

import java.util.List;
import java.util.Objects;

public class VaultView extends AppCompatActivity implements Entries_RecylerViewInterface {

    private AppBarConfiguration appBarConfiguration;
    private ActivityVaultViewBinding binding;
    Button logOutButton;
    FloatingActionButton newEntryButton;
    //ListView entriesListView;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityVaultViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("My Vault");

        newEntryButton = findViewById(R.id.newEntryButton);
        //logOutButton = findViewById(R.id.logOutButton);
        recyclerView = findViewById(R.id.entriesRecyclerView);

        DataBaseHelper dataBaseHelper = new DataBaseHelper(VaultView.this, "passguard.db", null, 1);
        SessionManagement sessionManagement = new SessionManagement(VaultView.this);
        List<VaultModel> entries = dataBaseHelper.getEntries(sessionManagement.getSession());

        Entries_RecyclerViewAdapter adapter = new Entries_RecyclerViewAdapter(VaultView.this, entries, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(VaultView.this));


        newEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VaultView.this, AddEntry.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(VaultView.this, EntryView.class);

        DataBaseHelper dataBaseHelper = new DataBaseHelper(VaultView.this, "passguard.db", null, 1);
        SessionManagement sessionManagement = new SessionManagement(VaultView.this);
        List<VaultModel> entries = dataBaseHelper.getEntries(sessionManagement.getSession());

        intent.putExtra("ID", entries.get(position).getId());
        intent.putExtra("TITLE", entries.get(position).getTitle());
        intent.putExtra("USERNAME", entries.get(position).getUsername());
        intent.putExtra("PASSWORD", entries.get(position).getPassword());
        intent.putExtra("URL", entries.get(position).getUrl());
        intent.putExtra("NOTES", entries.get(position).getNotes());

        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.vaultviewtb, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int itemId = item.getItemId();

        if (itemId == R.id.logout){
            SessionManagement sessionManagement = new SessionManagement(VaultView.this);
            sessionManagement.removeSession();

            Intent intent = new Intent(VaultView.this, MainActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}