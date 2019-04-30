package com.christophermasse.checklist.presentation.feature.task_list;

import android.os.Bundle;

import com.christophermasse.checklist.R;
import com.christophermasse.checklist.presentation.activity.BaseActivity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class TaskListActivity extends BaseActivity {

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
}
