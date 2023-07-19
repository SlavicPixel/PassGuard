package com.upi.passguard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.button.MaterialButton;

import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {
    TextView usernameText;
    TextView passwordText;
    MaterialButton loginbtn;
    MaterialButton newaccbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameText = findViewById(R.id.username);
        passwordText = findViewById(R.id.password);

        loginbtn = findViewById(R.id.loginbtn);
        newaccbtn =  findViewById(R.id.newaccbtn);


        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameText.getText().toString();
                String password = passwordText.getText().toString();

                if(ValidateFields(username, password))
                    try {
                        PBKDF2Helper pbkdf2Helper = new PBKDF2Helper();
                        try {
                            password = pbkdf2Helper.getPBKDF2Hash(password, pbkdf2Helper.getSalt(username));
                        } catch (NoSuchAlgorithmException e) {
                            throw new RuntimeException(e);
                        }

                        System.loadLibrary("sqlcipher");
                        DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this,username + ".db", null, 1, password);
                        dataBaseHelper.getEntries();

                        SessionManagement sessionManagement = new SessionManagement(MainActivity.this);
                        sessionManagement.saveSession(username, password);

                        Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, VaultViewActivity.class);
                        startActivity(intent);
                    } catch (net.sqlcipher.database.SQLiteException e) {
                        passwordText.setError("Incorrect password");
                        Toast.makeText(MainActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                    }
            }
        });

        newaccbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewAccountActivity.class);
                startActivity(intent);
            }
        });

    }

    private boolean ValidateFields(String username, String password) {
        Utils utils = new Utils();

        if(username.length() == 0)
        {
            usernameText.setError("This field is required!");
            Toast.makeText(MainActivity.this, "Username and password fields cannot be empty!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(!utils.dbExist(MainActivity.this, username + ".db"))
        {
            usernameText.setError("Database with that name does not exists");
            Toast.makeText(MainActivity.this, "Database with that name does not exists", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(password.length() == 0) {
            passwordText.setError("This field is required!");
            Toast.makeText(MainActivity.this, "Username and password fields cannot be empty!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}