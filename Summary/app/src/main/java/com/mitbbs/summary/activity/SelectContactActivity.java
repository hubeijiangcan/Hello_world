package com.mitbbs.summary.activity;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.mitbbs.summary.R;
import com.mitbbs.summary.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
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
        lv_contact.setAdapter(new SimpleAdapter(SelectContactActivity.this,data,R.layout.item_contact,new String[]{"name","number"},new int[]{R.id.tv_name,R.id.tv_num}));
    }

    private List<Map<String,String>> selectContact() {
        ContentResolver resolver = getContentResolver();
        Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
        Cursor cursor = resolver.query(uri,new String[]{"contact_id"},null,null,null);
        while (cursor.moveToNext()){
            String contact_id = cursor.getString(0);
            
        }
        return null;
    }
}
