package com.christophermasse.checklist.presentation.feature.edit_task;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import com.christophermasse.checklist.R;
import com.christophermasse.checklist.data.repository.TaskRepo;
import com.christophermasse.checklist.entities.Task;
import com.christophermasse.checklist.presentation.fragment.BaseFragment;
import com.google.android.material.textfield.TextInputEditText;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditTaskFrag extends BaseFragment {

    @BindView(R.id.et_title)
    TextInputEditText et_title;

    @BindView(R.id.et_description)
    TextInputEditText et_description;

    @BindView(R.id.checkbox)
    CheckBox cb;

    @BindView(R.id.update)
    Button b_update;

    @Inject
    TaskRepo mTaskRepo;

    private Task mTask = new Task();

    private static final String ARG_TASK_ID = "com.christophermasse.checklist.arg_task_id";

    private static final String ARG_TASK = "com.christophermasse.checklist.arg_task";

    public static EditTaskFrag newInstance(Task task) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_TASK_ID, task);

        EditTaskFrag fragment = new EditTaskFrag();
        fragment.setArguments(args);

        return fragment;
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
        et_title.setText(mTask.getName());
        et_description.setText(mTask.getName());
        cb.setChecked(mTask.isCompleted());

    }
    @OnClick(R.id.update)
    void updateTask(){
        showToastShort("UPDATING");
    }
}
