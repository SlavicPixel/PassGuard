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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView usernameText = findViewById(R.id.username);
        TextView passwordText = findViewById(R.id.password);

        MaterialButton loginbtn = findViewById(R.id.loginbtn);
        MaterialButton newaccbtn =  findViewById(R.id.newaccbtn);


        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameText.getText().toString();
                String password = passwordText.getText().toString();
                Utils utils = new Utils();

                PBKDF2Helper pbkdf2Helper = new PBKDF2Helper();
                try {
                    password = pbkdf2Helper.getPBKDF2Hash(password, pbkdf2Helper.getSalt(username));
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                }

                if(utils.dbExist(MainActivity.this, username + ".db"))
                    try {
                        System.loadLibrary("sqlcipher");
                        DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this,username + ".db", null, 1, password);
                        dataBaseHelper.getEntries();

                        SessionManagement sessionManagement = new SessionManagement(MainActivity.this);
                        sessionManagement.saveSession(username, password);

                        Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, VaultViewActivity.class);
                        startActivity(intent);
                    } catch (net.sqlcipher.database.SQLiteException e) {
                        Toast.makeText(MainActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                    }
                else {
                    Toast.makeText(MainActivity.this, "Database does not exist", Toast.LENGTH_SHORT).show();
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
}