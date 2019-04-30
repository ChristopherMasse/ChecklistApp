package com.christophermasse.checklist.data.repository;

import com.christophermasse.checklist.data.dao.TaskDao;
import com.christophermasse.checklist.entities.Task;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;


/**
 * Wrapper around the Room Dao
 */

@Singleton
public class TaskRepository implements TaskRepo{

    private TaskDao dao;

    @Inject
    public TaskRepository(TaskDao dao) {
        this.dao = dao;
    }

    @Override
    public Observable<List<Task>> getAllTasks() {


        return dao.getAllTasks();
    }
}
