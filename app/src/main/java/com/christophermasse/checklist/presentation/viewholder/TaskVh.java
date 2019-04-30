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

    public TaskVh(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setText(String taskDetails){
        tv_task.setText(taskDetails);
    }
}
