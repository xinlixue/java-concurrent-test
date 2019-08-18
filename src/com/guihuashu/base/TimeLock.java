package com.guihuashu.base;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @program: java-concurrent-test
 * @description: 限时等待
 * @author: Mr.Feng
 * @create: 2019-07-27 23:25
 **/
public class TimeLock implements Runnable {

  public static ReentrantLock lock = new ReentrantLock();

  @Override
  public void run() {
    try {
      /**
       * 在等待5秒内不能获取到锁，返回false
       */
      if (lock.tryLock(5, TimeUnit.SECONDS)) {
        Thread.sleep(6000);
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      if (lock.isHeldByCurrentThread()) {
        lock.unlock();
      }
    }
  }

  public static void main(String[] args) throws InterruptedException {
    long startTime = System.currentTimeMillis();
    TimeLock timeLock = new TimeLock();
    Thread t1 = new Thread(timeLock);
    Thread t2 = new Thread(timeLock);
    t1.start();
    t2.start();
    t1.join();
    t2.join();
    long time = (System.currentTimeMillis() - startTime) / 1000;
    System.out.println("耗时: " + time);
  }
}
