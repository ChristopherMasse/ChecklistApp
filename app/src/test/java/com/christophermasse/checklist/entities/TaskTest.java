package com.christophermasse.checklist.entities;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertFalse;

@RunWith(JUnit4.class)
public class TaskTest {

    @Test
    public void checkConstructor(){
        int id = 123;
        String name = "Buy groceries";
        String desc = "Buy milk, eggs, and bread";
        Task task = new Task(id, name, desc, false);

        Assert.assertEquals(task.getId(), id);
        Assert.assertEquals(task.getName(), name);
        Assert.assertEquals(task.getDescription(), desc);
        assertFalse(task.isCompleted());
    }
}