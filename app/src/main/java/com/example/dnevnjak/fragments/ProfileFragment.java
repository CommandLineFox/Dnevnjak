package com.example.dnevnjak.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.dnevnjak.R;
import com.example.dnevnjak.activity.ChangePasswordActivity;
import com.example.dnevnjak.activity.LoginActivity;
import com.example.dnevnjak.model.User;
import com.google.gson.Gson;

import java.util.Map;

public class ProfileFragment extends Fragment {
    private final String loggedInUser = "current";
    private TextView usernameText;
    private TextView emailText;
    private Button changePasswordButton;
    private Button logoutButton;
    private ProfileViewModel mViewModel;

    private View view;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        setupVariables();
        setupText();
        listenerSetup();

        return view;
    }

    private void setupVariables() {
        usernameText = view.findViewById(R.id.profile_username);
        emailText = view.findViewById(R.id.profile_email);
        changePasswordButton = view.findViewById(R.id.change_password_button);
        logoutButton = view.findViewById(R.id.logout_button);
    }

    private void setupText() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);
        Gson gson = new Gson();
        User user = gson.fromJson(sharedPreferences.getString(loggedInUser, ""), User.class);

        usernameText.setText(user.getUsername());
        emailText.setText(user.getEmail());
    }

    private void listenerSetup() {
        changePasswordButton.setOnClickListener(v -> {
            Intent intent = new Intent(this.getContext(), ChangePasswordActivity.class);
            startActivity(intent);
        });
        logoutButton.setOnClickListener(v -> {
            SharedPreferences.Editor sharedPreferences = getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE).edit();
            sharedPreferences.remove(loggedInUser);
            sharedPreferences.apply();

            Intent intent = new Intent(this.getContext(), LoginActivity.class);
            startActivity(intent);
        });
    }
}