package com.mitbbs.summary.handler;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by jc on 2017/7/31.
 */
public class MessageQueue {


    Message[] items;

    //入队与出队索引
    int putIndex;
    int takeIndex;

    //计数器
    int count;
    //互斥锁
    Lock lock;
    //条件变量
    private Condition notEmpty;
    private Condition notFull;


    public MessageQueue(){
        this.items = new Message[50];
        this.lock = new ReentrantLock();
        this.notEmpty = lock.newCondition();
        this.notFull = lock.newCondition();
    }

    /**
     * 加入队列
     * @param msg
     */
    public void enqueueMessage(Message msg){
        try {
            lock.lock();
            while (count == items.length) {
                try {
                    notFull.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            items[putIndex] = msg;
            putIndex = (++putIndex == items.length) ? 0 : putIndex;
            count++;

            notEmpty.signalAll();
        }finally {
            lock.unlock();
        }

    }


    /**
     * 出队列
     * @return
     */
    public Message next(){
        Message msg = null;
        try {
            lock.lock();
            while (count == 0){
                try {
                    //阻塞线程
                    notEmpty.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            msg = items[takeIndex];
            items[takeIndex] = null;
            takeIndex = (++takeIndex == items.length)?0:takeIndex;
            count--;
            //使用了一个Message对象，通知子线程可以继续生产了
            notFull.signalAll();
        }finally {
            lock.unlock();
        }
        return msg;
    }
}
