package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDelegate;

public class ListViewAdapter extends BaseAdapter {
    private final Activity context;
    private final String[] titles;
    private final String[] desc;
    private final String[] start_dates;
    private final String[] end_dates;
    private final String current_date;
    private final int image; //drawable here

    public ListViewAdapter(Activity context, String[] titles, String[] desc, int image_drawable,
                           String[] start_dates, String[] end_dates, String current_date)
    {
        super();
        this.context = context;
        this.titles = titles;
        this.desc = desc;
        this.start_dates = start_dates;
        this.end_dates = end_dates;
        this.current_date = current_date;
        image = image_drawable;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder
    {
        TextView textView1;
        TextView textView2;
        ImageView imageView;
        TextView textView3;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        LayoutInflater inflater = context.getLayoutInflater();
        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.list_item, null);
            holder = new Holder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.dayEventImage);
            holder.textView1 = (TextView) convertView.findViewById(R.id.dayEventTitle);
            holder.textView2 = (TextView) convertView.findViewById(R.id.dayEventDescription);
            holder.textView3 = (TextView) convertView.findViewById(R.id.dayEventDates);
            convertView.setTag(holder);
        }
        else
        {
            holder = (Holder) convertView.getTag();
        }
        holder.textView1.setText(titles[position]);
        holder.textView2.setText(desc[position]);
        //if (image != 0)
            //holder.imageView.setImageResource(R.drawable.day_event_logo);
        if (start_dates == null && end_dates == null)
            holder.textView3.setText(current_date);
        else if (end_dates != null){
            String dates = start_dates[position] + " - " + end_dates[position];
            holder.textView3.setText(dates);
        }
        else {
            holder.textView3.setText(start_dates[position]);
        }

        return convertView;
    }
}
