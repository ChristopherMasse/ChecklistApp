package com.christophermasse.checklist.presentation.feature.add_task;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.christophermasse.checklist.R;
import com.christophermasse.checklist.entities.Task;
import com.christophermasse.checklist.presentation.activity.BaseActivity;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class AddTaskActivity extends BaseActivity implements AddTaskFragment.SubmitTaskListener {

    public static final String EXTRA_TASK_TITLE = "extra_task_title";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_frag);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Add Task");
        createFrag();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    private void createFrag(){
        FragmentManager fm = getSupportFragmentManager();
        Fragment frag = AddTaskFragment.newInstance();
        fm.beginTransaction()
            .replace(R.id.single_frag_container, frag)
            .commit();
    }



    @Override
    public void onTaskSubmitted(Task task) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_TASK_TITLE, task.getName());
        setResult(RESULT_OK, intent);
        finish();
    }
}
