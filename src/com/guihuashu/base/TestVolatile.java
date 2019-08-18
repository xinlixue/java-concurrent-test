package com.guihuashu.base;

/**
 * @program: java-concurrent-test
 * @description: 线程安全
 * @author: Mr.Feng
 * @create: 2019-07-27 17:15
 **/
public class TestVolatile implements Runnable {

  static TestVolatile instance = new TestVolatile();
  /**
   * volatile 只能确保一个线程修改数据后，其他线程能看见这个改动，当两个线程同时修改数据时，依然会冲突
   */
  static volatile int i = 0;

  public static /*synchronized*/ void instance() {
    i++;
  }

  @Override
  public void run() {
    for (int j = 0; j < 100000; j++) {
      instance();
    }
  }

  public static void main(String[] args) throws InterruptedException {
    Thread t1 = new Thread(instance);
    Thread t2 = new Thread(instance);
    t1.start();
    t2.start();
    t1.join();
    t2.join();
    System.out.println(i);
  }
}
