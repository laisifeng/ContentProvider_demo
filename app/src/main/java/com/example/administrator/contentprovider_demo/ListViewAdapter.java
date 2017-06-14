package com.example.administrator.contentprovider_demo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/14 0014.
 */

public class ListViewAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    List<Map<String,Object>> list;

    public ListViewAdapter(Context context, List<Map<String,Object>> list) {
        this.context = context;
        inflater=LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            convertView=inflater.inflate(R.layout.layout_child,null);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.textView.setText(list.get(position).get("name")+"");
        viewHolder.textView2.setText(list.get(position).get("phone")+"");
        viewHolder.textView3.setText(list.get(position).get("email")+"");
        return convertView;

    }
    class ViewHolder{
        TextView textView,textView2,textView3;

        public ViewHolder(View view) {
            textView= (TextView) view.findViewById(R.id.textView);
            textView2= (TextView) view.findViewById(R.id.textView2);
            textView3= (TextView) view.findViewById(R.id.textView3);
        }
    }
}
