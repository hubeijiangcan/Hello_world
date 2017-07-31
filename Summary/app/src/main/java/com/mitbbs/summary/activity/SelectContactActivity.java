package com.mitbbs.summary.activity;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.mitbbs.summary.R;
import com.mitbbs.summary.base.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**获取联系人列表
 * Created by jc on 2017/6/2.
 */
public class SelectContactActivity extends BaseActivity{

    private ListView lv_contact;
    private List<Map<String,String>> data = new ArrayList<Map<String,String>>();

    @Override
    protected int attachLayout() {
        return R.layout.activity_select;
    }

    @Override
    protected void bindViews() {

        lv_contact = (ListView) findViewById(R.id.lv_contact);
        data = selectContact();
        Log.e("Tag",data.toString());
        lv_contact.setAdapter(new SimpleAdapter(SelectContactActivity.this,data,R.layout.item_contact,new String[]{"name","number"},new int[]{R.id.tv_name,R.id.tv_num}));
    }

    private List<Map<String,String>> selectContact() {

        ContentResolver resolver = getContentResolver();
        //得到查询raw_contacts表的URI:raw_contacts:存放了contact_id
        Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");

        Cursor cursor = resolver.query(uri, new String[]{"contact_id"}, null, null, null);
        while(cursor.moveToNext()){
            String contact_id = cursor.getString(0);
            //根据contact_id查询data表
            //得到查询data表的URI:data联系人信息的数据字段值
            Uri dataUri = Uri.parse("content://com.android.contacts/data");

            //关联查询data,mimetype表示mimetypes表中的mimetype字段
            Cursor dataCursor = resolver.query(dataUri, new String[]{"mimetype","data1"}, "raw_contact_id=?", new String[]{contact_id}, null);
            Map<String,String> map = new HashMap<String, String>();

            while(dataCursor.moveToNext()){
                String mimetype = dataCursor.getString(0);
                String data1 = dataCursor.getString(1);

                if("vnd.android.cursor.item/phone_v2".equals(mimetype)){
                    //电话号码
                    map.put("number", data1);

                }else if("vnd.android.cursor.item/name".equals(mimetype)){
                    //姓名
                    map.put("name", data1);
                }
            }
            data.add(map);

        }
        return data;
    }
}
