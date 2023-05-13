package com.upi.passguard;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.upi.passguard.databinding.ActivityNewAccountBinding;

public class NewAccount extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityNewAccountBinding binding;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNewAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        MaterialButton newaccbtn = (MaterialButton) findViewById(R.id.newaccbtn);

        TextView newUsername =(TextView) findViewById(R.id.newUsername);
        TextView newPassword =(TextView) findViewById(R.id.newPassword);
        TextView newPasswordConfirm =(TextView) findViewById(R.id.newPasswordConfirm);

        newaccbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String username = newUsername.getText().toString();
                String password = newPassword.getText().toString();
                String passwordConfirm = newPasswordConfirm.getText().toString();
                AccountManagement newAccount = new AccountManagement(username, password);
                if(newAccount.createNewAccount(passwordConfirm)){
                    Toast.makeText(NewAccount.this, "Account successfully created", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(NewAccount.this, MainActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(NewAccount.this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}