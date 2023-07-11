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
                Utils utils = new Utils();

                if(!utils.dbExist(NewAccountActivity.this, username + ".db"))
                {
                    if(password.equals(passwordConfirm)){
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
                    else {
                        Toast.makeText(NewAccountActivity.this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(NewAccountActivity.this, "Database with that name already exists", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }


}