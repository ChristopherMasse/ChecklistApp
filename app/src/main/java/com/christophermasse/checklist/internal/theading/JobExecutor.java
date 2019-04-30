package com.christophermasse.checklist.internal.theading;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class JobExecutor implements ThreadExecutor{

    private final ThreadPoolExecutor threadPoolExecutor;

    @Inject
    JobExecutor() {
        this.threadPoolExecutor = new ThreadPoolExecutor(3, 5, 12, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(), new JobThreadFactory());
    }

    @Override public void execute(Runnable runnable) {
        this.threadPoolExecutor.execute(runnable);
    }

    private static class JobThreadFactory implements ThreadFactory {
        private int counter = 0;

        @Override public Thread newThread(Runnable runnable) {
            return new Thread(runnable, "android_thread_" + counter++);
        }
    }
}
