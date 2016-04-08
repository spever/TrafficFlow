package com.subzero.trafficflow.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Handler;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.subzero.trafficflow.HttpConfig.UrlConstants;
import com.subzero.trafficflow.R;
import com.subzero.trafficflow.Utils.JsonUtil;
import com.subzero.trafficflow.Utils.LogUtil;
import com.subzero.trafficflow.Utils.ToastUtil;
import com.subzero.trafficflow.adapter.HourItemAdapter;
import com.subzero.trafficflow.adapter.MinutesItemAdapter;
import com.subzero.trafficflow.bean.Hour;
import com.subzero.trafficflow.response.HourResponse;
import com.subzero.trafficflow.response.MinutesResponse;
import com.subzero.trafficflow.widget.xlistview.XListView;
import com.zhy.http.okhttp.callback.StringCallback;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import okhttp3.Call;

/**
 * 24小时页面
 * Created by hui on 2016/3/11.
 */
public class HourFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener, View.OnClickListener, XListView.IXListViewListener {

    private View v;
    private View header;
    private XListView xListView;
    private LineChart mLineChart;
    private Legend mLegend;


    private TextView id_tv_time_hour;
    private RadioGroup groupDown, groupUp;
    private RadioButton id_broken;
    private RadioButton id_up;
    private RadioButton id_dowm;
    private ImageView imageView2;
    private RadioButton id_auto_car;
    private RadioButton id_ck_car;
    private RadioButton id_hc_car;
    private RadioButton id_speed;

    private int iYear;
    private int iMonth;
    private int iDay;
    private Calendar calendar;
    private String str;
    private String gczbs;
    private String time;

    private RadioButton tag;
    private RadioButton flag;
    private ArrayList<Hour> list_hour;

    private Handler mHandler = new Handler();

    @Override
    protected View initView() {
        v = View.inflate(getActivity(), R.layout.fragment_label_item, null);
        initXlistView();
        initId();
        time = id_tv_time_hour.getText().toString().trim();
        initListener();
        return v;
    }

    private void initListener() {
        groupUp.setOnCheckedChangeListener(this);
        groupDown.setOnCheckedChangeListener(this);
        id_tv_time_hour.setOnClickListener(this);
        imageView2.setOnClickListener(this);
    }

    private void initXlistView() {
        xListView = (XListView) v.findViewById(R.id.id_xlist_label);
        header = View.inflate(getActivity(), R.layout.xlistview_header_hour, null);
        xListView.addHeaderView(header);
        xListView.setXListViewListener(this);
        xListView.setPullLoadEnable(false);
        xListView.setPullRefreshEnable(true);
    }

    private void initId() {
        groupUp = (RadioGroup) header.findViewById(R.id.rg_group_des_up);
        id_broken = (RadioButton) header.findViewById(R.id.id_broken);
        id_up = (RadioButton) header.findViewById(R.id.id_up);
        id_dowm = (RadioButton) header.findViewById(R.id.id_dowm);

        groupDown = (RadioGroup) header.findViewById(R.id.rg_group_des);
        id_auto_car = (RadioButton) header.findViewById(R.id.id_auto_car);
        id_ck_car = (RadioButton) header.findViewById(R.id.id_ck_car);
        id_hc_car = (RadioButton) header.findViewById(R.id.id_hc_car);
        id_speed = (RadioButton) header.findViewById(R.id.id_speed);

        imageView2 = (ImageView) header.findViewById(R.id.imageView2);
        id_tv_time_hour = (TextView) header.findViewById(R.id.id_tv_time_hour);
    }

    @Override
    protected void initData() {

        gczbs = getActivity().getIntent().getStringExtra("gczbs");

        calendar = Calendar.getInstance();
        iYear = calendar.get(Calendar.YEAR);
        iMonth = calendar.get(Calendar.MONTH);
        iDay = calendar.get(Calendar.DAY_OF_MONTH);

        RequestHourData(gczbs, time, "A", 1);


    }

    @Override
    public void initHttp(String url) {
        url = UrlConstants.HOUR;
        super.initHttp(url);
    }

    //    XSFX	A	断面
//    XSFX	S	上行
//    XSFX	X	下行

