package com.christophermasse.checklist.presentation.feature.edit_task;

import com.christophermasse.checklist.presentation.feature.task_list.TaskListActivity;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

@RunWith(AndroidJUnit4.class)
public class EditTaskFragTest {

    @Rule
    public ActivityScenarioRule<TaskListActivity> mActivityTestRule = new ActivityScenarioRule<>(TaskListActivity.class);




    @Test
    public void testFrag(){
        Assert.assertEquals(2, 2);

    }

}