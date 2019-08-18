package com.guihuashu.base;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @program: java-concurrent-test
 * @description: CAS 比较交换
 * @author: Mr.Feng
 * @create: 2019-07-28 23:42
 **/
public class AtomicIntegerDemo {


  static AtomicInteger i = new AtomicInteger();

  public static class AddThread implements Runnable {

    @Override
    public void run() {
      for (int j = 0; j < 1000; j++) {
        //CAS策略
        i.incrementAndGet();
      }
    }
  }

  public static void main(String[] args) throws InterruptedException {
    Thread[] tasks = new Thread[10];
    for (int k = 0; k < 10; k++) {
      tasks[k] = new Thread(new AddThread());
    }
    for (int i1 = tasks.length - 1; i1 >= 0; i1--) {
      tasks[i1].start();
    }
    for (int i1 = 0; i1 < tasks.length; i1++) {
      tasks[i1].join();
    }
    System.out.println(i);
  }
}
