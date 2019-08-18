package com.guihuashu.base;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @program: java-concurrent-test
 * @description: 重入锁
 * @author: Mr.Feng
 * @create: 2019-07-27 23:03
 **/
public class ReenterLock implements Runnable {

  public static ReentrantLock lock = new ReentrantLock();
  public static int i = 0;

  @Override
  public void run() {
    /**
     * 重入锁可以在一个线程中获取多次相同的锁，获取多少次锁，相应的也需要释放对应次数的锁
     */
    for (int j = 0; j < 100000; j++) {
      lock.lock();
//      lock.lock();
      i++;
      lock.unlock();
//      lock.unlock();
    }
  }

  public static void main(String[] args) throws InterruptedException {
    ReenterLock reenterLock = new ReenterLock();
    Thread t1 = new Thread(reenterLock);
    Thread t2 = new Thread(reenterLock);
    t1.start();
    t2.start();
    t1.join();
    t2.join();
    System.out.println(i);
  }
}
