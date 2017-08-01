package com.mitbbs.summary.handler;

/**
 * Created by jc on 2017/7/31.
 */
public final class Looper {

    /**
     * 每个主线程都有一个Looper对象
     * Looper对象保存在Threadlocal中，保证了线程数据的隔离
     */
    private static final ThreadLocal<Looper> mThreadLocal = new ThreadLocal<Looper>();

    /**
     * 一个Looper对象对应一个消息队列
     */
    public MessageQueue mQueue;


    private Looper(){
        mQueue = new MessageQueue();
    }
    /**
     * Looper对象的初始化
     */
    public static void prepare(){
        if (mThreadLocal.get() != null){
            throw new RuntimeException("Only one Looper maybe created per thread");
        }
        mThreadLocal.set(new Looper());
    }

    /**
     * 获取当前线程的Looper对象
     * @return
     */
    public static Looper myLooper(){
        return mThreadLocal.get();
    }

    /**
     *  轮询消息队列
     */
    public static void loop(){
        Looper me = myLooper();
        if (me == null){
            throw new RuntimeException("No Looper；Looper.prepare() wasn't called on this Thread");
        }
        MessageQueue queue = me.mQueue;
        for (;;){
            Message msg = queue.next();
            if (msg == null){
                continue;
            }
            //转发给handler
            msg.target.dispatchMessage(msg);
        }
    }

}
