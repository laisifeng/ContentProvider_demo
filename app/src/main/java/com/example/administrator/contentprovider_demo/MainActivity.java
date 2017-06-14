package com.example.administrator.contentprovider_demo;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    ListView listView;

    ContentResolver resolver;
    ListViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView= (ListView) findViewById(R.id.listView);
        resolver=getContentResolver();

    }
    public void click(View view){
        List<Map<String,Object>> list = findPhoneNumber(resolver);
        adapter=new ListViewAdapter(this,list);
        listView.setAdapter(adapter);
        }
    public List<Map<String,Object>> findPhoneNumber(ContentResolver resolver){
        List<Map<String,Object>> list = new ArrayList<>();
        //获得raw_contacts表的名字
        String raw_contacts = "content://com.android.contacts/raw_contacts";
        //获得电话号的URI
        String raw_contacts_phones = "content://com.android.contacts/data/phones";
        //获得email的URI
        String raw_contacts_emails = "content://com.android.contacts/data/emails";

        Cursor cursor = resolver.query(Uri.parse(raw_contacts), new String[]{"_id", "display_name"}, null, null, null);

        //从raw_contacts表中获取联系人ID和姓名
        //肯定要通过循环数据查找
        while (cursor.moveToNext()) {
            int contacts_id = cursor.getInt(cursor.getColumnIndex("_id"));
            String contacts_name = cursor.getString(cursor.getColumnIndex("display_name"));

            Map<String, Object> map = new HashMap<>();
            map.put("id", contacts_id);
            map.put("name", contacts_name);

            //根据你找到的这个ID去相应的表中去找号码
            Cursor cursor1 = resolver.query(Uri.parse(raw_contacts_phones),
                    new String[]{"raw_contact_id", "data1"}, "raw_contact_id=?",
                    new String[]{contacts_id + ""}, null);
            //就可以通过这个游标去循环找你的电话号码
            StringBuilder sb = new StringBuilder();
            while (cursor1.moveToNext()){
                sb.append(cursor1.getString(1));
                //sb.append(" | ");

                //将我们找到的电话号加入到map集合当中去
                map.put("phone",sb.toString());


            }

            Cursor cursor2 = resolver.query(Uri.parse(raw_contacts_emails),
                    new String[]{"raw_contact_id", "data1"}, "raw_contact_id=?",
                    new String[]{contacts_id + ""}, null);

            while (cursor2.moveToNext()){
                String email = cursor2.getString(1);
                map.put("email",email);
            }

            list.add(map);
        }
        return list;
    }

}
