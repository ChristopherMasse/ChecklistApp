package com.christophermasse;

import android.app.Application;

import com.christophermasse.checklist.internal.component.AppComponent;
import com.christophermasse.checklist.internal.component.DaggerAppComponent;
import com.christophermasse.checklist.internal.module.AppModule;
import com.christophermasse.checklist.internal.module.RoomModule;

import org.jetbrains.annotations.NotNull;

import timber.log.Timber;

public class App extends Application {

    private static AppComponent sAppComponent;

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

        initializeInjector();
    }


    private void initializeInjector(){
        sAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .roomModule(new RoomModule(this))
                .build();
    }

    public static AppComponent getAppComponent() {
        return sAppComponent;
    }

}
