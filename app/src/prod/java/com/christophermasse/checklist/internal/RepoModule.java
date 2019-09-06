package com.christophermasse.checklist.internal;

import com.christophermasse.checklist.data.repository.TaskRepo;
import com.christophermasse.checklist.data.repository.TaskRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepoModule {

    @Provides
    @Singleton
    TaskRepo provideTaskRepo(TaskRepository repository){
        return repository;
    }
}
