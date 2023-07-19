package com.upi.passguard;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.button.MaterialButton;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.ui.AppBarConfiguration;

import com.upi.passguard.databinding.ActivityNewAccountBinding;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class NewAccountActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityNewAccountBinding binding;

    MaterialButton newaccbtn;

    TextView newUsername;
    TextView newPassword;
    TextView newPasswordConfirm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNewAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        newaccbtn = findViewById(R.id.newaccbtn);

        newUsername = findViewById(R.id.newUsername);
        newPassword =  findViewById(R.id.newPassword);
        newPasswordConfirm = findViewById(R.id.newPasswordConfirm);

        newaccbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String username = newUsername.getText().toString();
                String password = newPassword.getText().toString();
                String passwordConfirm = newPasswordConfirm.getText().toString();

                if(ValidateFields(username, password, passwordConfirm))
                {
                    PBKDF2Helper pbkdf2Helper = new PBKDF2Helper();
                    try {
                        password = pbkdf2Helper.getPBKDF2Hash(password, pbkdf2Helper.getSalt(username));
                    } catch (NoSuchAlgorithmException e) {
                        throw new RuntimeException(e);
                    }

                    System.loadLibrary("sqlcipher");
                    DataBaseHelper dataBaseHelper = new DataBaseHelper(NewAccountActivity.this, username + ".db", null, 1, password);


                    new MaterialAlertDialogBuilder(NewAccountActivity.this)
                            .setTitle("Warning")
                            .setMessage(R.string.alertMessage)
                            .setNeutralButton("I understand", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(NewAccountActivity.this, "Account successfully created", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(NewAccountActivity.this, MainActivity.class);
                                    startActivity(intent);
                                }
                            })
                            .show();
                    }


            }
        });


    }

    private boolean ValidateFields(String username, String password, String passwordConfirm) {
        Utils utils = new Utils();

        if(utils.dbExist(NewAccountActivity.this, username + ".db"))
        {
            newUsername.setError("Database with that name already exists");
            Toast.makeText(NewAccountActivity.this, "Database with that name already exists", Toast.LENGTH_SHORT).show();
            return false;
        }


        if(!password.equals(passwordConfirm))
        {
            newPassword.setError("Passwords do not match!");
            newPasswordConfirm.setError("Passwords do not match!");
            Toast.makeText(NewAccountActivity.this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(username.length() == 0)
        {
            newUsername.setError("This field is required!");
            Toast.makeText(NewAccountActivity.this, "Username and password fields cannot be empty!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(password.length() == 0) {
            newPassword.setError("This field is required!");
            Toast.makeText(NewAccountActivity.this, "Username and password fields cannot be empty!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}