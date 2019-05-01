package com.christophermasse.checklist.presentation.feature.task_list;

import android.content.Intent;
import android.os.Bundle;

import com.christophermasse.checklist.R;
import com.christophermasse.checklist.entities.Task;
import com.christophermasse.checklist.presentation.activity.BaseActivity;
import com.christophermasse.checklist.presentation.feature.add_task.AddTaskActivity;
import com.christophermasse.checklist.presentation.feature.edit_task.EditTaskFrag;
import com.christophermasse.checklist.presentation.listener.FragmentOps;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import timber.log.Timber;

public class TaskListActivity extends BaseActivity implements FragmentOps {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_frag);
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        createFrag();
    }

    private void createFrag(){
        FragmentManager fm = getSupportFragmentManager();
        Fragment frag = TaskListFrag.newInstance();
        fm.beginTransaction()
                .replace(R.id.single_frag_container, frag)
                .commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Timber.d(requestCode + "." + resultCode);
    }

    @Override
    public void requestEditTask(@NonNull Task task) {
        FragmentManager fm = getSupportFragmentManager();
        Fragment frag = EditTaskFrag.newInstance(task);
        fm.beginTransaction()
                .replace(R.id.single_frag_container, frag)
                .addToBackStack(null)
                .commit();
    }
}
