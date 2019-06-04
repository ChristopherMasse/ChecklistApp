package com.christophermasse.checklist.presentation.feature.task_list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.christophermasse.App;
import com.christophermasse.checklist.R;
import com.christophermasse.checklist.data.repository.TaskRepo;
import com.christophermasse.checklist.entities.Task;
import com.christophermasse.checklist.interactor.AddTask;
import com.christophermasse.checklist.internal.component.DaggerTaskComponent;
import com.christophermasse.checklist.presentation.adapter.TaskAdapter;
import com.christophermasse.checklist.presentation.feature.add_task.AddTaskActivity;
import com.christophermasse.checklist.presentation.fragment.BaseFragment;
import com.christophermasse.checklist.presentation.listener.FragmentOps;
import com.christophermasse.checklist.presentation.viewholder.TaskVh;
import com.christophermasse.checklist.presentation.viewmodel.TaskViewModel;
import com.christophermasse.checklist.presentation.viewmodel.TaskViewModelFactory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
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

    @Inject
    TaskRepo mTaskRepo;

    @Inject
    AddTask mAddTask;

    @Inject
    TaskViewModelFactory mFactory;

    private TaskViewModel mTaskViewModel;

    private TaskAdapter mTaskAdapter;

    private FragmentOps mFragmentOps;

    private CompositeDisposable disposables = new CompositeDisposable();



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

        Timber.d("onCreate()");

        setHasOptionsMenu(true);

        mTaskAdapter = new TaskAdapter(this);

        mTaskViewModel =  ViewModelProviders.of(this, mFactory).get(TaskViewModel.class);
        mTaskViewModel.doStuff();
        showToastShort(String.valueOf(mTaskViewModel.randomInt));
        mTaskViewModel.getTasks().observe(this, tasks -> {
            mTaskAdapter.submitList(tasks);
            Timber.d("tasks has changed!");
        });


//        mTaskViewModel.taskList().observe(this, tasks -> mTaskAdapter.submitList(tasks));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.options_task_list, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete_all:
                String message = "Are you sure you want to delete all stored tasks?";
                new AlertDialog.Builder(getContext())
                        .setTitle("Delete All Tasks")
                        .setMessage(message)
                        .setPositiveButton(android.R.string.yes,
                                (dialogInterface, i) -> deleteAllTasks())
                        .setNegativeButton(android.R.string.cancel, null)
                        .show();
                break;

            default:
        }
        return true;
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Timber.d("onCreateView()");

        setToolbarTitle("Tasks");
        showHomeButton(false);

        View root = inflater.inflate(R.layout.frag_task_list, container, false);
        mUnbinder = ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Timber.d("onViewCreated()");
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(mTaskAdapter);
        fab.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), AddTaskActivity.class);
            startActivityForResult(intent, ADD_TASK_REQUEST);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Timber.d("onResume()");

//        subscribeTasks();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposables != null && !disposables.isDisposed()){
            disposables.dispose();
            Timber.d("Disposing observer");
        }
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

            //TODO: usecase
//            Single<Long> single = mTaskRepo.insert(task)
//                    .subscribeOn(Schedulers.from(App.getAppComponent().threadExecutor()))
//                    .observeOn(App.getAppComponent().postExecutionThread().getScheduler());
//            single.subscribe(new DisposableSingleObserver<Long>() {
//                @Override
//                public void onSuccess(Long aLong) {
//                    showToastShort("Added item " + aLong);
//                }
//
//                @Override
//                public void onError(Throwable e) {
//                    Timber.e(e);
//                }
//            });


            mAddTask.execute(task).subscribeWith(new DisposableSingleObserver<Long>() {
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
    private void subscribeTasks() {
        disposables.add(
                mTaskRepo.getAllTasks()
                        .subscribeOn(Schedulers.from(App.getAppComponent().threadExecutor()))
                        .observeOn(App.getAppComponent().postExecutionThread().getScheduler())
                        .subscribeWith(new DisposableSubscriber<List<Task>>() {
            @Override
            public void onNext(List<Task> tasks) {
//                mTaskAdapter.setTaskList(tasks);
                Timber.d("getAllTasks() onNext()");
            }

            @Override
            public void onError(Throwable e) {
                Timber.e(e);
            }

            @Override
            public void onComplete() {
                Timber.d("onComplete()");
            }
        }));
    }

    private void deleteAllTasks(){
        Single<Integer> observable = mTaskRepo.deleteAll()
                .subscribeOn(Schedulers.from(App.getAppComponent().threadExecutor()))
                .observeOn(App.getAppComponent().postExecutionThread().getScheduler());
        disposables.add(observable.subscribeWith(new DisposableSingleObserver<Integer>() {
            @Override
            public void onSuccess(Integer integer) {
                showToastShort("Deleted " + integer + " rows");
            }
            @Override
            public void onError(Throwable e) {
                Timber.e(e);
            }
        }));
    }

    @Override
    public void onItemClick(int pos) {
        Task task = mTaskAdapter.getTaskAtPosition(pos);
        mFragmentOps.requestEditTask(task);
    }

    @Override
    public void onItemCheck(int pos, boolean isChecked) {
        Task task = mTaskAdapter.getTaskAtPosition(pos);
        task.setCompleted(isChecked);
        Single<Integer> single = mTaskRepo.update(task)
                .subscribeOn(Schedulers.from(App.getAppComponent().threadExecutor()))
                .observeOn(App.getAppComponent().postExecutionThread().getScheduler());
        disposables.add(single.subscribeWith(new DisposableSingleObserver<Integer>() {
            @Override
            public void onSuccess(Integer integer) {
//                mTaskAdapter.notifyItemChanged(pos);
                Timber.d("Updated item at pos " + pos);
            }

            @Override
            public void onError(Throwable e) {

            }
        }));

        //TODO: mark completed in repo
    }
}
