package com.upi.passguard;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.button.MaterialButton;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.ui.AppBarConfiguration;

import com.upi.passguard.databinding.ActivityNewAccountBinding;

public class NewAccountActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityNewAccountBinding binding;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNewAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        MaterialButton newaccbtn = findViewById(R.id.newaccbtn);

        TextView newUsername = findViewById(R.id.newUsername);
        TextView newPassword =  findViewById(R.id.newPassword);
        TextView newPasswordConfirm = findViewById(R.id.newPasswordConfirm);

        newaccbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String username = newUsername.getText().toString();
                String password = newPassword.getText().toString();
                String passwordConfirm = newPasswordConfirm.getText().toString();
                DataBaseHelper dataBaseHelper = new DataBaseHelper(NewAccountActivity.this, "passguard.db", null, 1);
                UserModel userModel;

                if(!dataBaseHelper.userExists(username))
                {
                    if(password.equals(passwordConfirm)){
                        userModel = new UserModel(-1, username, password);
                        dataBaseHelper.addUser(userModel);
                        dataBaseHelper.createVault(username);

                        Toast.makeText(NewAccountActivity.this, "Account successfully created", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(NewAccountActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(NewAccountActivity.this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(NewAccountActivity.this, "User with that name already exists. Please choose a different username.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


}