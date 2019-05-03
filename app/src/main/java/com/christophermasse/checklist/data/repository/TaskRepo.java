package com.christophermasse.checklist.data.repository;

import com.christophermasse.checklist.entities.Task;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

public interface TaskRepo {

    Single<Long> insert(Task task);

    Single<Integer> update(Task task);

    Single<Integer> delete(Task task);

    Single<Integer> deleteAll();

    Flowable<List<Task>> getAllTasks();
}
