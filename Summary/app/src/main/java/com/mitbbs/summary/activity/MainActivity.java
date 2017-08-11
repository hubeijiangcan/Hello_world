package com.mitbbs.summary.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.mitbbs.summary.R;
import com.mitbbs.summary.util.ImageLoader;


public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_PERMISSION_CAMERA = 0x12;
    private ListView listView;
    final String  TAG = "MainActivity";
    String[] datas = {"https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1496365945&di=dc238a8757d20bb4a654758a0f61d8fa&src=http://static.qdqss.cn/2017/0113/1484280657963.jpg",
        "https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1496365945&di=9f73cb1f41f2948405761cfa5058e725&src=http://mvimg2.meitudata.com/57e90e7733ca63586.jpg",
            "https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1496365945&di=92fa7854a82eaf0489ddd30f72d2ddfc&src=http://attach.bbs.miui.com/forum/201608/15/223501e44kkuevtsz7fp30.jpg",
            "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1445698388,2642056218&fm=23&gp=0.jpg",
            "https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1496365945&di=6c2dc227bc639b29753b95c9caf6a6ea&src=http://www.sznews.com/ent/images/attachement/jpg/site3/20160704/IMG78e3b5a05ef341758934370.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1496376102748&di=9b62928ea0a45d2b6785ae9d3f87d9ad&imgtype=0&src=http%3A%2F%2Fimg.ycwb.com%2Fent%2Fattachement%2Fjpg%2Fsite2%2F20130209%2F6cf0490dd6c7127fe6d05f.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1496376102742&di=8db00490df95ccbf047c4a607d4025b5&imgtype=0&src=http%3A%2F%2Fimg3.duitang.com%2Fuploads%2Fitem%2F201604%2F24%2F20160424163240_Rd2WA.jpeg"
    };
    private ImageLoader loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loader = ImageLoader.getInstanse(MainActivity.this);
        listView = (ListView) findViewById(R.id.lv_listView);
        checkPermission();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                startActivity(new Intent(MainActivity.this,HomeActivity.class));
                    //测试自定义view用界面
                startActivity(new Intent(MainActivity.this,TestViewActivity.class));
            }
        });

    }
    private void checkPermission() {
        if (ActivityCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED
                ||ActivityCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION_CAMERA);
        }else {
            listView.setAdapter(new MyAdapter());
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION_CAMERA){
            if (grantResults.length == 2 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED&&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED){
                listView.setAdapter(new MyAdapter());
            }else {
                Toast.makeText(this, "未授权拍照，无法获取相机", Toast.LENGTH_SHORT).show();
                reRequestPermission("是否去应用程序界面授权", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Uri packageURI = Uri.parse("package:" + "com.mitbbs.summary");
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                                , packageURI);
                        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                    }


                });

            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    private void reRequestPermission(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("去授权", okListener)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create()
                .show();
    }

    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return datas.length;
        }

        @Override
        public Object getItem(int position) {
            return datas[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(MainActivity.this,R.layout.item_main,null);
            ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
            loader.loadImage(datas[position],imageView);
            return view;
        }
    }
}
