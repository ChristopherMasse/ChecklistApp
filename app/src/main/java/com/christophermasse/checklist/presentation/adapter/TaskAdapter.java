package com.christophermasse.checklist.presentation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.christophermasse.checklist.R;
import com.christophermasse.checklist.entities.Task;
import com.christophermasse.checklist.presentation.viewholder.TaskVh;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import timber.log.Timber;

public class TaskAdapter extends ListAdapter<Task, TaskVh> {

    private TaskVh.TaskEventListener mTaskEventListener;

    public TaskAdapter(TaskVh.TaskEventListener listener) {
        super(DIFF_CALLBACK);
        this.mTaskEventListener = listener;
    }

    private static final DiffUtil.ItemCallback<Task> DIFF_CALLBACK = new DiffUtil.ItemCallback<Task>() {
        @Override
        public boolean areItemsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
            return oldItem.getId() == newItem.getId() &&
                    oldItem.getName().equals(newItem.getName()) &&
                    oldItem.getDescription().equals(newItem.getDescription()) &&
                    oldItem.isCompleted() == newItem.isCompleted();
        }
    };

    public Task getTaskAtPosition(int pos){
        return getItem(pos);
    }

//    public TaskAdapter(TaskVh.TaskEventListener listener) {
//        this.mTaskEventListener = listener;
//    }

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
        Task task = getTaskAtPosition(position);
        String text = task.getId() + " " + task.getName();
        holder.setText(text);
        holder.setCompleted(task.isCompleted());
    }
}
