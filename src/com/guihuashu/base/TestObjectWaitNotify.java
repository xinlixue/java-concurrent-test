package com.guihuashu.base;

/**
 * @program: java-concurrent-test
 * @description: 测试对象的waith和notify方法
 * @author: Mr.Feng
 * @create: 2019-07-27 00:19
 **/
public class TestObjectWaitNotify {

  final static Object object = new Object();

  /**
   * wait()会释放锁，sleep不会释放锁
   */

  public static class T1 extends Thread {

    @Override
    public void run() {

      synchronized (object) {
        System.out.println(System.currentTimeMillis() + " T1 start !");
        try {
          System.out.println(System.currentTimeMillis() + " T1 wait for object !");
          //在执行wait前，会先申请锁，在执行wait方法后，会释放持有的锁
          object.wait();
          //在调用notify后，该线程会重新获取锁，如果获取不到处于等待锁状态
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        System.out.println(System.currentTimeMillis() + " T1 end !");
      }
    }
  }

  public static class T2 extends Thread {
    @Override
    public void run() {
      synchronized (object) {
        System.out.println(System.currentTimeMillis() + " T2 start !");
        //执行notify前也会先获取锁
        object.notify();
        System.out.println(System.currentTimeMillis() + " T2 end !");
        try {
          Thread.sleep(2000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }

  public static void main(String[] args) {
    T1 t1 = new T1();
    T2 t2 = new T2();
    t1.start();
    t2.start();
  }

}
