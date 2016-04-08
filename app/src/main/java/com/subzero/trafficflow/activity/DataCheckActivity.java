package com.subzero.trafficflow.activity;

import android.content.Intent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import com.subzero.trafficflow.HttpConfig.UrlConstants;
import com.subzero.trafficflow.R;
import com.subzero.trafficflow.Utils.DateUtil;
import com.subzero.trafficflow.Utils.JsonUtil;
import com.subzero.trafficflow.Utils.LogUtil;
import com.subzero.trafficflow.Utils.ToastUtil;
import com.subzero.trafficflow.bean.MinutesCheck;
import com.subzero.trafficflow.response.MinutesCheckResponse;
import com.subzero.trafficflow.widget.com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.subzero.trafficflow.widget.com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;
import com.zhy.http.okhttp.callback.StringCallback;

import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;

/**
 * Created by hui on 2016/3/14.
 */

public class DataCheckActivity extends BaseActivity implements View.OnClickListener {

    private MinutesCheck listData;

    private SimpleDateFormat mFormatter = new SimpleDateFormat("yyy-MM-dd HH:mm");

    private TextView id_tv_time_start;
    private TextView id_tv_time_end;
    private ImageView id_iv_more1;
    private ImageView id_iv_more2;
    private RadioGroup rg_group;

    private TextView id_tv_edit, id_tv_compare;
    private String mTimeStart;
    private String mTimeEnd;
    private DatePicker datepicker_start, datepicker_end;
    private TimePicker timePicker;
    private String gczbs;
    private TextView id_tv_num4;
    private TextView id_tv_num5;
    private TextView id_tv_num6;
    private TextView id_tv_num7;
    private TextView id_tv_num8;
    private TextView id_tv_num9;
    private TextView id_tv_num1;
    private TextView id_tv_num2;
    private TextView id_tv_num3;
    private String start;
    private String end;

