package com.christophermasse.checklist.presentation.feature.add_task;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.christophermasse.checklist.R;
import com.christophermasse.checklist.entities.Task;
import com.christophermasse.checklist.presentation.fragment.BaseFragment;
import com.google.android.material.textfield.TextInputEditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AddTaskFragment extends BaseFragment {

    @BindView(R.id.et_title)
    TextInputEditText et_title;

    @BindView(R.id.et_description)
    TextInputEditText et_description;

    @BindView(R.id.submit)
    Button b_submit;

    private SubmitTaskListener mSubmitTaskListener;

    public static AddTaskFragment newInstance() {
        
        Bundle args = new Bundle();
        
        AddTaskFragment fragment = new AddTaskFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() instanceof SubmitTaskListener){
            mSubmitTaskListener = (SubmitTaskListener) getActivity();
        } else {
            throw new ClassCastException("Parent activity must implement " + SubmitTaskListener.class.getSimpleName());
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.frag_add_task, container, false);
        mUnbinder = ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        b_submit.setOnClickListener(view1 -> {
            String title = et_title.getEditableText().toString();
            String desc = et_description.getEditableText().toString();

            Task task = new Task();
            task.setName(title);

            mSubmitTaskListener.onTaskSubmitted(task);
        });
    }


    public interface SubmitTaskListener{
        void onTaskSubmitted(Task task);
    }
}
