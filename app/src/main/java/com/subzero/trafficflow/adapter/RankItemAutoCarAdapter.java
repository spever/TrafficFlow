package com.subzero.trafficflow.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.subzero.trafficflow.R;
import com.subzero.trafficflow.bean.AutoCar;
import com.subzero.trafficflow.bean.RankItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hui on 2016/3/10.
 */
public class RankItemAutoCarAdapter extends BaseAdapter {
    private ArrayList<AutoCar> listRank;
    private Context context;

    public RankItemAutoCarAdapter(ArrayList<AutoCar> listRank, Context context) {
        this.listRank = listRank;
        this.context=context;
    }

    public RankItemAutoCarAdapter(Context context) {
        this.listRank = new ArrayList<AutoCar>();
        this.context=context;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return listRank.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return listRank.get(arg0);
    }

    public void addItem(List<AutoCar> list){
        this.listRank.addAll(list);
        notifyDataSetChanged();
    }
    public void refreshItem(List<AutoCar> list){
        this.listRank.clear();
        this.listRank.addAll(list);
        notifyDataSetChanged();
    }

    public List<AutoCar> getList() {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.xlistitem_item_rank, null);
            holder = new ViewHolder();
            holder.id_tv_rank_item = (TextView) convertView.findViewById(R.id.id_tv_rank_item);
            holder.id_tv_name = (TextView) convertView.findViewById(R.id.id_tv_name);
            holder.id_tv_number = (TextView) convertView.findViewById(R.id.id_tv_number);
            holder.id_tv_count = (TextView) convertView.findViewById(R.id.id_tv_count);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        AutoCar ac = listRank.get(position);
        holder.id_tv_rank_item.setText(position+1+"");
        holder.id_tv_name.setText(ac.getGCZMC());
        holder.id_tv_number.setText(ac.getGCZBH());
		holder.id_tv_count.setText(String.valueOf(ac.getJDC_DL()));

        return convertView;
    }
    
    public static class ViewHolder {
        public TextView id_tv_rank_item;
		public TextView id_tv_name;
        public TextView id_tv_number;
        public TextView id_tv_count;

    }
}
