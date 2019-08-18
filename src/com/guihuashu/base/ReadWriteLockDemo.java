package com.guihuashu.base;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @program: java-concurrent-test
 * @description: ReadWriteLock 读写锁
 * @author: Mr.Feng
 * @create: 2019-07-28 00:28
 **/
public class ReadWriteLockDemo {

  //重入锁
  private static Lock lock = new ReentrantLock();
  /**
   * 读-读 不阻塞
   * 读-写 互斥
   * 写-写 互
   */
  private static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

  //读锁
  private static Lock readLock = readWriteLock.readLock();
  //写锁
  private static Lock writeLock = readWriteLock.writeLock();
  private int value;


  public Object handleRead(Lock lock) throws InterruptedException {
    try {
      //模拟读操作，读操作越耗时，读写锁的优势越明显
      lock.lock();
      Thread.sleep(1000);
      return value;
    } finally {
      lock.unlock();
    }
  }


  public void handleWrite(Lock lock, int index) throws InterruptedException {
    try {
      //模拟写操作
      lock.lock();
      Thread.sleep(1000);
      value = index;
    } finally {
      lock.unlock();
    }
  }


  public static void main(String[] args) {
    final ReadWriteLockDemo demo = new ReadWriteLockDemo();
    Runnable readRunnale = new Runnable() {
      @Override
      public void run() {
        try {
          demo.handleRead(readLock);
//                  demo.handleRead(lock);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    };

    Runnable writeRunnale = new Runnable() {
      @Override
      public void run() {
        try {
          demo.handleWrite(writeLock, new Random().nextInt());
//          demo.handleWrite(lock, new Random().nextInt());
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    };

    //开启线程执行读
    for (int i = 0; i < 18; i++) {
      new Thread(readRunnale).start();
    }

    //写
    for (int i = 18; i < 20; i++) {
      new Thread(writeRunnale).start();
    }
  }
}
