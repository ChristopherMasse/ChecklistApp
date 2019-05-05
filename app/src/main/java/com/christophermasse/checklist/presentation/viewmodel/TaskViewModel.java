package com.christophermasse.checklist.presentation.viewmodel;

import com.christophermasse.checklist.data.repository.TaskRepo;
import com.christophermasse.checklist.entities.Task;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.ViewModel;

public class TaskViewModel extends ViewModel {

    private final TaskRepo mRepo;

    private LiveData<List<Task>> mTaskList;

    public TaskViewModel(@NonNull TaskRepo repo) {
        mRepo = repo;
        mTaskList = LiveDataReactiveStreams.fromPublisher(mRepo.getAllTasks());
    }

    public LiveData<List<Task>> getTaskList() {
        return mTaskList;
    }
}
