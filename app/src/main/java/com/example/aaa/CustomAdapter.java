package com.example.aaa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    ArrayList<ThoiTiet> arrayList;
     Context context;


    public CustomAdapter(ArrayList<ThoiTiet> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;

    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
//        convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_main, parent, false);

        ThoiTiet thoiTiet = arrayList.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.listview_item_thoitiet, null);
        TextView txtDay = (TextView) convertView.findViewById(R.id.tvngay);
        TextView txtStatus = (TextView)convertView.findViewById(R.id.tvtrangthai);
        TextView txtMaxTemp = (TextView)convertView.findViewById(R.id.tvMaxTemp);
        TextView txtMinTemp = (TextView)convertView.findViewById(R.id.tvMinTemp);
        ImageView imgStatus = (ImageView)convertView.findViewById(R.id.imgtrangthai);


        txtDay.setText(thoiTiet.Day);
        txtStatus.setText(thoiTiet.Status);
        txtMaxTemp.setText(thoiTiet.MaxTemp+"oC");
        txtMinTemp.setText(thoiTiet.MinTemp+"+oC");

        Picasso.with(context).load("http://openweathermap.org/img/wn/"+thoiTiet.Image+".png").resize(50,50).into(imgStatus);
        return convertView;
    }
}
