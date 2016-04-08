package com.subzero.trafficflow.activity;

import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.subzero.trafficflow.R;

import info.hoang8f.android.segmented.SegmentedGroup;

/**
 * Created by hasee on 2016/4/6.
 */
public class RouteLineDetailActivity extends BaseActivity {

    private ImageView id_iv_title_back;
    private TextView id_tv_time_detail;
    private SegmentedGroup groupUp;
    private RadioButton id_day;
    private RadioButton id_month;
    private RadioButton id_year;

    private RadioGroup groupDown;
    private RadioButton id_auto_car, id_ck_car, id_hc_car, id_crowd;
    @Override
    protected void initView() {
        setContentView(R.layout.activity_route_detail);
        initId();
    }

    private void initId() {
        id_iv_title_back = (ImageView) findViewById(R.id.id_iv_title_back);
        id_tv_time_detail = (TextView) findViewById(R.id.id_tv_time_detail);
        groupUp = (SegmentedGroup) findViewById(R.id.view_left);
        id_day = (RadioButton) findViewById(R.id.id_day);
        id_month = (RadioButton) findViewById(R.id.id_month);
        id_year = (RadioButton) findViewById(R.id.id_year);

        groupDown = (RadioGroup) findViewById(R.id.rg_group_des);
        id_auto_car = (RadioButton)findViewById(R.id.id_auto_car);
        id_ck_car = (RadioButton) findViewById(R.id.id_ck_car);
        id_hc_car = (RadioButton) findViewById(R.id.id_hc_car);
        id_crowd = (RadioButton) findViewById(R.id.id_crowd);

    }
}
