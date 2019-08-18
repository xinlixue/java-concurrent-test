package com.guihuashu.base;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @program: java-concurrent-test
 * @description: 线程池定时调度
 * @author: Mr.Feng
 * @create: 2019-07-28 13:48
 **/
public class ScheduleExecutorServiceDemo {


  public static void main(String[] args) {
    ScheduledExecutorService executorService =
            Executors.newScheduledThreadPool(10);
    /**
     *     如果前面的任务没有完成，调度任务也不会启动
     *     周期定时调度，如果上次任务没执行完，不会开启下一次调度，而是在任务执行完后立即调用（任务执行时间大于执行间隔时间）
     */
  /*  executorService.scheduleAtFixedRate(new Runnable() {
      @Override
      public void run() {
        try {
          Thread.sleep(5000);
          System.out.println(Thread.currentThread().getId() + ":" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }, 0, 2, TimeUnit.SECONDS);*/
    /**
     * 上次任务结束后间隔多少时间执行
     */
    executorService.scheduleWithFixedDelay(new Runnable() {
      @Override
      public void run() {
        try {
          Thread.sleep(5000);
          System.out.println(Thread.currentThread().getId() + ":" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }, 0, 2, TimeUnit.SECONDS);

  }
}
