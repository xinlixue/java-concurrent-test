package com.guihuashu.base;

import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @program: java-concurrent-test
 * @description: 线程中堆栈信息
 * @author: Mr.Feng
 * @create: 2019-07-28 17:36
 **/
public class DivTask implements Runnable {

  int a, b;

  public DivTask(int a, int b) {
    this.a = a;
    this.b = b;
  }

  @Override
  public void run() {
    double re = a / b;
    System.out.println(re);
  }

  public static void main(String[] args) {
    /**
     * 线程会吃掉异常信息
     */
//    ThreadPoolExecutor executor = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 0L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
    ThreadPoolExecutor executor = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 0L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>()) {


      /**
       * 重写下面方法，保存任务线程的堆栈信息
       */
      @Override
      public void execute(Runnable task) {
        super.execute(wrap(task, clientTrace(), Thread.currentThread().getName()));
      }

      @Override
      public Future<?> submit(Runnable task) {
        return super.submit(wrap(task, clientTrace(), Thread.currentThread().getName()));
      }

      private Exception clientTrace() {
        return new Exception("Client stack trace");
      }

      /**
       * 对传入的Runnable任务进行一次包装，以便能处理异常信息
       * @param task
       * @param clientStack
       * @param clientThreadName
       * @return
       */
      private Runnable wrap(final Runnable task, final Exception clientStack, String clientThreadName) {
        return new Runnable() {
          @Override
          public void run() {
            try {
              task.run();
            } catch (Exception e) {
              clientStack.printStackTrace();
              throw e;
            }
          }
        };
      }
    };


    for (int i = 0; i < 5; i++) {
      executor.submit(new DivTask(100, i));
//      executor.execute(new DivTask(100, i));
    }

  }
}
