package com.subzero.trafficflow.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.subzero.trafficflow.R;
import com.subzero.trafficflow.Utils.LogUtil;
import com.subzero.trafficflow.activity.FilterActivity;
import com.subzero.trafficflow.widget.xlistview.XListView;

import java.util.Calendar;

/**
 * 路线页面
 * Created by hui on 2016/3/9.
 */
public class RouteLineFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    private View v;
    private RadioGroup groupDown;
    private RadioButton id_auto_car, id_ck_car, id_hc_car, id_crowd;
    private ImageView id_iv_filter;
    private TextView id_tv_time;
    private Calendar calendar;
    private String str;
    private String time;

    private int iYear;
    private int iMonth;
    private int iDay;
    private XListView xlistView;

    @Override
    protected View initView() {
        v = View.inflate(getActivity(), R.layout.fragment_routeline, null);
        initId();
        initListener();
        return v;
    }

    private void initListener() {
        id_iv_filter.setOnClickListener(this);
        id_tv_time.setOnClickListener(this);
        groupDown.setOnCheckedChangeListener(this);
    }

    private void initId() {

        id_iv_filter = (ImageView) v.findViewById(R.id.id_iv_filter);
        id_tv_time = (TextView) v.findViewById(R.id.id_tv_time);
        groupDown = (RadioGroup) v.findViewById(R.id.rg_group_des);
        id_auto_car = (RadioButton) v.findViewById(R.id.id_auto_car);
        id_ck_car = (RadioButton) v.findViewById(R.id.id_ck_car);
        id_hc_car = (RadioButton) v.findViewById(R.id.id_hc_car);
        id_crowd = (RadioButton) v.findViewById(R.id.id_crowd);
        xlistView = (XListView) v.findViewById(R.id.id_xlistview);
    }


    @Override
    protected void initData() {

        calendar = Calendar.getInstance();
        iYear = calendar.get(Calendar.YEAR);
        iMonth = calendar.get(Calendar.MONTH);
        iDay = calendar.get(Calendar.DAY_OF_MONTH);


    }

    private void selectTime() {
        DatePickerDialog picker = new DatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, listener, iYear, iMonth, iDay);
        picker.setCancelable(true);
        picker.setCanceledOnTouchOutside(true);
        picker.show();

    }

    private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            // 每次保存设置的日期
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            str = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;

            LogUtil.e("str", str);

            id_tv_time.setText(str);

            time = id_tv_time.getText().toString().trim();

        }
    };

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.id_auto_car:

                break;
            case R.id.id_ck_car:

                break;
            case R.id.id_hc_car:

                break;
            case R.id.id_crowd:

                break;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_iv_filter:
                Intent intent = new Intent();
                intent.setClass(getActivity(), FilterActivity.class);
                startActivity(intent);
                break;
            case R.id.id_tv_time:
                selectTime();
                break;
        }

    }

}
