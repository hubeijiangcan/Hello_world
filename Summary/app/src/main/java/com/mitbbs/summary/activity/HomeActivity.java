package com.mitbbs.summary.activity;

import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mitbbs.summary.R;
import com.mitbbs.summary.base.BaseActivity;
import com.mitbbs.summary.util.TipsFactory;

/**
 * Created by jc on 2017/5/24.
 */
public class HomeActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private HomeAdapter adapter;
    private int[] ids = new int[]{R.drawable.safe,R.drawable.callmsgsafe,R.drawable.app,
    R.drawable.taskmanager,R.drawable.netmanager,R.drawable.trojan,
    R.drawable.sysoptimize,R.drawable.atools,R.drawable.settings};
    private String[] names;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private TipsFactory tipsFactory;

    @Override
    protected int attachLayout() {
        names = getResources().getStringArray(R.array.home);
        sp = getSharedPreferences("config",MODE_PRIVATE);
        editor = sp.edit();

        tipsFactory = TipsFactory.getInstance();
        return R.layout.activity_home;
    }

    @Override
    protected void bindViews() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_home);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context,3);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        if (adapter == null){
            adapter = new HomeAdapter();
        }
        mRecyclerView.setAdapter(adapter);
    }

    public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder>{


        private AlertDialog ad;
        private AlertDialog ad1;

        @Override
        public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_home,parent,false);
            return new HomeViewHolder(view);
        }

        @Override
        public void onBindViewHolder(HomeViewHolder holder, final int position) {
            holder.iv_icon.setImageResource(ids[position]);
            holder.tv_name.setText(names[position]);

            holder.root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    log("click");
                    enterActivity(position);
                }
            });
        }

        private void enterActivity(int position) {
            switch (position){
                case 8:
                    log("posiotion");
                    startActivity(SettingActivity.class);
                    break;
                case 0:
                    String password = sp.getString("password","");
                    if (TextUtils.isEmpty(password)) {
                        showSettingPasswordDialog();
                    } else {
                        showEnterPasswordDialog(password);
                    }
                    break;
            }
        }



        private void showSettingPasswordDialog() {
            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
            ad = null;
            View view = LayoutInflater.from(context).
                    inflate(R.layout.dialog_setting_password,null);
            final EditText et_password = (EditText) view.findViewById(R.id.et_password);
            final EditText et_confim_password = (EditText) view.findViewById(R.id.et_confim_password);
            Button bt_ok = (Button) view.findViewById(R.id.bt_ok);
            Button bt_cancle = (Button) view.findViewById(R.id.bt_cancle);

            bt_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String password = et_password.getText().toString().trim();
                    String confim_password = et_confim_password.getText().toString().trim();
                    if (TextUtils.isEmpty(password)){
                        tipsFactory.showTips(context,"密码不能为空",R.drawable.app);
                        return;
                    }

                    if (TextUtils.isEmpty(confim_password) || !password.equals(confim_password)){
                        tipsFactory.showTips(context,"两次密码输入不一致",R.drawable.app);
                        return;
                    }

                    ad.dismiss();
                    editor.putString("password",password);
                    editor.commit();
                    startActivity(SetUp01Activity.class);
                }
            });

            bt_cancle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ad.isShowing()){
                        ad.dismiss();
                    }
                }
            });
            builder.setView(view);
            ad = builder.create();
            ad.setCancelable(true);
            ad.show();
        }

        private void showEnterPasswordDialog(final String confim) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
            ad1 = null;
            View view = LayoutInflater.from(context).
                    inflate(R.layout.dialog_entry_password,null);
            final EditText et_password = (EditText) view.findViewById(R.id.et_password);
            Button bt_ok = (Button) view.findViewById(R.id.bt_ok);
            Button bt_cancle = (Button) view.findViewById(R.id.bt_cancle);
            bt_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String password = et_password.getText().toString().trim();
                    if (confim.equals(password)){
                        if (sp.getBoolean("isSetup",false)){
                            startActivity(LostFindActivity.class);
                        }else {
                            startActivity(SetUp01Activity.class);
                        }
                    }else {
                        tipsFactory.showTips(context,"密码错误,请重新输入",R.drawable.app);
                        ad1.dismiss();
                        return;
                    }
                    ad1.dismiss();
                }
            });

            bt_cancle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ad1.isShowing()){
                        ad1.dismiss();
                    }
                }
            });

            builder.setView(view);
            ad1 = builder.create();
            ad1.setCancelable(true);
            ad1.show();

        }
        @Override
        public int getItemCount() {
            return ids.length;
        }

        class HomeViewHolder extends RecyclerView.ViewHolder {
            ImageView iv_icon;
            TextView tv_name;
            LinearLayout root;
            public HomeViewHolder(View itemView) {
                super(itemView);
                iv_icon = (ImageView) itemView.findViewById(R.id.iv_icon);
                tv_name = (TextView) itemView.findViewById(R.id.tv_name);
                root = (LinearLayout) itemView.findViewById(R.id.ll_homeRoot);
            }

        }
    }
}



