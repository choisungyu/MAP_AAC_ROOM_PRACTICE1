package com.csg.map_aac_room_practice;

import android.app.Application;

import com.google.android.libraries.places.api.Places;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize Places.
        Places.initialize(getApplicationContext(), "AIzaSyD148oNEhvyv9dsS9e05gu0a1BXajgDbw4");

// Create a new Places client instance.
    }
}
