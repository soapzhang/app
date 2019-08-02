package com.soap.app.method;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 学习newCondition()的用法
 */
public class TestLock {
    static class NumberWrapper {
        public int value = 1;
    }

        public static void main(String[] args) {
        //初始化一个可重入锁
        final Lock lock= new ReentrantLock();
        //锁的第一个条件，3
        final Condition threeCondition = lock.newCondition();
        //锁的第二个条件，6
        final Condition sixCondition = lock.newCondition();

        final NumberWrapper num = new NumberWrapper();

        //A线程负责打印1-3，7-9
        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {

                //获取锁
                lock.lock();
                System.out.println("ThreadA获取到锁1");
                try {
                    System.out.println("ThreadA start write.....");
                    while (num.value<=3){
                        System.out.println(num.value);
                        num.value++;
                    }
                    //此时需要通知B线程打印4-6
                    threeCondition.signal();
                }catch (Exception e){

                }finally {
                    lock.unlock();
                    System.out.println("ThreadA释放锁1");
                }
                lock.lock();
                System.out.println("ThreadA获取到锁2");
                try {

                    //等待B线程的通知，输出7-9
                    sixCondition.await();
                    System.out.println("ThreadA continue write.....");
                    while (num.value<=9){
                        System.out.println(num.value);
                        num.value++;
                    }
                }catch (Exception e){

                }finally {
                    lock.unlock();
                    System.out.println("ThreadA释放锁2");
                }
            }
        });
        //B线程负责输出4-6
        Thread threadB = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    lock.lock();
                    System.out.println("ThreadB获取到锁1");
                    while (num.value<=3){
                        threeCondition.await();
                    }
                }catch (InterruptedException e){

                }finally {
                    lock.unlock();
                    System.out.println("ThreadB释放锁1");
                }

                try {
                    lock.lock();
                    System.out.println("ThreadB获取到锁2");
                    System.out.println("ThreadB start write.....");
                    while (num.value<=6){
                        System.out.println(num.value);
                        num.value++;
                    }
                    sixCondition.signal();

                }catch (Exception e){

                }finally {
                    System.out.println("ThreadB释放锁2");
                    lock.unlock();
                }

            }
        });
        threadA.start();
        threadB.start();
    }
    // 可重入锁是排斥的，同一次只有一个线程可以lock（）
//    public static void main(String[] args) {
//            final Lock lock = new ReentrantLock();
//            final NumberWrapper num = new NumberWrapper();
//
//        Thread thread1 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("A 线程开始执行");
//                try {
//                    lock.lock();
//                    while (num.value < 10) {
//                        System.out.println("A out " + num.value);
//                        num.value++;
//                    }
//                } catch (Exception e) {
//
//                } finally {
//                    lock.unlock();
//                }
//
//            }
//        });
//        Thread thread2 = new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//                System.out.println("B 线程开始执行");
//                try {
//                    lock.lock();
//                    while (num.value < 10) {
//                        System.out.println("B out " + num.value);
//                        num.value++;
//                    }
//                } catch (Exception e) {
//
//                } finally {
//                    lock.unlock();
//                }
//
//            }
//        });
//        thread1.start();
//        thread2.start();
//    }
}
