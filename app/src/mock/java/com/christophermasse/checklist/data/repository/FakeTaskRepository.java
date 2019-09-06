package com.christophermasse.checklist.data.repository;

import com.christophermasse.checklist.entities.Task;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;
import io.reactivex.Single;
import timber.log.Timber;


@Singleton
public class FakeTaskRepository implements TaskRepo {

    private List<Task> mTaskList = new ArrayList<>();

    private long mLastRowId = 0L;

    @Inject
    public FakeTaskRepository() {
        Task task1 = new Task(1, "Wash dishes", "Wash all of the dishes before dinner", true);
        Task task2 = new Task(2, "Pay rent", "Mail the rent check before the 1st of the month", false);
        mTaskList.add(task1);
        mTaskList.add(task2);
    }

    @Override
    public Single<Long> insert(Task task) {
        mTaskList.add(task);
        mLastRowId++;
        return Single.just(mLastRowId);
    }

    @Override
    public Single<Integer> update(Task task) {
        int index = mTaskList.indexOf(task);
        if (index == -1) return Single.just(0);
        mTaskList.set(index, task);
        return Single.just(1);
    }

    @Override
    public Single<Integer> delete(Task task) {
        Timber.d("delete()");
        if (mTaskList.remove(task)) return Single.just(1);
        return Single.just(0);
    }

    @Override
    public Single<Integer> deleteAll() {
        Timber.d("deleteAll()");
        int size = mTaskList.size();
        mTaskList.clear();
        return Single.just(size);
    }

    @Override
    public Flowable<List<Task>> getAllTasks() {
        Timber.d("getAllTasks()");
        return Flowable.just(mTaskList);
    }
}
