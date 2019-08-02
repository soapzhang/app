package com.soap.app.method;

class ThreadYield extends Thread {
    public ThreadYield(String name) {
        super(name);
    }

    @Override
    public void run() {
        for (int i = 1; i <= 50; i++) {
            System.out.println("" + this.getName() + "-----" + i);
            // 当i为30时，该线程就会把CPU时间让掉，让其他或者自己的线程执行（也就是谁先抢到谁执行）
            if (i == 30) {
                this.yield();
            }
        }
        System.out.println(Thread.currentThread().getName()+"结束");

    }

    public static void main(String[] args) {

        ThreadYield t1 = new ThreadYield("张三");
        ThreadYield t2 = new ThreadYield("李四");
        t1.setPriority(Thread.MAX_PRIORITY);
        t2.setPriority(Thread.MIN_PRIORITY);
        t2.start();
        t1.start();
    }

}