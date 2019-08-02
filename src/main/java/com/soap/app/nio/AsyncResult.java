package com.soap.app.nio;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AsyncResult  {
    private Lock lock_= new ReentrantLock();
    public AsyncResult(){
        lock_.newCondition();
    }
}
