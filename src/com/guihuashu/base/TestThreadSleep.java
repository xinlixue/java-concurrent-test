package com.guihuashu.base;

/**
 * @program: java-concurrent-test
 * @description:
 * @author: Mr.Feng
 * @create: 2019-07-26 23:51
 **/
public class TestThreadSleep {


  public static void main(String[] args) throws InterruptedException {

    Thread t1 = new Thread() {
      @Override
      public void run() {

        while (true) {
          if (Thread.currentThread().isInterrupted()) {
            System.out.println("线程中断!");
            break;
          }
          try {
            Thread.sleep(2000);
          } catch (InterruptedException e) {
            System.out.println("线程在sleep时被中断，抛出异常");
            //设置中断状态，抛出异常会清除中断状态
            Thread.currentThread().interrupt();
          }
          Thread.yield();
        }
      }
    };
    t1.start();
    Thread.sleep(2000);
    t1.interrupt();
  }
}
