package com.subzero.trafficflow.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.subzero.trafficflow.R;
import com.subzero.trafficflow.bean.DeviceCheck;

import java.util.ArrayList;

/**
 * Created by hui on 2016/3/16.
 */
public class CompareItemAdapter extends BasicAdapter<DeviceCheck> {
    public CompareItemAdapter(Context context, ArrayList<DeviceCheck> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = View.inflate(context, R.layout.xlistitem_item_compare, null);
        }
        return convertView;
    }

    static class ViewHolder {
        public TextView id_tv_name;
        public TextView id_tv_input_by;
        public TextView id_tv_device_data;
        public TextView id_tv_percent;

        public ViewHolder(View convertView) {
            id_tv_name = (TextView) convertView.findViewById(R.id.id_tv_name);
            id_tv_input_by = (TextView) convertView.findViewById(R.id.id_tv_input_by);
            id_tv_device_data = (TextView) convertView.findViewById(R.id.id_tv_device_data);
            id_tv_percent = (TextView) convertView.findViewById(R.id.id_tv_percent);
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
