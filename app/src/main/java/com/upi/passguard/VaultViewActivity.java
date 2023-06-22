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
import android.widget.Button;

import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.upi.passguard.databinding.ActivityVaultViewBinding;

import java.util.List;
import java.util.Objects;

public class VaultViewActivity extends AppCompatActivity implements Entries_RecylerViewInterface {

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

        SessionManagement sessionManagement = new SessionManagement(VaultViewActivity.this);
        DataBaseHelper dataBaseHelper = new DataBaseHelper(VaultViewActivity.this, sessionManagement.getSession(true) + ".db", null, 1, sessionManagement.getSession(false));
        List<VaultModel> entries = dataBaseHelper.getEntries();

        Entries_RecyclerViewAdapter adapter = new Entries_RecyclerViewAdapter(VaultViewActivity.this, entries, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(VaultViewActivity.this));


        newEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VaultViewActivity.this, AddEntryActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(VaultViewActivity.this, EntryViewActivity.class);

        SessionManagement sessionManagement = new SessionManagement(VaultViewActivity.this);
        System.loadLibrary("sqlcipher");
        DataBaseHelper dataBaseHelper = new DataBaseHelper(VaultViewActivity.this, sessionManagement.getSession(true) + ".db", null, 1, sessionManagement.getSession(false));
        List<VaultModel> entries = dataBaseHelper.getEntries();

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
            SessionManagement sessionManagement = new SessionManagement(VaultViewActivity.this);
            sessionManagement.removeSession();

            Intent intent = new Intent(VaultViewActivity.this, MainActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}