package com.subzero.trafficflow.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.subzero.trafficflow.R;
import com.subzero.trafficflow.bean.Month;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hui on 2016/3/10.
 */
public class MonthItemAdapter extends BaseAdapter {
    private ArrayList<Month> listRank;
    private Context context;
    private int type;


    public MonthItemAdapter(ArrayList<Month> listRank, Context context,int type) {
        this.listRank = listRank;
        this.context = context;
        this.type = type;

    }

    public MonthItemAdapter(Context context) {
        this.listRank = new ArrayList<Month>();
        this.context = context;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        if (listRank != null) {

            return listRank.size();
        }
        return 0;

    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return listRank.get(arg0);
    }

    public void addItem(List<Month> list) {
        this.listRank.addAll(list);
        notifyDataSetChanged();
    }

    public void refreshItem(List<Month> list) {
        this.listRank.clear();
        this.listRank.addAll(list);
        notifyDataSetChanged();
    }

    public List<Month> getList() {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.xlistitem_item_month, null);
            holder = new ViewHolder();
            holder.id_tv_rank_item = (TextView) convertView.findViewById(R.id.id_tv_rank_item);
            holder.id_tv_name = (TextView) convertView.findViewById(R.id.id_tv_name);
            holder.id_tv_pollutecount = (TextView) convertView.findViewById(R.id.id_tv_pollutecount);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Month nb = listRank.get(position);
        holder.id_tv_rank_item.setText(String.format("%d", position + 1));
        holder.id_tv_name.setText(String.valueOf(nb.getMONTH())+"月");
        switch (type) {
            case 1:
                holder.id_tv_pollutecount.setText(String.valueOf(nb.getJDC_DL()));
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
            case 5:
                holder.id_tv_pollutecount.setText(String.valueOf(nb.getYJD()));
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
