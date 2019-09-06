package com.christophermasse.checklist.data.repository;

import com.christophermasse.checklist.data.dao.TaskDao;
import com.christophermasse.checklist.entities.Task;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;
import io.reactivex.Single;
import timber.log.Timber;


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
        Timber.d("insert()");
        return dao.insert(task);
    }

    @Override
    public Single<Integer> update(Task task) {
        Timber.d("update()");
        return dao.update(task);
    }

    @Override
    public Single<Integer> delete(Task task) {
        Timber.d("delete()");
        return dao.delete(task);
    }

    @Override
    public Single<Integer> deleteAll() {
        Timber.d("deleteAll()");
        return dao.deleteAll();
    }

    @Override
    public Flowable<List<Task>> getAllTasks() {
        Timber.d("getAllTasks()");
        return dao.getAllTasks();
    }

}
