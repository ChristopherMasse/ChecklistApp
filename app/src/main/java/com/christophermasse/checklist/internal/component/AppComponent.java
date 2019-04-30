package com.christophermasse.checklist.internal.component;

import com.christophermasse.checklist.data.database.TaskDatabase;
import com.christophermasse.checklist.data.repository.TaskRepo;
import com.christophermasse.checklist.internal.module.AppModule;
import com.christophermasse.checklist.internal.module.RoomModule;
import com.christophermasse.checklist.internal.theading.PostExecutionThread;
import com.christophermasse.checklist.internal.theading.ThreadExecutor;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, RoomModule.class})
public interface AppComponent {

    TaskDatabase taskDatabase();

    ThreadExecutor threadExecutor();

    PostExecutionThread postExecutionThread();

    TaskRepo taskRepo();
}
