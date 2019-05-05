package com.christophermasse.checklist.presentation.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.christophermasse.checklist.R;
import com.christophermasse.checklist.entities.Task;
import com.christophermasse.checklist.presentation.viewholder.TaskVh;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import timber.log.Timber;

public class TaskAdapter extends RecyclerView.Adapter<TaskVh> {

    private List<Task> mTaskList = new ArrayList<>();


    private TaskVh.TaskEventListener mTaskEventListener;

    public TaskAdapter(TaskVh.TaskEventListener listener) {
        this.mTaskEventListener = listener;
    }

    @NonNull
    @Override
    public TaskVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View vh = inflater.inflate(R.layout.vh_task, parent, false);
        return new TaskVh(vh, mTaskEventListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskVh holder, int position) {
        Timber.d("Binding item at position " + position);
        Task task = mTaskList.get(position);
        String text = task.getId() + " " + task.getName();
        holder.setText(text);
        holder.setCompleted(task.isCompleted());
    }

    @Override
    public int getItemCount() {
        return mTaskList.size();
    }


    public void setTaskList(List<Task> taskList) {
        Timber.d("settingTaskList");
        mTaskList = taskList;
        notifyDataSetChanged();
    }

    public Task getTaskAtPosition(int pos) throws IndexOutOfBoundsException{
        return mTaskList.get(pos);
    }
}