    private int tag = 0;
    private int flag = 0;
    private ImageView id_iv_title_back;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_data_check);
        initId();
        initListener();


    }

    private void initId() {
        id_tv_time_start = (TextView) findViewById(R.id.id_tv_time_start);
        id_tv_time_end = (TextView) findViewById(R.id.id_tv_time_end);
        id_iv_more1 = (ImageView) findViewById(R.id.id_iv_more1);
        id_iv_more2 = (ImageView) findViewById(R.id.id_iv_more2);
        id_iv_title_back = (ImageView) findViewById(R.id.id_iv_title_back);
        rg_group = (RadioGroup) findViewById(R.id.rg_group_des);
        id_tv_edit = (TextView) findViewById(R.id.id_tv_edit);
        id_tv_compare = (TextView) findViewById(R.id.id_tv_compare);
        id_tv_num4 = (TextView) findViewById(R.id.id_tv_num4);
        id_tv_num5 = (TextView) findViewById(R.id.id_tv_num5);
        id_tv_num6 = (TextView) findViewById(R.id.id_tv_num6);
        id_tv_num7 = (TextView) findViewById(R.id.id_tv_num7);
        id_tv_num8 = (TextView) findViewById(R.id.id_tv_num8);
        id_tv_num9 = (TextView) findViewById(R.id.id_tv_num9);
        id_tv_num1 = (TextView) findViewById(R.id.id_tv_num1);
        id_tv_num2 = (TextView) findViewById(R.id.id_tv_num2);
        id_tv_num3 = (TextView) findViewById(R.id.id_tv_num3);

    }


    @Override
    public void initHttp(String url) {
        url = UrlConstants.MINUTES_CHECK;
        super.initHttp(url);
    }

    private void RequestMinutesCheckData(String gczbs, String xsfx, String startTime, String endTime) {
        showProgressDialog("数据加载中.....");
        base.addParams("gczbs", gczbs)
                .addParams("xsfx", xsfx)
                .addParams("startTime", startTime)
                .addParams("endTime", endTime)
                .addParams("cols", "XKC,DKC,XHC,ZHC,DHC,TDH,JZX,MTC,TLJ")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                LogUtil.e("error", "error");
                e.printStackTrace();
            }

            @Override
            public void onResponse(String response) {
                dismissProgressDialog();

                LogUtil.e("response-----check", response);

                String res = JsonUtil.getFieldValue(response, "result");
                MinutesCheckResponse minutesCheckResponse = JsonUtil.parseJsonToBean(res, MinutesCheckResponse.class);
                if (minutesCheckResponse != null && minutesCheckResponse.isSuccess()) {

                    id_tv_num4.setText(String.valueOf(minutesCheckResponse.getData().getXKC()));
                    id_tv_num5.setText(String.valueOf(minutesCheckResponse.getData().getDKC()));
                    id_tv_num6.setText(String.valueOf(minutesCheckResponse.getData().getXHC()));
                    id_tv_num7.setText(String.valueOf(minutesCheckResponse.getData().getZHC()));
                    id_tv_num8.setText(String.valueOf(minutesCheckResponse.getData().getDHC()));
                    id_tv_num9.setText(String.valueOf(minutesCheckResponse.getData().getTDH()));
                    id_tv_num1.setText(String.valueOf(minutesCheckResponse.getData().getJZX()));
                    id_tv_num2.setText(String.valueOf(minutesCheckResponse.getData().getMTC()));
                    id_tv_num3.setText(String.valueOf(minutesCheckResponse.getData().getTLJ()));

                }
            }
        });
    }

    @Override
    protected void initData() {

        gczbs = this.getIntent().getStringExtra("gczbs");

        LogUtil.e("gczbs--------check", gczbs);

        start = id_tv_time_start.getText().toString().trim();
        end = id_tv_time_end.getText().toString().trim();
        start = "2016-03-09 05:23";
        end = "2016-03-10 06:47";
        RequestMinutesCheckData(gczbs, "A", start, end);

    }

    private void initListener() {
        mTimeStart = id_tv_time_start.getText().toString();
        mTimeEnd = id_tv_time_end.getText().toString();
        id_tv_time_start.setOnClickListener(this);
        id_tv_time_end.setOnClickListener(this);
        id_iv_more1.setOnClickListener(this);
        id_iv_more2.setOnClickListener(this);
        id_tv_edit.setOnClickListener(this);
        id_tv_compare.setOnClickListener(this);
        id_iv_title_back.setOnClickListener(this);
        rg_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.id_broken:
                        RequestMinutesCheckData(gczbs, "A", start, end);
                        break;
                    case R.id.id_up:
                        RequestMinutesCheckData(gczbs, "S", start, end);
                        break;
                    case R.id.id_dowm:
                        RequestMinutesCheckData(gczbs, "X", start, end);
                        break;

                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_tv_time_start:
            case R.id.id_iv_more1:
                SelectStartTime(1);
                break;
            case R.id.id_tv_time_end:
            case R.id.id_iv_more2:
                SelectStartTime(2);
                break;
            case R.id.id_tv_edit:
                Intent intentEdit = new Intent();
                intentEdit.setClass(this, EditActivity.class);
                startActivity(intentEdit);
                finish();
                break;
            case R.id.id_tv_compare:
                Intent intentComp = new Intent();
                intentComp.setClass(this, ComPareActivity.class);
                startActivity(intentComp);
                break;
            case R.id.id_iv_title_back:
                finish();
                break;
        }

    }

    private void SelectStartTime(final int type) {

        setTheme(android.R.style.Theme_DeviceDefault_NoActionBar);

        new SlideDateTimePicker.Builder(getSupportFragmentManager())
                .setListener(new SlideDateTimeListener() {
                    @Override
                    public void onDateTimeSet(Date date) {
                        if (type == 1) {
                            id_tv_time_start.setText(mFormatter.format(date));
                            start = id_tv_time_start.getText().toString().trim();
                            tag = 1;
                        } else if (type == 2) {
                            id_tv_time_end.setText(mFormatter.format(date));
                            end = id_tv_time_end.getText().toString().trim();
//                            flag = 1;

                            LogUtil.e("start", start);
                            LogUtil.e("end", end);


                            if (1 == DateUtil.compare_date(end, start)) {

                                RequestMinutesCheckData(gczbs, "A", start, end);

                                LogUtil.e("time--choose", "我是时间选择后的数据。。。。。");

                            } else if (-1 == DateUtil.compare_date(end, start)) {

                                ToastUtil.showNormalToast("请检查你所选择的时间段");
                                return;
                            }


                        }

                    }
                })
                .setInitialDate(new Date())
                        //.setMinDate(minDate)
                        //.setMaxDate(maxDate)
                .setIs24HourTime(true)
                        //.setTheme(SlideDateTimePicker.HOLO_DARK)
                        //.setIndicatorColor(Color.parseColor("#990000"))
                .build()
                .show();
    }

}
