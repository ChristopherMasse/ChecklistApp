package com.christophermasse.checklist.internal;

import com.christophermasse.checklist.data.repository.FakeTaskRepository;
import com.christophermasse.checklist.data.repository.TaskRepo;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepoModule {

    @Provides
    @Singleton
    TaskRepo provideTaskRepo(FakeTaskRepository repository){
        return repository;
    }
}
