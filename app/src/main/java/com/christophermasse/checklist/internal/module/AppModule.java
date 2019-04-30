package com.christophermasse.checklist.internal.module;

import com.christophermasse.App;

import dagger.Module;

@Module
public class AppModule {

    private final App application;

    public AppModule(App application) {
        this.application = application;
    }
}
