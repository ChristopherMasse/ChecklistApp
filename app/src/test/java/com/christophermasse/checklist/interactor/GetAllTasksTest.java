package com.christophermasse.checklist.interactor;

import com.christophermasse.checklist.data.repository.TaskRepo;
import com.christophermasse.checklist.entities.Task;
import com.christophermasse.checklist.internal.theading.PostExecutionThread;
import com.christophermasse.checklist.internal.theading.ThreadExecutor;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.schedulers.TestScheduler;
import timber.log.Timber;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.verify;
import static org.mockito.BDDMockito.when;
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