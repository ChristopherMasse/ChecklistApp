package com.christophermasse.checklist.presentation.viewholder;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.christophermasse.checklist.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TaskVh extends RecyclerView.ViewHolder {

    @BindView(R.id.task_text)
    TextView tv_task;

    @BindView(R.id.checkbox)
    CheckBox cb_task;

    private final TaskEventListener mListener;

    public TaskVh(@NonNull View itemView, @NonNull TaskEventListener listener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.mListener = listener;
        itemView.setOnClickListener(view -> mListener.onItemClick(getAdapterPosition()));
        cb_task.setOnCheckedChangeListener((compoundButton, b) -> {
            mListener.onItemCheck(getAdapterPosition(), b);
        });

    }

    public void setText(String taskDetails){
        tv_task.setText(taskDetails);
    }

    public interface TaskEventListener {

        void onItemClick(int pos);

        void onItemCheck(int pos, boolean isChecked);
    }
}
