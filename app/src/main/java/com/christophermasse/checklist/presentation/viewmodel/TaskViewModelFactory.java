package com.christophermasse.checklist.presentation.viewmodel;

import com.christophermasse.checklist.data.repository.TaskRepo;
import com.christophermasse.checklist.interactor.AddTask;
import com.christophermasse.checklist.interactor.GetAllTasks;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class TaskViewModelFactory implements ViewModelProvider.Factory {

    private final TaskRepo taskRepo;

    private final GetAllTasks mGetAllTasks;

    private final AddTask mAddTask;

    @Inject
    public TaskViewModelFactory(@NonNull TaskRepo taskRepo, @NonNull GetAllTasks getAllTasks, @NonNull AddTask addTask) {
        this.taskRepo = taskRepo;
        this.mGetAllTasks = getAllTasks;
        this.mAddTask = addTask;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TaskViewModel.class)) {
            return (T) new TaskViewModel(taskRepo, mGetAllTasks);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
