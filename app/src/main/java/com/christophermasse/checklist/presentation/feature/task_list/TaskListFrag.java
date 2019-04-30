package com.christophermasse.checklist.presentation.feature.task_list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.christophermasse.checklist.R;
import com.christophermasse.checklist.presentation.adapter.TaskAdapter;
import com.christophermasse.checklist.presentation.fragment.BaseFragment;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TaskListFrag extends BaseFragment {

    @BindView(R.id.recycler_view)
    RecyclerView rv;

    @Inject
    TaskAdapter mTaskAdapter;

    public static TaskListFrag newInstance() {
        Bundle args = new Bundle();
        TaskListFrag fragment = new TaskListFrag();
        fragment.setArguments(args);
        return fragment;
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

        rv.setAdapter();
    }
}
