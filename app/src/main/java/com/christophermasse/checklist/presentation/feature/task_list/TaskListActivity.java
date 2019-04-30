package com.christophermasse.checklist.presentation.feature.task_list;

import android.content.Intent;
import android.os.Bundle;

import com.christophermasse.checklist.R;
import com.christophermasse.checklist.presentation.activity.BaseActivity;
import com.christophermasse.checklist.presentation.feature.add_task.AddTaskActivity;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import timber.log.Timber;

public class TaskListActivity extends BaseActivity implements TaskListFrag.RequestAddTaskListener{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_frag);
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
    public void onRequestAddTask() {
//        Intent intent = new Intent(TaskListActivity.this, AddTaskActivity.class);
//        startActivityForResult(intent, ADD_TASK_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Timber.d(requestCode + "." + resultCode);
    }
}
