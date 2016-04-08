package com.subzero.trafficflow.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.subzero.trafficflow.R;
import com.subzero.trafficflow.bean.Hour;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hui on 2016/3/10.
 */
public class HourItemAdapter extends BaseAdapter {
    private ArrayList<Hour> listRank;
    private Context context;
    private int type;

    public HourItemAdapter(ArrayList<Hour> listRank, Context context, int type) {
        this.listRank = listRank;
        this.context = context;
        this.type = type;
    }

    public HourItemAdapter(Context context) {
        this.listRank = new ArrayList<Hour>();
        this.context = context;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        if (listRank != null)
            return listRank.size();
        return 0;
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return listRank.get(arg0);
    }

    public void addItem(List<Hour> list) {
        this.listRank.addAll(list);
        notifyDataSetChanged();
    }

    public void refreshItem(List<Hour> list) {
        this.listRank.clear();
        this.listRank.addAll(list);
        notifyDataSetChanged();
    }

    public List<Hour> getList() {
        return listRank;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        // TODO Auto-generated method stub

        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.xlistitem_item_hour, null);
            holder = new ViewHolder();
            holder.id_tv_rank_item = (TextView) convertView.findViewById(R.id.id_tv_rank_item);
            holder.id_tv_name = (TextView) convertView.findViewById(R.id.id_tv_name);
            holder.id_tv_pollutecount = (TextView) convertView.findViewById(R.id.id_tv_pollutecount);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Hour nb = listRank.get(position);
        holder.id_tv_rank_item.setText(String.valueOf(position + 1));
        int hour = nb.getHOUR();
        String label = "";
        switch (hour) {
            case 1:
                label = "01:00";
                break;
            case 2:
                label = "02:00";
                break;
            case 3:
                label = "03:00";
                break;
            case 4:
                label = "04:00";
                break;
            case 5:
                label = "05:00";
                break;
            case 6:
                label = "06:00";
                break;
            case 7:
                label = "07:00";
                break;
            case 8:
                label = "08:00";
                break;
            case 9:
                label = "09:00";
                break;
            case 10:
                label = "10:00";
                break;
            case 11:
                label = "11:00";
                break;
            case 12:
                label = "12:00";
                break;
            case 13:
                label = "13:00";
                break;
            case 14:
                label = "14:00";
                break;
            case 15:
                label = "15:00";
                break;
            case 16:
                label = "16:00";
                break;
            case 17:
                label = "17:00";
                break;
            case 18:
                label = "18:00";
                break;
            case 19:
                label = "19:00";
                break;
            case 20:
                label = "20:00";
                break;
            case 21:
                label = "21:00";
                break;
            case 22:
                label = "22:00";
                break;
            case 23:
                label = "23:00";
                break;
            case 24:
                label = "00:00";
                break;
        }
        holder.id_tv_name.setText(label);
        switch (type) {
            case 1:
                holder.id_tv_pollutecount.setText(String.valueOf(nb.getJDC_CS()));
                break;
            case 2:
                holder.id_tv_pollutecount.setText(String.valueOf(nb.getKC_DL()));
                break;
            case 3:
                holder.id_tv_pollutecount.setText(String.valueOf(nb.getHC_DL()));
                break;
            case 4:
                holder.id_tv_pollutecount.setText(String.valueOf(nb.getJDC_CS()));
                break;

        }

        return convertView;
    }

    public static class ViewHolder {
        public TextView id_tv_rank_item;
        public TextView id_tv_name;
        public TextView id_tv_pollutecount;

    }
}
