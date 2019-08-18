package com.guihuashu.base;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @program: java-concurrent-test
 * @description: 信号量 限制同时执行的线程数
 * @author: Mr.Feng
 * @create: 2019-07-28 00:06
 **/
public class SemapDemo implements Runnable {
  /**
   * 初始化为5个许可
   */
  final Semaphore semaphore = new Semaphore(5);


  @Override
  public void run() {
    try {
      //获取执行许可
      semaphore.acquire();
      Thread.sleep(2000);
      System.out.println(Thread.currentThread().getId() + " : done!");
      //释放一个许可
      semaphore.release();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    ExecutorService executorService = Executors.newFixedThreadPool(20);
    SemapDemo semapDemo = new SemapDemo();
    for (int i = 0; i < 20; i++) {
      executorService.submit(semapDemo);
    }
  }
}
