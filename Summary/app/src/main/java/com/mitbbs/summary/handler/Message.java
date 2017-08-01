package com.mitbbs.summary.handler;

/**
 * Created by jc on 2017/7/31.
 */
public class Message {

    public int what;
    public Object obj;
    public Handler target;


    @Override
    public String toString() {
        return obj.toString();
    }
}
