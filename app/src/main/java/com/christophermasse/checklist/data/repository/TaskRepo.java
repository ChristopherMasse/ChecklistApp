package com.christophermasse.checklist.data.repository;

import com.christophermasse.checklist.entities.Task;

import java.util.List;

import io.reactivex.Observable;

public interface TaskRepo {

    Observable<List<Task>> getAllTasks();
}
