package com.soap.app.method;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * condition的使用
 */
public class AppOffical {
    /**
     * BoundedBuffer是一个定长100的集合
     */
    static class BoundedBuffer{
        final Lock lock = new ReentrantLock();
        final Condition notFull = lock.newCondition();
        final Condition notEmpty = lock.newCondition();
        final Object[] items = new Object[100];
        int putptr,takeptr,count;
        public void put(Object x) {
            System.out.println("put wait lock");
            lock.lock();
            System.out.println("put get lock");
            try {
                while (count ==items.length){
                    System.out.println("Buffer is full,please wait");
                    notFull.await();
                }
                items[putptr]=x;
                if(++putptr==items.length){
                    putptr=0;
                }
                count++;
                notEmpty.signal();
            }catch (Exception e){

            }finally {
                lock.unlock();
            }
        }

        public Object take()throws InterruptedException{
            System.out.println("take wait lock");
            lock.lock();
            System.out.println("take get lock");
            Object x = items[takeptr];
            try {
                while (count == 0) {
                    System.out.println("no element,please wait");
                    notEmpty.await();
                }
                if (++takeptr == items.length) {
                    takeptr = 0;
                }
                --count;
                notFull.signal();
                return x;
            }finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        final BoundedBuffer boundedBuffer = new BoundedBuffer();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("t1 run");
                    for (int i = 0; i < 1000; i++) {
                        boundedBuffer.put(Integer.valueOf(i));

                    }
                } catch (Exception e) {

                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
                  try {
                      Object take = boundedBuffer.take();
                      System.out.println(take);
                  }catch (Exception e){

                  }

                }
            }
        });
        t1.start();
        t2.start();

    }
}
