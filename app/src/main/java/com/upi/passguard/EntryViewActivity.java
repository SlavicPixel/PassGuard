package com.upi.passguard;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.ui.AppBarConfiguration;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.upi.passguard.databinding.ActivityEntryViewBinding;

import java.util.List;
import java.util.Objects;

public class EntryViewActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityEntryViewBinding binding;
    int id;
    String title, username, password, url, notes;
    TextView titleTV, usernameTV, passwordTV, urlTV, notesTV;
    FloatingActionButton editEntryButton;
    ImageView copyUN_IV, copyPW_IV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_view);
        binding = ActivityEntryViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Item information");

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
        editEntryButton = findViewById(R.id.editEntryButton);
        copyUN_IV = findViewById(R.id.usernameCopy);
        copyPW_IV = findViewById(R.id.passwrodCopy);

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

        editEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EntryViewActivity.this, EditEntryActivity.class);

                SessionManagement sessionManagement = new SessionManagement(EntryViewActivity.this);
                DataBaseHelper dataBaseHelper = new DataBaseHelper(EntryViewActivity.this, sessionManagement.getSession(true) + ".db", null, 1, sessionManagement.getSession(false));
                List<VaultModel> entries = dataBaseHelper.getEntries();
                id = getIntent().getIntExtra("ID", 0);

                intent.putExtra("ID", id);
                intent.putExtra("TITLE", title);
                intent.putExtra("USERNAME", username);
                intent.putExtra("PASSWORD", password);
                intent.putExtra("URL", url);
                intent.putExtra("NOTES", notes);

                startActivity(intent);
            }
        });

        copyUN_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("Entry Username", username);
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(EntryViewActivity.this, "Username copied", Toast.LENGTH_SHORT).show();
            }
        });

        copyPW_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("Entry Password", password);
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(EntryViewActivity.this, "Password copied", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.entryviewtb, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        SessionManagement sessionManagement = new SessionManagement(EntryViewActivity.this);
        DataBaseHelper dataBaseHelper = new DataBaseHelper(EntryViewActivity.this, sessionManagement.getSession(true) + ".db", null, 1, sessionManagement.getSession(false));
        int itemId = item.getItemId();

        if (itemId == R.id.deleteEntry){
            id = getIntent().getIntExtra("ID", 0);
            dataBaseHelper.deleteEntry(id);
            Toast.makeText(this, "Item deleted", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(EntryViewActivity.this, VaultViewActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}