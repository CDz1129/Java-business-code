package com.cdz.javacode.juc;

import java.util.concurrent.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: CDz
 * Create: 2020-11-07 08:05
 *
 * 弹性线程池:
 * 当core线程满了之后，不是进入 queue队列而是创建到max线程数
 * 当max线程数满后，加入队列中。
 **/
public class L02ThreadPool {

    public static void main(String[] args) {
        // extend LinkedBlockingQueue to force offer() to return false conditionally
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>() {
            private static final long serialVersionUID = -6903933921423432194L;
            @Override
            public boolean offer(Runnable e) {
                // Offer it to the queue if there is 0 items already queued, else
                // return false so the TPE will add another thread. If we return false
                // and max threads have been reached then the RejectedExecutionHandler
                // will be called which will do the put into the queue.
                if (size() == 0) {
                    return super.offer(e);
                } else {
                    return false;
                }
            }
        };
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(1 /*core*/, 50 /*max*/,
                60 /*secs*/, TimeUnit.SECONDS, queue);
        threadPool.setRejectedExecutionHandler(new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                try {
                    // This does the actual put into the queue. Once the max threads
                    //  have been reached, the tasks will then queue up.
                    executor.getQueue().put(r);
                    // we do this after the put() to stop race conditions
                    if (executor.isShutdown()) {
                        throw new RejectedExecutionException(
                                "Task " + r + " rejected from " + executor);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        });


    }
}
