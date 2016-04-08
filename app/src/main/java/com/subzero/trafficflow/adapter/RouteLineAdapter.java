package com.subzero.trafficflow.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.subzero.trafficflow.R;
import com.subzero.trafficflow.bean.Day;
import com.subzero.trafficflow.bean.MinutesCheck;

import java.util.ArrayList;

/**
 * Created by hui on 2016/3/15.
 */
public class RouteLineAdapter extends BasicAdapter<Day> {


    public RouteLineAdapter(Context context, ArrayList<Day> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.xlistitem_item_routeline, null);
        }


        return convertView;
    }

    static class ViewHolder {

        public TextView id_tv_name;
        public TextView id_tv_number;
        public TextView id_tv_belong;

        public ViewHolder(View convertView) {
            id_tv_name = (TextView) convertView.findViewById(R.id.id_tv_name);
            id_tv_number = (TextView) convertView.findViewById(R.id.id_tv_num);
            id_tv_belong = (TextView) convertView.findViewById(R.id.id_tv_belong);
        }

        public static ViewHolder getHolder(View convertView) {

            ViewHolder holder = (ViewHolder) convertView.getTag();
            if (holder == null) {
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }

            return holder;
        }
    }
}
