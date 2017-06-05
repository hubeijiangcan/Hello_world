package com.mitbbs.summary.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;


/**
 * 图片的缓存(二级)
 * Created by jc on 2017/6/1.
 */
public class ImageLoader {
    private static Context mContext;
    private static final int MAX_CAPACITY= 20;//设置链表长度
    private static ImageLoader imageLoader = null;
    private static LinkedHashMap<String,SoftReference<Bitmap>> fistCache = new LinkedHashMap<String,SoftReference<Bitmap>>(MAX_CAPACITY){
        @Override
        protected boolean removeEldestEntry(Entry<String, SoftReference<Bitmap>> eldest) {
            if (this.size()>20){
                return true;
            }
            diskCache(eldest.getKey(),eldest.getValue());
            return false;
        }


    };

    private ImageLoader(){
    }

    public static ImageLoader getInstanse(Context context){
        mContext = context;
        if (imageLoader == null){
            imageLoader = new ImageLoader();
        }
        return imageLoader;

    }

    /**
     * 加载图片到对应控件
     * @param key
     * @param view
     */
    public void loadImage(String key, ImageView view){
        synchronized (view){
            Bitmap bitmap = getBitMapFromCache(key);
            if (bitmap != null){
                view.setImageBitmap(bitmap);
            }else {
                view.setBackgroundDrawable(new ColorDrawable(Color.GRAY));
                //异步加载图片
                AsyncImageLoderTask task = new AsyncImageLoderTask(view);
                task.execute(key);

            }
        }
    }

    private Bitmap getBitMapFromCache(String key) {
        if (fistCache.get(key) != null){
            Bitmap bitmap = fistCache.get(key).get();
            if (bitmap != null){
                fistCache.put(key,new SoftReference<Bitmap>(bitmap));
                return bitmap;
            }
        }
        //检查磁盘
        Bitmap bitmap = getBitmapFromLocalKey(key);
        if (bitmap != null){
            fistCache.put(key,new SoftReference<Bitmap>(bitmap));
            return bitmap;
        }
        return null;
    }

    /**
     * 获取磁盘缓存的图片
     * @param key
     * @return
     */
    private Bitmap getBitmapFromLocalKey(String key) {
        if (key != null){
            return null;
        }
        String path = mContext.getCacheDir().getAbsolutePath()+"/"+key;
        InputStream is = null;

        try {
            is = new FileInputStream(new File(path));
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            return bitmap;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            if (is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


    private static void diskCache(String key, SoftReference<Bitmap> value) {
        //消息摘要算法
//        String fileName = MD5Utils.decode(key);
        String path = mContext.getCacheDir().getAbsolutePath()+"/"+key;
        FileOutputStream os = null;

        try {
            os = new FileOutputStream(new File(path));
            if (value.get() != null){
                value.get().compress(Bitmap.CompressFormat.JPEG,100,os);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            if (os != null){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    class AsyncImageLoderTask extends AsyncTask<String,Void,Bitmap>{
        private ImageView imageView;
        private String key;

        public AsyncImageLoderTask(ImageView imageview){
            this.imageView = imageview;
        }
        @Override
        protected Bitmap doInBackground(String... params) {
            this.key = params[0];
            Bitmap bitmap = downLoad(key);
            return bitmap;
        }


        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (bitmap != null){
                addFistCache(key,bitmap);
                imageView.setImageBitmap(bitmap);
            }
        }

        


        private Bitmap downLoad(String key) {
            InputStream is = null;

            try {
                is = HttpUtil.download(key);
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                return bitmap;
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                if (is != null){
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }



    }

    public void addFistCache(String key, Bitmap bitmap) {
        if (bitmap != null){
            synchronized (fistCache){
                fistCache.put(key,new SoftReference<Bitmap>(bitmap));
            }
        }
    }
}
