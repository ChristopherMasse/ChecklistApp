package com.christophermasse.checklist.presentation.viewmodel;

import com.christophermasse.checklist.data.repository.TaskRepo;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class TaskViewModelFactory implements ViewModelProvider.Factory {

    private final TaskRepo taskRepo;

    @Inject
    public TaskViewModelFactory(@NonNull TaskRepo taskRepo) {
        this.taskRepo = taskRepo;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TaskViewModel.class)) {
            return (T) new TaskViewModel(taskRepo);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
