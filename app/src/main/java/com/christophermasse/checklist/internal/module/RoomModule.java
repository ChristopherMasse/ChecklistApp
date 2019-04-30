package com.christophermasse.checklist.internal.module;

import com.christophermasse.App;
import com.christophermasse.checklist.data.dao.TaskDao;
import com.christophermasse.checklist.data.database.TaskDatabase;

import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;

@Module
public class RoomModule {

    private TaskDatabase taskDatabase;

    public RoomModule(App app) {
        taskDatabase = Room.databaseBuilder(app, TaskDatabase.class, TaskDatabase.DATABASE_NAME)
                .build();
    }

    @Singleton
    @Provides
    TaskDatabase providesTaskDatabase(){
        return this.taskDatabase;
    }


    @Singleton
    @Provides
    TaskDao providesTaskDao(){
        return this.taskDatabase.taskDao();
    }
}
