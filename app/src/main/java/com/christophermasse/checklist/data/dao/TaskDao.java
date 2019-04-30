package com.christophermasse.checklist.data.dao;

import com.christophermasse.checklist.entities.Task;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import io.reactivex.Observable;

@Dao
public interface TaskDao {

    @Insert
    void insert(Task task);

    @Update
    void update(Task task);

    @Delete
    void delete(Task task);

    @Query("DELETE FROM task")
    void deleteAll();

    @Query("SELECT * FROM task")
    Observable<List<Task>> getAllTasks();


}
