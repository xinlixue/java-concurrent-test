package com.guihuashu.base;

/**
 * @program: java-concurrent-test
 * @description:
 * @author: Mr.Feng
 * @create: 2019-07-27 00:45
 **/
public class TestJoinYield {

  public volatile static int i = 0;


  public static class AddThread extends Thread {
    @Override
    public void run() {
      for (; i < 100000; i++) {

      }
    }
  }

  public static void main(String[] args) throws InterruptedException {
    AddThread addThread = new AddThread();
    addThread.start();
    addThread.join();
    System.out.println(i);
  }


}
