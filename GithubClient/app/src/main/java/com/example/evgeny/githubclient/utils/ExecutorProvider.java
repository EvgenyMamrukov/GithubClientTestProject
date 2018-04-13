package com.example.evgeny.githubclient.utils;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import static java.lang.Thread.MIN_PRIORITY;

/**
 * Created by Evgeny on 10.04.18.
 */

public class ExecutorProvider {
    public static final String IDLE_THREAD_NAME = "RestClient-";
    private static int thhreadIndex = 0;

    private static ThreadFactory customThreadPoolFactory = new ThreadFactory() {
        @Override
        public Thread newThread(final Runnable r) {
            return new Thread(new Runnable() {
                @Override
                public void run() {
                    Thread.currentThread().setPriority(MIN_PRIORITY);
                    r.run();
                }
            }, IDLE_THREAD_NAME + (thhreadIndex++));
        }
    };

    public static Executor defaultHttpExecutor() {
        return Executors.newCachedThreadPool(customThreadPoolFactory);
    }

}
