package com.christophermasse.checklist.presentation.viewmodel;

import com.christophermasse.checklist.data.repository.TaskRepo;
import com.christophermasse.checklist.entities.Task;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.ViewModel;

public class TaskListViewModel extends ViewModel {

    private TaskRepo mRepo;

    public TaskListViewModel(TaskRepo repo) {
        mRepo = repo;
    }

//    private LiveData<List<Task>> data = LiveDataReactiveStreams.fromPublisher(mRepo.getAllTasks());
}
