package com.christophermasse.checklist.presentation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.christophermasse.checklist.R;
import com.christophermasse.checklist.entities.Task;
import com.christophermasse.checklist.internal.scope.PerFragment;
import com.christophermasse.checklist.presentation.viewholder.TaskVh;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

@PerFragment
public class TaskAdapter extends RecyclerView.Adapter<TaskVh> {

    private List<Task> mTaskList = new ArrayList<>();

    @Inject
    public TaskAdapter() {
        mTaskList.add(new Task());
        mTaskList.add(new Task());
        mTaskList.add(new Task());
        mTaskList.add(new Task());
        mTaskList.add(new Task());
    }

    @NonNull
    @Override
    public TaskVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View vh = inflater.inflate(R.layout.vh_task, parent, false);
        return new TaskVh(vh);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskVh holder, int position) {
        holder.setText(String.valueOf(position));
    }

    @Override
    public int getItemCount() {
        return mTaskList.size();
    }
}
