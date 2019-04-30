package com.christophermasse.checklist.internal.module;

import com.christophermasse.App;
import com.christophermasse.checklist.data.repository.TaskRepo;
import com.christophermasse.checklist.data.repository.TaskRepository;
import com.christophermasse.checklist.internal.theading.AndroidUiThread;
import com.christophermasse.checklist.internal.theading.JobExecutor;
import com.christophermasse.checklist.internal.theading.PostExecutionThread;
import com.christophermasse.checklist.internal.theading.ThreadExecutor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private final App application;

    public AppModule(App application) {
        this.application = application;
    }

    @Provides
    @Singleton
    TaskRepo provideTaskRepo(TaskRepository repository){
        return repository;
    }


    @Provides
    @Singleton
    ThreadExecutor provideJobExecutor(JobExecutor jobExecutor){
        return jobExecutor;
    }

    @Provides
    @Singleton
    PostExecutionThread providePostExecutionThread(AndroidUiThread androidUiThread) {
        return androidUiThread;
    }
}
