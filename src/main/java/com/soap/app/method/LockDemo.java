package com.soap.app.method;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * lock的使用
 */
public class LockDemo implements Runnable {
    private int counter = 0;
    private final Lock lock = new ReentrantLock();

    @Override
    public void run() {
        int loopTimes =10000;
        while (loopTimes>0){
            try {
                lock.lock();
                counter++;
            }catch (Exception e){

            }finally {
                lock.unlock();
            }
            loopTimes--;
        }
    }

    public static void main(String[] args)throws InterruptedException {
        LockDemo lockDemo = new LockDemo();
        Thread[] threads = new Thread[]{
                new Thread(lockDemo),new Thread(lockDemo),new Thread(lockDemo),new Thread(lockDemo),new Thread(lockDemo)
        };
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread t : threads) {
            t.join();
        }

        System.out.println("demo`s counter is "+lockDemo.counter);
    }
}
