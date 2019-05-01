package com.christophermasse.checklist.data.dao;

import com.christophermasse.checklist.entities.Task;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import io.reactivex.Observable;
import io.reactivex.Single;

@Dao
public interface TaskDao {

    @Insert
    Single<Long> insert(Task task);

    @Update
    Single<Integer> update(Task task);

    @Delete
    Single<Integer> delete(Task task);

    @Query("DELETE FROM task")
    Single<Integer> deleteAll();

    @Query("SELECT * FROM task")
    Observable<List<Task>> getAllTasks();
}
