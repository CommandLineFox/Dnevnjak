package com.example.dnevnjak.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dnevnjak.R;
import com.example.dnevnjak.model.User;
import com.example.dnevnjak.model.UserJson;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

public class ChangePasswordActivity extends AppCompatActivity {
    private EditText oldPassword;
    private EditText newPassword;
    private Button changePasswordButton;
    private Button cancelButton;
    private final String loggedInUser = "current";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        setupVariables();
        listenerSetup();
    }

    private void setupVariables() {
        oldPassword = findViewById(R.id.old_password);
        newPassword = findViewById(R.id.new_password);
        changePasswordButton = findViewById(R.id.confirm_change_password_button);
        cancelButton = findViewById(R.id.cancel_password_change_button);
    }

    private boolean validateChanges(String oldPw, String newPw) {
        SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        Gson gson = new Gson();
        User user = gson.fromJson(sharedPreferences.getString(loggedInUser, ""), User.class);
        if (!oldPw.equals(user.getPassword())) {
            oldPassword.setError("Pogrešna lozinka");
            return false;
        }
        if (oldPw.equals("")) {
            oldPassword.setError("Morate uneti staru lozinku");
            return false;
        }

        if (newPw.equals("")) {
            newPassword.setError("Morate uneti novu lozinku");
            return false;
        }

        if (oldPw.equals(newPw)) {
            newPassword.setError("Morate uneti lozinku koja je različita od prethodne");
            return false;
        }

        if (newPw.length() < 5) {
            Toast.makeText(this, "Password mora da bude duži od 4", Toast.LENGTH_LONG).show();
            return false;
        } else if (newPw.equals(newPw.toLowerCase())) {
            Toast.makeText(this, "Password mora da ima bar jedno veliko slovo", Toast.LENGTH_LONG).show();
            return false;
        } else if (!newPw.matches(".*\\d+.*")) {
            Toast.makeText(this, "Password mora da ima bar jedan broj u sebi", Toast.LENGTH_LONG).show();
            return false;
        } else if (!newPw.matches(".*[~#^|$%&*!]*.*")) {
            Toast.makeText(this, "Ovi simboli ne smeju da budu u passwordu", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    private void listenerSetup() {
        changePasswordButton.setOnClickListener(v -> {
            String oldPw = oldPassword.getText().toString();
            String newPw = newPassword.getText().toString();

            if (!validateChanges(oldPw, newPw)) {
                return;
            }

            SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
            Gson gson = new Gson();
            User user = gson.fromJson(sharedPreferences.getString(loggedInUser, ""), User.class);

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
            ArrayList<User> users = userJson.getUsersFromJSON(stringBuilder.toString());

            for (User each : users) {
                if (each.equals(user)) {
                    each.setPassword(newPw);
                }
            }
            try {
                reader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Toast.makeText(this, "Uspešno promenjeno", Toast.LENGTH_LONG).show();
        });
        cancelButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
    }
}