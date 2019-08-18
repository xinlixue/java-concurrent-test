package com.guihuashu.base;

/**
 * @program: java-concurrent-test
 * @description: Integer加锁测试
 * @author: Mr.Feng
 * @create: 2019-07-27 22:19
 **/
public class TestIntegerSync implements Runnable {

  static Integer i = 0;

  @Override
  public void run() {
    for (int j = 0; j < 10000; j++) {
      /**
       * Integer是不可变对象，对Integer改变其实是新new了一个对象，synchronized方法获取锁时，获取的
       * 对象都不相同，所以不能加锁成功
       */
      synchronized (i) {
        i++;
      }
    }
  }

  public static void main(String[] args) throws InterruptedException {
    Thread t1 = new Thread(new TestIntegerSync());
    Thread t2 = new Thread(new TestIntegerSync());
    t1.start();
    t2.start();
    t1.join();
    t2.join();
    System.out.println(i);

  }
}
