package com.christophermasse.checklist.interactor;

import com.christophermasse.checklist.data.repository.TaskRepo;
import com.christophermasse.checklist.entities.Task;
import com.christophermasse.checklist.internal.theading.PostExecutionThread;
import com.christophermasse.checklist.internal.theading.ThreadExecutor;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

public class GetAllTasks {

    private final TaskRepo mTaskRepo;

    private final PostExecutionThread mPostExecutionThread;

    private final ThreadExecutor mThreadExecutor;

    @Inject
    public GetAllTasks(@NonNull TaskRepo taskRepo,
                       @NonNull PostExecutionThread postExecutionThread,
                       @NonNull ThreadExecutor threadExecutor) {
        this.mTaskRepo = taskRepo;
        this.mPostExecutionThread = postExecutionThread;
        this.mThreadExecutor = threadExecutor;
    }


    Flowable<List<Task>> buildUseCase(){
        return mTaskRepo.getAllTasks();
    }


    public Flowable<List<Task>> execute(){
        return buildUseCase()
                .subscribeOn(Schedulers.from(mThreadExecutor))
                .observeOn(mPostExecutionThread.getScheduler());
    }

}
