package com.android.commonlib.utils;

import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolManager {

    private final int corePoolSize;
    private final int maximumPoolSize;
    private long keepAliveTime = 1;//存活时间
    private TimeUnit unit = TimeUnit.SECONDS;
    private ThreadPoolExecutor executor;

    private static class LazyHolder {
        public static final ThreadPoolManager INSTANCE = new ThreadPoolManager();

        private LazyHolder() {
        }
    }

    public static ThreadPoolManager getInstance() {
        return LazyHolder.INSTANCE;
    }

    private ThreadPoolManager() {
        /**
         * 给corePoolSize赋值：当前设备可用处理器核心数*2 + 1,能够让cpu的效率得到最大程度执行
         */
        corePoolSize = Runtime.getRuntime().availableProcessors() * 2 + 1;
        maximumPoolSize = Integer.MAX_VALUE;
        executor = new ThreadPoolExecutor(
                corePoolSize, //当某个核心任务执行完毕，会依次从缓冲队列中取出等待任务
                maximumPoolSize, //先corePoolSize,然后new LinkedBlockingQueue<Runnable>(),然后maximumPoolSize,但是它的数量是包含了corePoolSize的
                keepAliveTime, //表示的是maximumPoolSize当中等待任务的存活时间
                unit,
                new SynchronousQueue<Runnable>(), //无缓冲的等待队列
                Executors.defaultThreadFactory(), //创建线程的工厂
                new ThreadPoolExecutor.AbortPolicy() //用来对超出maximumPoolSize的任务的处理策略
        );
    }

    public ThreadPoolExecutor getExecutor() {
        return executor;
    }

    /**
     * 执行任务
     */
    public void execute(Runnable runnable) {
        if (runnable == null) {
            return;
        }
        executor.execute(runnable);
    }

    /**
     * 从线程池中移除任务
     */
    public void remove(Runnable runnable) {
        if (runnable == null) {
            return;
        }
        executor.remove(runnable);
    }

}
