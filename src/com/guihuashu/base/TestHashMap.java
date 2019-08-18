package com.guihuashu.base;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: java-concurrent-test
 * @description: HashMap线程不安全
 * @author: Mr.Feng
 * @create: 2019-07-27 18:13
 **/
public class TestHashMap {


  static Map<String, String> map = new HashMap<>();

  public static class AddHash implements Runnable {
    int start = 0;

    public AddHash(int start) {
      this.start = start;
    }

    @Override
    public void run() {
      for (int i = start; i < 10000000; i += 2) {
        map.put(Integer.valueOf(i).toString(), Integer.toBinaryString(i));
      }
    }
  }

  public static void main(String[] args) throws InterruptedException {
    Thread t1 = new Thread(new AddHash(0));
    Thread t2 = new Thread(new AddHash(1));
    t1.start();
    t2.start();
    t1.join();
    t2.join();
    System.out.println(map.size());
  }
}
