package com.christophermasse.checklist.interactor;

import com.christophermasse.checklist.data.repository.TaskRepo;
import com.christophermasse.checklist.entities.Task;
import com.christophermasse.checklist.internal.theading.PostExecutionThread;
import com.christophermasse.checklist.internal.theading.ThreadExecutor;

import org.intellij.lang.annotations.Flow;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Null;
import org.mockito.junit.MockitoJUnitRunner;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.TestScheduler;
import io.reactivex.subscribers.TestSubscriber;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(MockitoJUnitRunner.class)
public class GetAllTasksTest {

    private GetAllTasks mGetAllTasks;

    @Mock private TaskRepo mockTaskRepo;
    @Mock private PostExecutionThread mockPostExecutionThread;
    @Mock private ThreadExecutor mockThreadExecutor;

    @Before
    public void setUp() {
        mGetAllTasks = new GetAllTasks(mockTaskRepo, mockPostExecutionThread, mockThreadExecutor);
        given(mockPostExecutionThread.getScheduler()).willReturn(new TestScheduler());
    }

    @Test
    public void testSuccessfulGetAllTasksUseCase() {
        mGetAllTasks.buildUseCase();
        verify(mockTaskRepo).getAllTasks();
        verifyNoMoreInteractions(mockTaskRepo);
        verifyZeroInteractions(mockThreadExecutor);
        verifyZeroInteractions(mockPostExecutionThread);
    }

    @Test(expected = NullPointerException.class)
    public void testEmptyRepoWillThrowNullPointer(){
        given(mockTaskRepo.getAllTasks()).willThrow(new NullPointerException());
        mGetAllTasks.execute();
//        TestSubscriber<List<Task>> testObserver = mGetAllTasks.execute().test();

    }

    @Test
    public void testNonEmptyRepoReturnsItems(){
        ArrayList<Task> taskList = new ArrayList<>();
        Task one = new Task(1, "Buy groceries", "Buy eggs, milk, bread", false);
        Task two = new Task(2, "Do laundry", "Wash gym clothes", true);
        taskList.add(one);
        taskList.add(two);
        Mockito.when(mockTaskRepo.getAllTasks()).thenReturn(Flowable.just(taskList));
//        given(mockTaskRepo.getAllTasks()).willReturn(Flowable.just(taskList));

        mGetAllTasks.execute().test().assertComplete();
//        TestSubscriber<List<Task>> testObserver = mGetAllTasks.execute().test();
//        testObserver.assertValues(Mockito.anyList());
    }

    @Test
    public void testSubscriber() {
        List<Task> taskList = new ArrayList<>();
        Task one = new Task(1, "Buy groceries", "Buy eggs, milk, bread", false);
        Task two = new Task(2, "Do laundry", "Wash gym clothes", true);
        taskList.add(one);
        taskList.add(two);

        Mockito.when(mockTaskRepo.getAllTasks()).thenReturn(Flowable.just(taskList));

        TestSubscriber<List<Task>> testSubscriber = mGetAllTasks.execute().test();

        testSubscriber.assertValueCount(1)
                .assertNoErrors()
                .assertResult(taskList)
                .assertComplete();


        testSubscriber.dispose();
    }

    @Test
    public void basicTest(){
        Observable.just(1)
                .test()
                .assertSubscribed()
                .assertValues(0)
                .assertComplete()
                .assertNoErrors();
    }


//    @Test
//    public void testThreadSubscriptions(){
//        Assert.assertNotNull(mockPostExecutionThread);
//        Assert.assertNotNull(mockThreadExecutor);
//        mGetAllTasks.executeAlt();
//        verify(mockTaskRepo).getAllTasks();
//        verifyNoMoreInteractions(mockTaskRepo);
//        mGetAllTasks.executeAlt();
//    }

}