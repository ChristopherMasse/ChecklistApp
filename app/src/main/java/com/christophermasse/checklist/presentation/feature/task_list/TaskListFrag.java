package com.christophermasse.checklist.presentation.feature.task_list;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.christophermasse.App;
import com.christophermasse.checklist.R;
import com.christophermasse.checklist.data.repository.TaskRepo;
import com.christophermasse.checklist.entities.Task;
import com.christophermasse.checklist.internal.component.DaggerTaskComponent;
import com.christophermasse.checklist.presentation.adapter.TaskAdapter;
import com.christophermasse.checklist.presentation.fragment.BaseFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class TaskListFrag extends BaseFragment {

    @BindView(R.id.recycler_view)
    RecyclerView rv;

    @BindView(R.id.fab_add_task)
    FloatingActionButton fab;

    @Inject
    TaskAdapter mTaskAdapter;

    @Inject
    TaskRepo mTaskRepo;

    public static TaskListFrag newInstance() {
        Bundle args = new Bundle();
        TaskListFrag fragment = new TaskListFrag();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        DaggerTaskComponent.builder()
                .appComponent(App.getAppComponent())
                .build()
                .inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.frag_task_list, container, false);
        mUnbinder = ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(mTaskAdapter);
        fab.setOnClickListener(view1 -> {
            Task task = new Task();
            String name = "Some Random Task";
            task.setName(name);

            Single<Long> single = mTaskRepo.insert(task)
                    .subscribeOn(Schedulers.from(App.getAppComponent().threadExecutor()))
                    .observeOn(App.getAppComponent().postExecutionThread().getScheduler());
            single.subscribe(new DisposableSingleObserver<Long>() {
                @Override
                public void onSuccess(Long aLong) {
                    showToastShort("Added item " + aLong);
                }

                @Override
                public void onError(Throwable e) {
                    Timber.e(e);
                }
            });
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Observable<List<Task>> observable = mTaskRepo.getAllTasks()
                .subscribeOn(Schedulers.from(App.getAppComponent().threadExecutor()))
                .observeOn(App.getAppComponent().postExecutionThread().getScheduler());
        observable.subscribe(new DisposableObserver<List<Task>>() {
            @Override
            public void onNext(List<Task> tasks) {
                mTaskAdapter.setTaskList(tasks);
                Timber.d("onNext()");
            }

            @Override
            public void onError(Throwable e) {
                Timber.e(e);
            }

            @Override
            public void onComplete() {

            }
        });

    }
}
