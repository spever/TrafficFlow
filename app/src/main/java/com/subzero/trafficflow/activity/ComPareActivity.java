package com.subzero.trafficflow.activity;

import android.widget.ImageView;
import android.widget.TextView;

import com.subzero.trafficflow.R;
import com.subzero.trafficflow.adapter.CompareItemAdapter;
import com.subzero.trafficflow.bean.DeviceCheck;
import com.subzero.trafficflow.widget.xlistview.XListView;

import java.util.ArrayList;

/**
 * Created by hui on 2016/3/15.
 */
public class ComPareActivity extends BaseActivity {

    private ArrayList<DeviceCheck> listData;
    private ImageView id_iv_edit;
    private TextView id_tv_upload,id_tv_compare_result;
    private XListView xListView;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_compare);
        initId();
        initXlistView();
        
    }

    private void initXlistView() {
        xListView = (XListView) findViewById(R.id.id_xlistView_compare);
    }

    @Override
    protected void initData() {
        xListView.setAdapter(new CompareItemAdapter(this,listData));
    }

    private void initId() {
        id_iv_edit = (ImageView) findViewById(R.id.id_iv_edit);
        id_tv_upload = (TextView) findViewById(R.id.id_tv_upload);
        id_tv_compare_result = (TextView) findViewById(R.id.id_tv_compare_result);
        
    }
}
