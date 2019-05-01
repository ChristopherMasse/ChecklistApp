package com.christophermasse.checklist.presentation.feature.task_list;

import android.content.Context;
import android.content.Intent;
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
import com.christophermasse.checklist.presentation.feature.add_task.AddTaskActivity;
import com.christophermasse.checklist.presentation.fragment.BaseFragment;
import com.christophermasse.checklist.presentation.listener.FragmentOps;
import com.christophermasse.checklist.presentation.viewholder.TaskVh;
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

import static android.app.Activity.RESULT_OK;
import static com.christophermasse.checklist.presentation.feature.add_task.AddTaskActivity.EXTRA_TASK_DESC;
import static com.christophermasse.checklist.presentation.feature.add_task.AddTaskActivity.EXTRA_TASK_TITLE;

public class TaskListFrag extends BaseFragment implements TaskVh.TaskEventListener {

    public static final int ADD_TASK_REQUEST = 101;

    @BindView(R.id.recycler_view)
    RecyclerView rv;

    @BindView(R.id.fab_add_task)
    FloatingActionButton fab;

    private TaskAdapter mTaskAdapter;

    @Inject
    TaskRepo mTaskRepo;

    private FragmentOps mFragmentOps;

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

        if (getActivity() instanceof FragmentOps) {
            mFragmentOps = (FragmentOps) getActivity();
        } else {
            throw new ClassCastException("Parent activity must implement " + FragmentOps.class.getSimpleName());
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setToolbarTitle("Tasks");
        showHomeButton(false);
        View root = inflater.inflate(R.layout.frag_task_list, container, false);
        mUnbinder = ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTaskAdapter = new TaskAdapter(this);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(mTaskAdapter);
        fab.setOnClickListener(view1 -> {

            Intent intent = new Intent(getActivity(), AddTaskActivity.class);
            startActivityForResult(intent, ADD_TASK_REQUEST);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Timber.d(requestCode + "." + resultCode);
        if (requestCode == ADD_TASK_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(EXTRA_TASK_TITLE);
            String desc = data.getStringExtra(EXTRA_TASK_DESC);

            Task task = new Task();

            task.setName(title);
            task.setDescription(desc);

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

        }
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

    @Override
    public void onItemClick(int pos) {
        Task task = mTaskAdapter.getTaskAtPosition(pos);
        mFragmentOps.requestEditTask(task);
    }

    @Override
    public void onItemCheck(int pos, boolean isChecked) {
        Task task = mTaskAdapter.getTaskAtPosition(pos);
        //TODO: mark completed in repo
    }

}
