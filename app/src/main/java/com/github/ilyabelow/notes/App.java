package com.github.ilyabelow.notes;

import android.app.Application;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        NoteRepository.initialize(this);
    }
}
