package com.christophermasse;

import android.app.Application;

import org.jetbrains.annotations.NotNull;

import timber.log.Timber;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree(){
            @Override
            protected String createStackElementTag(@NotNull StackTraceElement element) {
                // Add line numbers
                return super.createStackElementTag(element) + ":" + element.getLineNumber();
            }
        });
    }
}
