package com.guihuashu.base;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @program: java-concurrent-test
 * @description: 重入锁的锁中断响应
 * @author: Mr.Feng
 * @create: 2019-07-27 23:11
 **/
public class IntLok implements Runnable {

  public static ReentrantLock lock1 = new ReentrantLock();
  public static ReentrantLock lock2 = new ReentrantLock();

  int lock;


  /**
   * 控制加锁顺序，构建死锁
   */

  public IntLok(int lock) {
    this.lock = lock;
  }

  @Override
  public void run() {

    try {
      if (lock == 1) {
        lock1.lockInterruptibly();
        try {
          Thread.sleep(500);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        lock2.lockInterruptibly();
      } else {
        lock2.lockInterruptibly();
        try {
          Thread.sleep(500);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        lock1.lockInterruptibly();

      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (lock1.isHeldByCurrentThread()) {
        lock1.unlock();
      }
      if (lock2.isHeldByCurrentThread()) {
        lock2.unlock();
      }
      System.out.println(Thread.currentThread().getName() + ": 线程退出！");
    }
  }

  public static void main(String[] args) throws InterruptedException {
    IntLok lok1 = new IntLok(1);
    IntLok lok2 = new IntLok(2);
    Thread t1 = new Thread(lok1, "线程1");
    Thread t2 = new Thread(lok2, "线程2");
    t1.start();
    t2.start();
    Thread.sleep(1000);
    //中断一个线程
    t2.interrupt();
  }
}
