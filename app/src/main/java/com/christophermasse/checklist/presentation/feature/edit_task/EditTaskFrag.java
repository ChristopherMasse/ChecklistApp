package com.christophermasse.checklist.presentation.feature.edit_task;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import com.christophermasse.App;
import com.christophermasse.checklist.R;
import com.christophermasse.checklist.data.repository.TaskRepo;
import com.christophermasse.checklist.entities.Task;
import com.christophermasse.checklist.internal.component.DaggerTaskComponent;
import com.christophermasse.checklist.presentation.fragment.BaseFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Single;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class EditTaskFrag extends BaseFragment {

    @BindView(R.id.title)
    TextInputLayout til_title;

    @BindView(R.id.et_title)
    TextInputEditText et_title;


    @BindView(R.id.description_input)
    TextInputLayout til_desc;

    @BindView(R.id.et_description)
    TextInputEditText et_description;

    @BindView(R.id.checkbox)
    CheckBox cb;

    @BindView(R.id.update)
    Button b_update;

    @Inject
    TaskRepo mTaskRepo;

    private Task mTask = new Task();

    private static final String ARG_TASK = "com.christophermasse.checklist.arg_task";

    public static EditTaskFrag newInstance(Task task) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_TASK, task);

        EditTaskFrag fragment = new EditTaskFrag();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showHomeButton(true);
        setToolbarTitle("Edit Task");

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle args = getArguments();
        if (args != null) {
            mTask = (Task) args.getSerializable(ARG_TASK);
        } else {
            throw new NullPointerException("Arguments must be set for this fragment");
        }

        DaggerTaskComponent.builder()
                .appComponent(App.getAppComponent())
                .build()
                .inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.frag_edit_task, container, false);
        mUnbinder = ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String title = mTask.getName();
        String desc = mTask.getDescription();
        boolean isCompleted = mTask.isCompleted();

        til_title.setHintAnimationEnabled(false);
        til_desc.setHintAnimationEnabled(false);

        et_title.setText(title);
        et_description.setText(desc);

        til_title.setHintAnimationEnabled(true);
        til_desc.setHintAnimationEnabled(true);


        cb.setChecked(isCompleted);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @OnClick(R.id.update)
    void updateTask(){

        mTask.setName(et_title.getEditableText().toString().trim());
        mTask.setDescription(et_description.getEditableText().toString().trim());
        mTask.setCompleted(cb.isChecked());

        Single<Integer> single = mTaskRepo.update(mTask)
                .subscribeOn(Schedulers.from(App.getAppComponent().threadExecutor()))
                .observeOn(App.getAppComponent().postExecutionThread().getScheduler());
        single.subscribe(new DisposableSingleObserver<Integer>() {
            @Override
            public void onSuccess(Integer integer) {
                getActivity().getSupportFragmentManager().popBackStack();
            }

            @Override
            public void onError(Throwable e) {
                Timber.e(e);
            }
        });
    }
}
