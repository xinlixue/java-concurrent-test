package com.guihuashu.base;

import java.util.concurrent.*;

/**
 * @program: java-concurrent-test
 * @description: 自定义线程池创建 ThreadFactory
 * @author: Mr.Feng
 * @create: 2019-07-28 17:00
 **/
public class DiyThreadFactory {


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
    ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(10),
            new ThreadFactory() {
              /**
               *实现创建线程方法
               * @param r
               * @return
               */
              @Override
              public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setDaemon(true);
                System.out.println("create :" + t);
                return t;
              }
            }, new ThreadPoolExecutor.DiscardPolicy()) {
      /**
       * 重写下面3个方法，对线程的调用记录
       * @param t
       * @param r
       */
      @Override
      protected void beforeExecute(Thread t, Runnable r) {
        System.out.println("准备执行任务:" + r);
      }

      @Override
      protected void afterExecute(Runnable r, Throwable t) {
        System.out.println("执行任务完成:" + r);
      }

      @Override
      protected void terminated() {
        System.out.println("线程池退出!");
      }
    };

    for (int i = 0; i < 50; i++) {
      executor.submit(task);
    }
    Thread.sleep(2000);
    executor.shutdown();
  }

}
