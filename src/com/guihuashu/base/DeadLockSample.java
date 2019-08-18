package com.guihuashu.base;

/**
 * @program: java-concurrent-test
 * @description: 死锁排查
 * @author: Mr.Feng
 * @create: 2019-08-11 19:11
 **/
public class DeadLockSample extends Thread {
  private String first;
  private String second;

  public DeadLockSample(String name, String first, String second) {
    super(name);
    this.first = first;
    this.second = second;
  }

  @Override
  public void run() {
    synchronized (first) {
      System.out.println(this.getName() + " obtained: " + first);
      try {
        Thread.sleep(1000L);
        synchronized (second) {
          System.out.println(this.getName() + " obtained: " + second);
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  public static void main(String[] args) throws InterruptedException {
    String lockA = "lockA";
    String lockB = "lockB";
    DeadLockSample t1 = new DeadLockSample("线程1", lockA, lockB);
    DeadLockSample t2 = new DeadLockSample("线程2", lockB, lockA);
    t1.start();
    t2.start();
    t1.join();
    t2.join();

  }
}
