package com.christophermasse.checklist.data.database;

import com.christophermasse.checklist.data.dao.TaskDao;
import com.christophermasse.checklist.entities.Task;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Task.class}, version = TaskDatabase.VERSION, exportSchema = false)
public abstract class TaskDatabase extends RoomDatabase {

    public abstract TaskDao taskDao();

    public static final int VERSION = 1;

    public static final String DATABASE_NAME = "task_db";

}
