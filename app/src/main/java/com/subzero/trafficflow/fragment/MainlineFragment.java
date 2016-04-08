package com.subzero.trafficflow.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.subzero.trafficflow.R;
import com.subzero.trafficflow.widget.xlistview.XListView;

/**
 * 主路网页面
 * Created by hui on 2016/3/9.
 */
public class MainlineFragment extends BaseFragment implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private View v;

    private ImageView id_iv_choose;
    private RadioGroup groupDown;
    private RadioButton id_auto_car,id_ck_car,id_hc_car,id_crowd;
    private TextView id_tv_time;

    @Override
    protected View initView() {
        v = View.inflate(getActivity(), R.layout.fragment_mainline, null);
        initId();
        initListener();
        return v;
    }

    private void initListener() {
//        id_iv_choose.setOnClickListener(this);
        id_tv_time.setOnClickListener(this);
        groupDown.setOnCheckedChangeListener(this);
    }

    private void initId() {

        id_iv_choose = (ImageView) v.findViewById(R.id.id_iv_choose);
        id_tv_time = (TextView) v.findViewById(R.id.id_tv_time);
        groupDown = (RadioGroup) v.findViewById(R.id.rg_group_des);
        id_auto_car = (RadioButton) v.findViewById(R.id.id_auto_car);
        id_ck_car = (RadioButton) v.findViewById(R.id.id_ck_car);
        id_hc_car = (RadioButton) v.findViewById(R.id.id_hc_car);
        id_crowd = (RadioButton) v.findViewById(R.id.id_crowd);
//        id_explistview = () v.findViewById(R.id.id_explistview);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

    }
}
