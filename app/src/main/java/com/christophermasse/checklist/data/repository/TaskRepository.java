package com.christophermasse.checklist.data.repository;

import com.christophermasse.checklist.data.dao.TaskDao;
import com.christophermasse.checklist.entities.Task;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Single;


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
    public Single<Long> insert(Task task) {
        return dao.insert(task);
    }

    @Override
    public Single<Integer> update(Task task) {
        return dao.update(task);
    }

    @Override
    public Single<Integer> delete(Task task) {
        return dao.delete(task);
    }

    @Override
    public Single<Integer> deleteAll() {
        return dao.deleteAll();
    }

    @Override
    public Observable<List<Task>> getAllTasks() {
        return dao.getAllTasks();
    }
}
