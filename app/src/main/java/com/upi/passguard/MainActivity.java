package com.upi.passguard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView usernameText =(TextView) findViewById(R.id.username);
        TextView passwordText =(TextView) findViewById(R.id.password);

        MaterialButton loginbtn = (MaterialButton) findViewById(R.id.loginbtn);
        MaterialButton newaccbtn = (MaterialButton) findViewById(R.id.newaccbtn);

        //admin and admin
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameText.getText().toString();
                String password = passwordText.getText().toString();
                AccountManagement existingAccount = new AccountManagement(username, password);
                DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this,"passguard.db", null, 1);

                if(dataBaseHelper.getUser(username, password)){
                    //correct password
                    Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, VaultView.class);
                    startActivity(intent);
                }else {
                    //incorrect
                    Toast.makeText(MainActivity.this, "Incorrect password or user does not exist", Toast.LENGTH_SHORT).show();
                }


            }
        });

        newaccbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewAccount.class);
                startActivity(intent);
            }
        });


    }
}