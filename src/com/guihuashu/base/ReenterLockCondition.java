package com.guihuashu.base;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @program: java-concurrent-test
 * @description: 重入锁 Condition条件
 * @author: Mr.Feng
 * @create: 2019-07-27 23:45
 **/
public class ReenterLockCondition implements Runnable {
  public static ReentrantLock lock = new ReentrantLock();
  //获取Condition条件对象
  public static Condition condition = lock.newCondition();

  @Override
  public void run() {

    try {
      lock.lock();
      //等待
      condition.await();
      System.out.println(Thread.currentThread().getName() + " go to!");
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      lock.unlock();
    }
  }

  public static void main(String[] args) throws InterruptedException {
    ReenterLockCondition lockCondition = new ReenterLockCondition();
    Thread t1 = new Thread(lockCondition);
    t1.start();
    Thread.sleep(2000);
    /**    通知线程t1继续执行
     *   先获取锁，才能执行signal唤醒等待线程
     */
    lock.lock();
    condition.signal();
    lock.unlock();
  }
}
