package com.example.dnevnjak.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dnevnjak.R;
import com.example.dnevnjak.model.User;
import com.example.dnevnjak.model.UserJson;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    private EditText loginEmail;
    private EditText loginUsername;
    private EditText loginPassword;
    private Button loginButton;
    private ArrayList<User> users;
    private final String loggedInUser = "current";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        skipLoginCheck();
        setupVariables();
        setupUserList();
        listenerSetup();
    }

    private void skipLoginCheck() {
        SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        if (sharedPreferences.contains(loggedInUser)) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    private void setupVariables() {
        loginEmail = findViewById(R.id.login_email_field);
        loginUsername = findViewById(R.id.login_username_field);
        loginPassword = findViewById(R.id.login_password_field);
        loginButton = findViewById(R.id.login_button);
    }

    private void setupUserList() {
        Resources resources = getResources();

        @SuppressLint("DiscouragedApi")
        int resourceId = resources.getIdentifier("users", "raw", getPackageName());
        InputStream inputStream = resources.openRawResource(resourceId);

        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            Toast.makeText(this, "Došlo je do unutrašnje greške", Toast.LENGTH_LONG).show();
        }

        UserJson userJson = new UserJson();
        users = userJson.getUsersFromJSON(stringBuilder.toString());
    }

    private boolean inputValidation(String email, String username, String password) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        if (email.equals("")) {
            loginEmail.setError("Morate uneti mejl");
            return false;
        }

        if (username.equals("")) {
            loginUsername.setError("Morate uneti ime");
            return false;
        }

        if (password.equals("")) {
            loginPassword.setError("Morate uneti password");
            return false;
        }

        if (!email.matches(emailRegex)) {
            Toast.makeText(this, "Morate uneti validnu mejl adresu", Toast.LENGTH_SHORT).show();
            return false;
        } else if (password.length() < 5) {
            Toast.makeText(this, "Password mora da bude duži od 4", Toast.LENGTH_LONG).show();
            return false;
        } else if (password.equals(password.toLowerCase())) {
            Toast.makeText(this, "Password mora da ima bar jedno veliko slovo", Toast.LENGTH_LONG).show();
            return false;
        } else if (!password.matches(".*\\d+.*")) {
            Toast.makeText(this, "Password mora da ima bar jedan broj u sebi", Toast.LENGTH_LONG).show();
            return false;
        } else if (!password.matches(".*[~#^|$%&*!]*.*")) {
            Toast.makeText(this, "Ovi simboli ne smeju da budu u passwordu", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    private void listenerSetup() {
        loginButton.setOnClickListener(v -> {
            String email = loginEmail.getText().toString();
            String username = loginUsername.getText().toString();
            String password = loginPassword.getText().toString();

            if (!inputValidation(email, username, password)) {
                return;
            }

            for (User user : users) {
                if (username.equals(user.getUsername()) && email.equals(user.getEmail()) && password.equals(user.getPassword())) {
                    SharedPreferences.Editor sharedPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE).edit();

                    Gson gson = new Gson();
                    sharedPreferences.putString(loggedInUser, gson.toJson(user));
                    sharedPreferences.apply();

                    loginEmail.getText().clear();
                    loginUsername.getText().clear();
                    loginPassword.getText().clear();

                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    return;
                }
            }

            Toast.makeText(this, "Nema takvog korisnika", Toast.LENGTH_LONG).show();
        });
    }
}