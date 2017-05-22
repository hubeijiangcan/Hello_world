package com.mitbbs.summary.util;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.TextView;

import com.mitbbs.summary.R;
import com.mitbbs.summary.widget.TipsToast;

/**
 * Created by jc on 2017/5/8.
 * 定制对话框或一些Toast
 */
public class TipsFactory {
    private volatile static TipsFactory tipsFactory;
    private final String TAG = "TipsFactory";
    private TipsToast tipsToast;
    private Dialog dialog;

    private TipsFactory(){

    }

    /**
     * 单例模式
     * @return
     */
    public static TipsFactory getInstance(){
        TipsFactory instance = null;
        if (tipsFactory == null){
            synchronized (TipsFactory.class){
                if (instance == null){
                    instance = new TipsFactory();
                    tipsFactory = instance;
                }
            }
        }
        return tipsFactory;
    }



    public void showTips(Context context,String msg){
        showTips(context,msg,-1);
    }

    /**
     * 显示提示框
     * @param iconResId  提示图片
     * @param msg	提示文字
     */
    public void showTips(Context context, String msg, int iconResId) {
        tipsToast = new TipsToast(context);

        if (tipsFactory !=null){
            if (Build.VERSION.SDK_INT < 14){
                tipsToast.cancel();
            }
        }else {
            tipsToast = TipsToast.makeText(context, msg, TipsToast.LENGTH_SHORT);
        }
        tipsToast.show();
        tipsToast.setIcon(iconResId);
        tipsToast.setText(msg);
    }


    public void showLoadingDialog(Context context,String content){
        dismissLoadingDialog();
        View view = View.inflate(context, R.layout.loading_dialog,null);
        TextView tv_content;
        tv_content = (TextView) view.findViewById(R.id.tv_content);
        tv_content.setText(content);

        dialog = new Dialog(context,R.style.NOBackGroundDialog);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setContentView(view);
        dialog.show();
    }

    public void dismissLoadingDialog() {
        if (dialog != null && dialog.isShowing()){
            dialog.dismiss();
        }
    }
}
