package com.guihuashu.base;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @program: java-concurrent-test
 * @description: 倒计时器 CountDownLatch 多线程控制工具类，
 * @author: Mr.Feng
 * @create: 2019-07-28 13:13
 **/
public class CountDownLatchDemo implements Runnable {

  /**
   * 初始化计数器个数
   */
  static final CountDownLatch end = new CountDownLatch(10);

  @Override
  public void run() {
    try {
      Thread.sleep(new Random().nextInt(10) * 1000);
      System.out.println(Thread.currentThread().getId() + "检查完成");
      //倒计时减1
      end.countDown();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) throws InterruptedException {
    CountDownLatchDemo demo = new CountDownLatchDemo();
    ExecutorService executorService = Executors.newFixedThreadPool(10);
    for (int i = 0; i < 10; i++) {
      executorService.submit(demo);
    }
    //10个任务全部执行完毕主线程才能执行
    end.await();
    //执行后面操作
    System.out.println("deno!");
    executorService.shutdown();
  }
}
