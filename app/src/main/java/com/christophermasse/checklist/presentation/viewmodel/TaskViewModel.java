package com.christophermasse.checklist.presentation.viewmodel;

import com.christophermasse.checklist.data.repository.TaskRepo;
import com.christophermasse.checklist.entities.Task;
import com.christophermasse.checklist.interactor.GetAllTasks;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subscribers.DisposableSubscriber;
import timber.log.Timber;

public class TaskViewModel extends ViewModel {

    private final TaskRepo mRepo;

    public int randomInt;

    private LiveData<List<Task>> mTaskList;

    private GetAllTasks mGetAllTasks;

    private MutableLiveData<List<Task>> mTasks = new MutableLiveData<>();

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public TaskViewModel(@NonNull TaskRepo repo, @NonNull GetAllTasks getAllTasks) {
        mRepo = repo;
        this.mGetAllTasks = getAllTasks;
        randomInt = (int) (Math.random() * 10000000);
        Timber.d("Created ViewModel with a random int of " + randomInt);
//        mTaskList = LiveDataReactiveStreams.fromPublisher(mRepo.getAllTasks());
    }

    public void doStuff(){
        mCompositeDisposable.add(mGetAllTasks.execute()
                .subscribeWith(new DisposableSubscriber<List<Task>>() {
            @Override
            public void onNext(List<Task> tasks) {
                int count = tasks.size();
                Timber.d("onNext(" + count + ")");
                mTasks.setValue(tasks);
            }

            @Override
            public void onError(Throwable t) {
                Timber.e(t);
            }

            @Override
            public void onComplete() {

            }
        }));
    }

    public MutableLiveData<List<Task>> getTasks() {
        return mTasks;
    }

    public LiveData<List<Task>> taskList() {
        return mTaskList;
    }
}