    private void RequestHourData(String gczbs, String date, String xsfx, final int type) {

        showProgressDialog("数据加载中......");
        base.addParams("gcrq", date)
                .addParams("xsfx", xsfx) //行驶方向
                .addParams("gczbs", gczbs)
                .addParams("cols", "HOUR,JDC_DL,KC_DL,HC_DL,JDC_CS,YDDJ")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {

                LogUtil.e("error", "error");

            }

            @Override
            public void onResponse(String response) {
                dismissProgressDialog();
                LogUtil.e("response=======================", response);
                String res = JsonUtil.getFieldValue(response, "result");

                LogUtil.e("res==hour----", res);
                HourResponse hourResponse = JsonUtil.parseJsonToBean(res, HourResponse.class);

                if (hourResponse != null && hourResponse.isSuccess()) {

                    list_hour = hourResponse.getData();

                    initChartData();

                    xListView.setAdapter(new HourItemAdapter(list_hour, getActivity(), type));

                }

            }
        });
    }

    private void initChartData() {
        mLineChart = (LineChart) header.findViewById(R.id.id_chart_line);
        mLineChart.setDescription("demo");
        mLineChart.setScaleEnabled(false); //设置图表是否可缩放
        mLineChart.setBackgroundColor(getResources().getColor(R.color.theme_color)); //设置图表背景颜色
        mLineChart.setDrawGridBackground(false);  //设置是否显示表格
        mLineChart.setTouchEnabled(false);

        mLegend = mLineChart.getLegend();//坐标线描述的样式
        mLegend.setEnabled(false);


        XAxis xAxis = mLineChart.getXAxis();     //得到图表的X轴实例
        xAxis.setTextColor(Color.WHITE);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置X轴的显示位置
        xAxis.setDrawGridLines(true);  //设置是否显示X轴表格
        xAxis.setAvoidFirstLastClipping(true); //设置x轴起点和终点label不超出屏幕
        xAxis.setDrawAxisLine(true);           //设置显示x轴
        xAxis.setSpaceBetweenLabels(0); // 设置x轴label不间隔

        YAxis leftAxis = mLineChart.getAxisLeft();  //得到图表的左侧Y轴实例
        leftAxis.setTextColor(Color.WHITE);
        leftAxis.setStartAtZero(true);   //设置图表起点从0开始
        leftAxis.enableGridDashedLine(10f, 5f, 0f); //设置横向表格为虚线

        leftAxis.setDrawLimitLinesBehindData(true);
        mLineChart.getAxisRight().setEnabled(false);

        setData();
        mLineChart.invalidate();

    }

    private void setData() {
        ArrayList<String> xVals = new ArrayList<String>();
        ArrayList<Entry> yVals = new ArrayList<Entry>();


        for (int i = 0; i < list_hour.size(); i++) {
            xVals.add(String.valueOf(list_hour.get(i).getHOUR()));
            yVals.add(new Entry(list_hour.get(i).getJDC_DL(), i));
        }
        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(yVals, "DataSet 1");
        set1.setDrawCubic(false);
        set1.setLineWidth(1.0f);
        set1.setCircleSize(3f);
        set1.setDrawValues(false); //是否在点上绘制values
        set1.setCircleColor(Color.parseColor("#fd4634"));
        set1.setDrawFilled(false);//设置曲线填充是启用
        set1.setFillColor(getResources().getColor(R.color.line_filled));//射置曲线填充颜色
        set1.setFillAlpha(128);//射置曲线填充颜色透明度
        // create a data object with the datasets

        LineData data = new LineData(xVals, set1);
        mLineChart.setData(data);

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        if (group == groupUp) {
            switch (checkedId) {
                case R.id.id_broken:
                    tag = id_broken;
                    tag.setChecked(true);
                    if (tag.isChecked() && id_auto_car.isChecked()) {
                        ToastUtil.showToast("断面机动车组合");
                        RequestHourData(gczbs, time, "A", 1);
                    }
                    if (tag.isChecked() && id_ck_car.isChecked()) {
                        ToastUtil.showToast("断面客车组合");
                        RequestHourData(gczbs, time, "A", 2);
                    }
                    if (tag.isChecked() && id_hc_car.isChecked()) {
                        ToastUtil.showToast("断面货车组合");
                        RequestHourData(gczbs, time, "A", 3);
                    }
                    if (tag.isChecked() && id_speed.isChecked()) {
                        ToastUtil.showToast("断面车速组合");
                        RequestHourData(gczbs, time, "A", 4);
                    }
                    break;

                case R.id.id_up:
                    tag = id_up;
                    tag.setChecked(true);
                    if (tag.isChecked() && id_auto_car.isChecked()) {
                        ToastUtil.showToast("上行机动车组合");
                        RequestHourData(gczbs, time, "S", 1);
                    }
                    if (tag.isChecked() && id_ck_car.isChecked()) {
                        ToastUtil.showToast("上行客车组合");
                        RequestHourData(gczbs, time, "S", 2);
                    }
                    if (tag.isChecked() && id_hc_car.isChecked()) {
                        ToastUtil.showToast("上行货车组合");
                        RequestHourData(gczbs, time, "S", 3);
                    }
                    if (tag.isChecked() && id_speed.isChecked()) {
                        ToastUtil.showToast("上行车速组合");
                        RequestHourData(gczbs, time, "S", 4);
                    }

                    break;
                case R.id.id_dowm:
                    tag = id_dowm;
                    tag.setChecked(true);
                    if (tag.isChecked() && id_auto_car.isChecked()) {
                        ToastUtil.showToast("下行机动车组合");
                        RequestHourData(gczbs, time, "X", 1);
                    }
                    if (tag.isChecked() && id_ck_car.isChecked()) {
                        ToastUtil.showToast("下行客车组合");
                        RequestHourData(gczbs, time, "X", 2);
                    }
                    if (tag.isChecked() && id_hc_car.isChecked()) {
                        ToastUtil.showToast("下行货车组合");
                        RequestHourData(gczbs, time, "X", 3);
                    }
                    if (tag.isChecked() && id_speed.isChecked()) {
                        ToastUtil.showToast("下行车速组合");
                        RequestHourData(gczbs, time, "X", 4);
                    }
                    break;
            }

        }

        if (group == groupDown) {
            switch (checkedId) {
                case R.id.id_auto_car:
                    flag = id_auto_car;
                    flag.setChecked(true);
                    if (flag.isChecked() && id_broken.isChecked()) {
                        ToastUtil.showToast("断面机动车组合");
                        RequestHourData(gczbs, time, "A", 1);
                    }
                    if (flag.isChecked() && id_up.isChecked()) {
                        ToastUtil.showToast("上行机动车组合");
                        RequestHourData(gczbs, time, "S", 1);
                    }
                    if (flag.isChecked() && id_dowm.isChecked()) {
                        ToastUtil.showToast("下行机动车组合");
                        RequestHourData(gczbs, time, "X", 1);
                    }
                    break;
                case R.id.id_ck_car:
                    flag = id_ck_car;
                    flag.setChecked(true);
                    if (flag.isChecked() && id_broken.isChecked()) {
                        ToastUtil.showToast("断面客车组合");
                        RequestHourData(gczbs, time, "A", 2);
                    }
                    if (flag.isChecked() && id_up.isChecked()) {
                        ToastUtil.showToast("上行客车组合");
                        RequestHourData(gczbs, time, "S", 2);
                    }
                    if (flag.isChecked() && id_dowm.isChecked()) {
                        ToastUtil.showToast("下行客车组合");
                        RequestHourData(gczbs, time, "X", 2);
                    }
                    break;
                case R.id.id_hc_car:
                    flag = id_hc_car;
                    flag.setChecked(true);
                    if (flag.isChecked() && id_broken.isChecked()) {
                        ToastUtil.showToast("断面货车组合");
                        RequestHourData(gczbs, time, "A", 3);
                    }
                    if (flag.isChecked() && id_up.isChecked()) {
                        ToastUtil.showToast("上行货车组合");
                        RequestHourData(gczbs, time, "S", 3);
                    }
                    if (flag.isChecked() && id_dowm.isChecked()) {
                        ToastUtil.showToast("下行货车组合");
                        RequestHourData(gczbs, time, "X", 3);
                    }
                    break;
                case R.id.id_speed:
                    flag = id_speed;
                    flag.setChecked(true);
                    if (flag.isChecked() && id_broken.isChecked()) {
                        ToastUtil.showToast("断面车速组合");
                        RequestHourData(gczbs, time, "A", 4);
                    }
                    if (flag.isChecked() && id_up.isChecked()) {
                        ToastUtil.showToast("上行车速组合");
                        RequestHourData(gczbs, time, "S", 4);
                    }
                    if (flag.isChecked() && id_dowm.isChecked()) {
                        ToastUtil.showToast("下行车速组合");

                        LogUtil.e("id_tv_time_minutes================", time);
                        RequestHourData(gczbs, time, "X", 4);
                    }
                    break;
            }

        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_tv_time_hour:
            case R.id.imageView2:
                selectTime();
                break;
        }

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

            id_tv_time_hour.setText(str);

            time = id_tv_time_hour.getText().toString().trim();

            RequestHourData(gczbs, time, "A", 1);

            id_broken.setChecked(true);
            id_auto_car.setChecked(true);
            tag = id_broken;
            tag.setChecked(true);
            flag = id_auto_car;
            flag.setChecked(true);

        }
    };

    @Override
    public void onRefresh() {

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                RequestHourData(gczbs, time, "A", 1);
                onload();
                id_broken.setChecked(true);
                id_auto_car.setChecked(true);
                tag = id_broken;
                tag.setChecked(true);
                flag = id_auto_car;
                flag.setChecked(true);
            }
        }, 2000);

    }

    private void onload() {
        xListView.stopRefresh();
        // 获取当前时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String data = sdf.format(new Date());
        xListView.setRefreshTime(data);
    }

    @Override
    public void onLoadMore() {

    }
}
