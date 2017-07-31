package com.mitbbs.summary.handler;

/**
 * Created by jc on 2017/7/31.
 */
public class Handler {

    private MessageQueue mQueue;


    /**
     * handler的初始化在主线程中
     */
    public Handler(){
        Looper looper = Looper.myLooper();
        this.mQueue = looper.mQueue;
    }

    /**
     * 发送消息 压入队列
     * @param msg
     */
    public void sendMessage(Message msg){
        mQueue.enqueueMessage(msg);
    }
}
