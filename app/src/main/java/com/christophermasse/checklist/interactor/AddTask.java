package com.christophermasse.checklist.interactor;

import com.christophermasse.App;
import com.christophermasse.checklist.data.repository.TaskRepo;
import com.christophermasse.checklist.entities.Task;
import com.christophermasse.checklist.internal.scope.PerFragment;
import com.christophermasse.checklist.internal.theading.PostExecutionThread;
import com.christophermasse.checklist.internal.theading.ThreadExecutor;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

@PerFragment
public class AddTask {

    private final TaskRepo mTaskRepo;

    private final PostExecutionThread mPostExecutionThread;

    private final ThreadExecutor mThreadExecutor;

    @Inject
    public AddTask(@NonNull TaskRepo taskRepo,
                   @NonNull PostExecutionThread postExecutionThread,
                   @NonNull ThreadExecutor threadExecutor) {
        this.mTaskRepo = taskRepo;
        this.mPostExecutionThread = postExecutionThread;
        this.mThreadExecutor = threadExecutor;
    }


    public Single<Long> execute(Task task){
        return mTaskRepo.insert(task)
                .subscribeOn(Schedulers.from(mThreadExecutor))
                .observeOn(mPostExecutionThread.getScheduler());
    }
}
