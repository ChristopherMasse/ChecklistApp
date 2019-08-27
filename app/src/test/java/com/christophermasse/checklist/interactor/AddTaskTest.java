package com.christophermasse.checklist.interactor;

import com.christophermasse.checklist.data.repository.TaskRepo;
import com.christophermasse.checklist.entities.Task;
import com.christophermasse.checklist.internal.theading.PostExecutionThread;
import com.christophermasse.checklist.internal.theading.ThreadExecutor;
import com.google.gson.Gson;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AddTaskTest {

    @Mock TaskRepo mockTaskRepo;

    @Mock PostExecutionThread mockPostExecutionThread;

    @Mock ThreadExecutor mockThreadExecutor;
    private AddTask mAddTask;

    @Before
    public void setUp() {
        mAddTask = new AddTask(mockTaskRepo, mockPostExecutionThread, mockThreadExecutor);
        Mockito.when(mockPostExecutionThread.getScheduler()).thenReturn(new TestScheduler());
    }

    @Test
    public void testAddTaskHappyCase(){
        Task task = new Task();
        Mockito.when(mockTaskRepo.insert(task)).thenReturn(Single.just(1L));

//        TestObserver<Long> subscriber = mAddTask.execute(task).test();
//        verify(mockTaskRepo).insert(task);

        TestObserver<Long> subscriber = new TestObserver<>();
        mAddTask.execute(task).subscribe(subscriber);


        verify(mockTaskRepo).insert(task);
//        Gson gson = new Gson();
//
//        String str=  gson.toJson(subscriber.getEvents());
//        System.out.print(str);

//        verify(mockTaskRepo).insert(task);
//        subscriber.assertComplete();
//        subscriber.assertNoErrors();
//        subscriber.assertValue(1L);
    }

    @Test(expected = NullPointerException.class)
    public void testErrorUseCase(){
        mAddTask.execute(null);
    }


}