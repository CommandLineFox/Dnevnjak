package com.example.dnevnjak.model;

import com.google.gson.Gson;

import java.util.ArrayList;

public class UserJson {
    private ArrayList<User> users;

    public UserJson() {
        users = new ArrayList<>();
    }

    public ArrayList<User> getUsersFromJSON(String users) {
        Gson gs = new Gson();
        UserJson userJson = gs.fromJson(users, UserJson.class);

        return userJson.users;
    }

    public String getJSONFromUsers(ArrayList<User> users) {
        Gson gs = new Gson();
        UserJson userJson = new UserJson();
        userJson.users = users;

        return gs.toJson(userJson, UserJson.class);
    }
}
