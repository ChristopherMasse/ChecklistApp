package com.christophermasse.checklist.data.repository;

import com.christophermasse.checklist.data.dao.TaskDao;

import javax.inject.Inject;
import javax.inject.Singleton;


/**
 * Wrapper around the Room Dao
 */

@Singleton
public class TaskRepository {

    private TaskDao dao;

    @Inject
    public TaskRepository(TaskDao dao) {
        this.dao = dao;
    }
}
