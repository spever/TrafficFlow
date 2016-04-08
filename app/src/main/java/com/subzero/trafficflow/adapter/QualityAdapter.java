package com.subzero.trafficflow.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.subzero.trafficflow.R;
import com.subzero.trafficflow.Utils.LogUtil;
import com.subzero.trafficflow.bean.Device;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hui on 2016/3/11.
 */
public class QualityAdapter extends BasicAdapter<Device> {

    private Map<Integer, Boolean> isSelected;

    public Map<Integer, Boolean> getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(Map<Integer, Boolean> isSelected) {
        this.isSelected = isSelected;
    }

    private List beSelectedData = new ArrayList();

    public QualityAdapter(Context context, ArrayList<Device> list) {
        super(context, list);
        isSelected=new HashMap<Integer, Boolean>();
        initData();
    }

    private void initData() {
        for(int i=0;i<list.size();i++){
            getIsSelected().put(i,false);

        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final int pos = position;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.listview_item_quality, null);
        }

        ViewHolder holder = ViewHolder.getHolder(convertView);

        Device device = list.get(position);

        holder.id_tv_name.setText(device.getMAKER_NAME());
        holder.id_tv_type.setText(device.getMODEL_CODE());
        holder.id_tv_num.setText(device.getSBSBM());

        holder.id_cb_quality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 当前点击的CB
                boolean cu = !isSelected.get(pos);
                // 先将所有的置为FALSE
                for (Integer p : isSelected.keySet()) {
                    isSelected.put(p, false);
                }
                // 再将当前选择CB的实际状态
                isSelected.put(pos, cu);
                notifyDataSetChanged();
                beSelectedData.clear();
                if (cu) beSelectedData.add(list.get(pos));

                if (beSelectedData!=null){
                    LogUtil.e("test====","test");
                }

            }
        });

        holder.id_cb_quality.setChecked(isSelected.get(position));

        return convertView;
    }

    static class ViewHolder {
        //        public ImageView iv_icon;
        public TextView id_tv_name;
        public TextView id_tv_type;
        public TextView id_tv_num;
        public CheckBox id_cb_quality;


        public ViewHolder(View convertView) {
            id_tv_name = (TextView) convertView.findViewById(R.id.id_tv_name);
            id_tv_type = (TextView) convertView.findViewById(R.id.id_tv_type);
            id_tv_num = (TextView) convertView.findViewById(R.id.id_tv_num);
            id_cb_quality = (CheckBox) convertView.findViewById(R.id.id_cb_quality);
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
