package com.guihuashu.base;

import java.util.concurrent.*;

/**
 * @program: java-concurrent-test
 * @description: 自定义线程池拒绝策略
 * @author: Mr.Feng
 * @create: 2019-07-28 16:41
 **/
public class RejectThreadPollDemo {


  public static class MyTask implements Runnable {

    @Override
    public void run() {
      System.out.println(System.currentTimeMillis() + " :Thread ID:" + Thread.currentThread().getId());
      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  public static void main(String[] args) throws InterruptedException {
    MyTask task = new MyTask();
    ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(10), Executors.defaultThreadFactory(),
            new RejectedExecutionHandler() {
              @Override
              public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                System.out.println(r.toString() + " :任务丢弃！");
              }
            });
    for (int i = 0; i < 1000000; i++) {
      executor.submit(task);
      Thread.sleep(10);
    }

  }

}
