package com.upi.passguard;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.upi.passguard.databinding.ActivityVaultViewBinding;

public class VaultView extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityVaultViewBinding binding;
    Button logOutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityVaultViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FloatingActionButton newEntryButton = (FloatingActionButton) findViewById(R.id.newEntryButton);
        logOutButton = findViewById(R.id.logOutButton);

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
                SessionManagement sessionManagement = new SessionManagement(VaultView.this);
                sessionManagement.removeSession();

                Intent intent = new Intent(VaultView.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }
}