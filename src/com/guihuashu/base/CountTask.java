package com.guihuashu.base;

import sun.rmi.runtime.Log;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * @program: java-concurrent-test
 * @description: ForkJoin 任务拆分，将大的执行任务拆分为多个线程执行
 * @author: Mr.Feng
 * @create: 2019-07-28 19:19
 **/
public class CountTask extends RecursiveTask<Long> {
  /**
   * 计算数列求和
   */
  private static final int THRESHOLD = 1000;
  private long start;
  private long end;

  public CountTask(long start, long end) {
    this.start = start;
    this.end = end;
  }

  @Override
  protected Long compute() {
    long sum = 0;
    boolean canCompute = (end - start) < THRESHOLD;
    if (canCompute) {
      for (long i = start; i < end; i++) {
        sum += i;
      }
    } else {
      //拆分为100个小任务
      long step = (start + end) / 1000;
      ArrayList<CountTask> subTasks = new ArrayList<>();
      long pos = start;
      for (int i = 0; i < 1000; i++) {
        long lastOne = pos + step;
        if (lastOne > end) {
          lastOne = end;
        }
        CountTask subTask = new CountTask(pos, lastOne);
        pos += step + 1;
        subTasks.add(subTask);
        subTask.fork();
      }
      for (CountTask t : subTasks) {
        sum += t.join();
      }
    }
    return sum;
  }

  public static void main(String[] args) {
    long start = System.currentTimeMillis();
    ForkJoinPool forkJoinPool = new ForkJoinPool();
    CountTask task = new CountTask(0, 200000L);
    ForkJoinTask<Long> result = forkJoinPool.submit(task);
    /**
     * 在执行get时，任务没有结束，那么线程会等待
     */
    try {
      Long res = result.get();
      System.out.println("sum= " + res);
      System.out.println("耗时: " + (System.currentTimeMillis() - start) + "毫秒");
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
      e.printStackTrace();
    }
    forkJoinPool.shutdown();
  }
}
