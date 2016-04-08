package com.subzero.trafficflow.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;


public class BasicAdapter<T> extends BaseAdapter {

    protected ArrayList<T> list;
    protected Context context;

    public BasicAdapter(Context context, ArrayList<T> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        if (list != null)
            return list.size();
        return 0;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub

        if (position == getCount() - 1 || list == null)
            return null;

        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        return null;
    }

}
