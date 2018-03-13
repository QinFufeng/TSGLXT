package com.example.wind.liberarymanege.httpdb;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wind.liberarymanege.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by wind on 2018/3/13.
 */

public class MyAdapter extends BaseAdapter {
    protected Context context;
    protected LayoutInflater inflater;
    protected int resource;
    protected ArrayList <Map<String,Object>> list;
    public MyAdapter(Context context, int resource, ArrayList <Map<String,Object>> list){
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.resource = resource;
        if(list==null){
            this.list=new ArrayList<>();
        }else{
            this.list = list;
        }
    }
    @Override
    public int getCount() {
        if(list.size()%2>0) {
            return list.size()/2+1;
        } else {
            return list.size()/2;
        }
    }
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null ) {
            convertView = inflater.inflate(resource, null);
            vh = new ViewHolder();
            vh.iv1=(ImageView) convertView.findViewById(R.id.BImg);
            vh.tv11=(TextView)convertView.findViewById(R.id.BName);
            vh.tv12=(TextView)convertView.findViewById(R.id.BAuthor);
            vh.tv13=(TextView)convertView.findViewById(R.id.BPice);

            vh.L2= (LinearLayout) convertView.findViewById(R.id.Llist2);

            vh.iv2=(ImageView) convertView.findViewById(R.id.BImg2);
            vh.tv21=(TextView)convertView.findViewById(R.id.BName2);
            vh.tv22=(TextView)convertView.findViewById(R.id.BAuthor2);
            vh.tv23=(TextView)convertView.findViewById(R.id.BPice2);

            convertView.setTag(vh);
        }else {
            vh = (ViewHolder)convertView.getTag();
        }

        int distance =  list.size() - position*2;
        int cellCount = distance >= 2? 2:distance;
        final List<Map<String, Object>> itemList = list.subList(position*2,position*2+cellCount);
        if (itemList.size() >0) {
            Map<String,Object> map=itemList.get(0);
            vh.iv1.setImageBitmap((Bitmap) map.get("BImg"));
            vh.tv11.setText(map.get("BName").toString());
            vh.tv12.setText(map.get("BAuthor").toString());
            vh.tv13.setText(map.get("BPice").toString());
            vh.tv11.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "p"+position, Toast.LENGTH_SHORT).show();
                }
            });
            if (itemList.size() >1){
                vh.L2.setVisibility(View.VISIBLE);
                Map<String,Object> map2=itemList.get(1);
                vh.iv2.setImageBitmap((Bitmap) map2.get("BImg"));
                vh.tv21.setText(map2.get("BName").toString());
                vh.tv22.setText(map2.get("BAuthor").toString());
                vh.tv23.setText(map2.get("BPice").toString());

                vh.tv21.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "p"+position, Toast.LENGTH_SHORT).show();
                    }
                });
            }else{
                vh.L2.setVisibility(View.INVISIBLE);
            }
        }

        /*Map<String,Object> map=list.get(position);
        vh.iv1.setImageBitmap((Bitmap) map.get("BImg"));
        vh.tv11.setText(map.get("BName").toString());
        vh.tv12.setText(map.get("BAuthor").toString());*/
        return convertView;
    }
    /**
     * 封装ListView中item控件以优化ListView
     * @author tongleer
     *
     */
    public static class ViewHolder{
        ImageView iv1;
        TextView tv11;
        TextView tv12;
        TextView tv13;

        ImageView iv2;
        TextView tv21;
        TextView tv22;
        TextView tv23;
        LinearLayout L2;

    }
}
