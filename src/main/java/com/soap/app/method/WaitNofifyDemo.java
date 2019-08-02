package com.soap.app.method;

/**
 * 使用Object的wait,notify,notifyAll做线程调度
 * 理解:线程B先启动。到达wait时等待唤醒，A也启动，打印1，2，3，唤醒wait的线程，b，b完成后唤醒a线程
 */
public class WaitNofifyDemo {

    private volatile int val =1;
    private synchronized void printAndIncrease(){
        System.out.println(Thread.currentThread().getName()+" prints "+val);
        val++;
    }
    public class PrintA implements Runnable{
        @Override
        public void run() {
            while(val<=3){
                printAndIncrease();
            }
            synchronized (WaitNofifyDemo.this){
                System.out.println("PrintA print 1,2,3,notify PrintB");
                WaitNofifyDemo.this.notify();
            }
            try {
                while (val<=6){
                    synchronized (WaitNofifyDemo.this){
                        System.out.println("wait in printA");
                        WaitNofifyDemo.this.wait();
                    }
                    System.out.println("wait end printA");
                }
            }catch (InterruptedException e){

            }
            while(val<=9){
                printAndIncrease();
            }
            System.out.println("printA exits");
        }
    }
    public class PrinterB implements Runnable {

        @Override
        public void run() {
            while (val < 3) {
                synchronized (WaitNofifyDemo.this) {
                    try {
                        System.out
                                .println("printerB wait for printerA printed 1,2,3");
                        WaitNofifyDemo.this.wait();
                        System.out
                                .println("printerB waited for printerA printed 1,2,3");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            while (val <= 6) {
                printAndIncrease();
            }

            System.out.println("notify in printerB");
            synchronized (WaitNofifyDemo.this) {
                WaitNofifyDemo.this.notify();
            }
            System.out.println("notify end printerB");
            System.out.println("PrinterB exits.");
        }
    }
    public static void main(String[] args) {
        WaitNofifyDemo demo = new WaitNofifyDemo();
        demo.doPrint();
    }

    private void doPrint() {
        PrintA pa = new PrintA();
        PrinterB pb = new PrinterB();
        Thread a = new Thread(pa);
        a.setName("printerA");
        Thread b = new Thread(pb);
        b.setName("printerB");
        // 必须让b线程先执行，否则b线程有可能得不到锁，执行不了wait，而a线程一直持有锁，会先notify了
        b.start();
        a.start();
    }
}
