package com.subzero.trafficflow.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Handler;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.subzero.trafficflow.HttpConfig.UrlConstants;
import com.subzero.trafficflow.R;
import com.subzero.trafficflow.Utils.DateUtil;
import com.subzero.trafficflow.Utils.JsonUtil;
import com.subzero.trafficflow.Utils.LogUtil;
import com.subzero.trafficflow.Utils.ToastUtil;
import com.subzero.trafficflow.adapter.DayItemAdapter;
import com.subzero.trafficflow.adapter.MonthItemAdapter;
import com.subzero.trafficflow.bean.Month;
import com.subzero.trafficflow.response.DayResponse;
import com.subzero.trafficflow.response.MonthResponse;
import com.subzero.trafficflow.widget.YearPickerDialog;
import com.subzero.trafficflow.widget.xlistview.XListView;
import com.zhy.http.okhttp.callback.StringCallback;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import okhttp3.Call;

/**
 * 每月页面
 * Created by hui on 2016/3/11.
 */
public class MonthFragment extends BaseFragment implements View.OnClickListener, RadioGroup.OnCheckedChangeListener, XListView.IXListViewListener {

    private View v;
    private View header;
    private XListView xListView;

    public BarChart mBarChart;
    private LineChart mLineChart;
    private ArrayList<BarEntry> entries;
    public BarDataSet dataset;
    private ArrayList<String> labels;
    private Legend mLegend;

    private RadioGroup groupDown, groupUp;
    private RadioButton id_broken;
    private RadioButton id_up;
    private RadioButton id_dowm;

    private RadioButton tag;
    private RadioButton flag;

    private Handler mHandler = new Handler();


    private ImageView imageView2;
    private RadioButton id_auto_car;
    private RadioButton id_ck_car;
    private RadioButton id_hc_car;
    private RadioButton id_speed;
    private RadioButton id_crowd;
    private TextView id_tv_time_month;
    private Calendar calendar;
    private String gczbs;
    private ArrayList<Month> list_month;

    private String inputYear;


    @Override
    protected View initView() {
        v = View.inflate(getActivity(), R.layout.fragment_label_item, null);
        initXlistView();
        initId();
        initListener();
        return v;
    }

    private void initListener() {
        id_tv_time_month.setOnClickListener(this);
        imageView2.setOnClickListener(this);
        groupUp.setOnCheckedChangeListener(this);
        groupDown.setOnCheckedChangeListener(this);
    }

    private void initXlistView() {
        xListView = (XListView) v.findViewById(R.id.id_xlist_label);
        header = View.inflate(getActivity(), R.layout.xlistview_header_month, null);
        xListView.addHeaderView(header);
        xListView.setXListViewListener(this);
        xListView.setPullRefreshEnable(true);
        xListView.setPullLoadEnable(false);
    }

    private void initId() {
        mBarChart = (BarChart) header.findViewById(R.id.id_chart_bar);
        mLineChart = (LineChart) header.findViewById(R.id.id_chart_line);
        groupUp = (RadioGroup) header.findViewById(R.id.rg_group_des_up);
        id_broken = (RadioButton) header.findViewById(R.id.id_broken);
        id_up = (RadioButton) header.findViewById(R.id.id_up);
        id_dowm = (RadioButton) header.findViewById(R.id.id_dowm);

        groupDown = (RadioGroup) header.findViewById(R.id.rg_group_des);
        id_auto_car = (RadioButton) header.findViewById(R.id.id_auto_car);
        id_ck_car = (RadioButton) header.findViewById(R.id.id_ck_car);
        id_hc_car = (RadioButton) header.findViewById(R.id.id_hc_car);
        id_speed = (RadioButton) header.findViewById(R.id.id_speed);
        id_crowd = (RadioButton) header.findViewById(R.id.id_crowd);

        imageView2 = (ImageView) header.findViewById(R.id.imageView2);

        id_tv_time_month = (TextView) header.findViewById(R.id.id_tv_calendar);
    }


    @Override
    public void initHttp(String url) {
        url = UrlConstants.MONTH;
        super.initHttp(url);
    }

    private void RequestMonthData(String gczbs, String year, String xsfx, final int type) {

        showProgressDialog("数据加载中......");
        base.addParams("year", year)
                .addParams("xsfx", xsfx) //行驶方向
                .addParams("gczbs", gczbs)
                .addParams("cols", "MONTH,JDC_DL,KC_DL,HC_DL,JDC_CS,YJD,YDDJ")
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

                LogUtil.e("res==month", res);
                MonthResponse monthResponse = JsonUtil.parseJsonToBean(res, MonthResponse.class);


                LogUtil.e("response", monthResponse + "");
                if (monthResponse != null && monthResponse.isSuccess()) {

                    list_month = monthResponse.getData();

                    if (type == 1) {
                        initBarCharttData();
                        mBarChart.setVisibility(View.VISIBLE);
                        mLineChart.setVisibility(View.GONE);
                    } else {
                        initChartData();
                        mBarChart.setVisibility(View.GONE);
                        mLineChart.setVisibility(View.VISIBLE);
                    }

                    xListView.setAdapter(new MonthItemAdapter(list_month, getActivity(), type));


                }

            }
        });
    }

    @Override
    protected void initData() {

        inputYear = id_tv_time_month.getText().toString().trim();

        gczbs = getActivity().getIntent().getStringExtra("gczbs");

        RequestMonthData(gczbs, inputYear, "A", 1);
    }

    private void initChartData() {

        mLineChart.setDescription("");
        mLineChart.setScaleEnabled(true); //设置图表是否可缩放
        mLineChart.setBackgroundColor(getResources().getColor(R.color.theme_color)); //设置图表背景颜色
        mLineChart.setDrawGridBackground(false);  //设置是否显示表格
        mLineChart.setTouchEnabled(true);

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


        for (int i = 0; i < list_month.size(); i++) {
            xVals.add(String.valueOf(list_month.get(i).getMONTH()));
            yVals.add(new Entry(list_month.get(i).getJDC_DL(), i));
        }

        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(yVals, "DataSet 1");
        set1.setDrawCubic(false);
        set1.setLineWidth(1.0f);
        set1.setCircleSize(3f);
        set1.setCircleColor(Color.parseColor("#fd4634"));
        set1.setDrawFilled(false);//设置曲线填充是启用
        set1.setFillColor(getResources().getColor(R.color.line_filled));//射置曲线填充颜色
        set1.setFillAlpha(128);//射置曲线填充颜色透明度
        // create a data object with the datasets

        set1.setHighlightEnabled(false);
        set1.setDrawValues(false);//去掉values值
        set1.setDrawCircles(true);//去掉values小圆圈


        LineData data = new LineData(xVals, set1);
        mLineChart.setData(data);

        mLineChart.animateX(5000);

    }

    private void initBarCharttData() {

        initEntriesData();
        initLableData();
        show();

    }

    private void show() {
        dataset = new BarDataSet(entries, "# of Calls");
        dataset.setColors(ColorTemplate.COLORFUL_COLORS);
        BarData data = new BarData(labels, dataset);
        LogUtil.e("label", labels.toString());
        LimitLine line = new LimitLine(10f);
        mBarChart.setDescription(" ");

        mBarChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        mBarChart.getXAxis().setAvoidFirstLastClipping(true);
        mBarChart.getXAxis().setTextColor(Color.WHITE);

        mBarChart.getAxisRight().setEnabled(false);
        mBarChart.getAxisRight().setTextColor(Color.WHITE);

        mBarChart.setData(data);
        mBarChart.setScaleEnabled(false);
        mBarChart.animateY(2000);

    }

    private void initLableData() {
        labels = new ArrayList<>();

        for (int i = 0; i < list_month.size(); i++) {
            labels.add(String.valueOf(list_month.get(i).getMONTH()));
        }

    }

    private void initEntriesData() {
        entries = new ArrayList<>();
        for (int i = 0; i < list_month.size(); i++) {
            entries.add(new BarEntry(Float.parseFloat(list_month.get(i).getJDC_CS()), i));
        }

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.imageView2:
            case R.id.id_tv_calendar:
                selectYearTime();
                break;
        }
    }

    private void selectYearTime() {
        calendar = Calendar.getInstance();
        calendar.setTime(DateUtil.strToDate("yyyy-MM", id_tv_time_month.getText().toString()));
        new YearPickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                calendar.set(Calendar.YEAR, year);
                id_tv_time_month.setText(DateUtil.clanderTodatetime(calendar, "yyyy"));

                inputYear = id_tv_time_month.getText().toString().trim();

                id_broken.setChecked(true);
                id_auto_car.setChecked(true);
                tag = id_broken;
                tag.setChecked(true);
                flag = id_auto_car;
                flag.setChecked(true);

                RequestMonthData(gczbs,inputYear,"A",1);

            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE)).show();
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
                        RequestMonthData(gczbs, inputYear, "A", 1);
                    }
                    if (tag.isChecked() && id_ck_car.isChecked()) {
                        ToastUtil.showToast("断面客车组合");
                        RequestMonthData(gczbs, inputYear, "A", 2);
                    }
                    if (tag.isChecked() && id_hc_car.isChecked()) {
                        ToastUtil.showToast("断面货车组合");
                        RequestMonthData(gczbs, inputYear, "A", 3);
                    }
                    if (tag.isChecked() && id_speed.isChecked()) {
                        ToastUtil.showToast("断面车速组合");
                        RequestMonthData(gczbs, inputYear, "A", 4);
                    }
                    if (tag.isChecked() && id_crowd.isChecked()) {
                        ToastUtil.showToast("断面拥挤度组合");
                        RequestMonthData(gczbs, inputYear, "A", 5);
                    }
                    break;

                case R.id.id_up:
                    tag = id_up;
                    tag.setChecked(true);
                    if (tag.isChecked() && id_auto_car.isChecked()) {
                        ToastUtil.showToast("上行机动车组合");
                        RequestMonthData(gczbs, inputYear, "S", 1);
                    }
                    if (tag.isChecked() && id_ck_car.isChecked()) {
                        ToastUtil.showToast("上行客车组合");
                        RequestMonthData(gczbs, inputYear, "S", 2);
                    }
                    if (tag.isChecked() && id_hc_car.isChecked()) {
                        ToastUtil.showToast("上行货车组合");
                        RequestMonthData(gczbs, inputYear, "S", 3);
                    }
                    if (tag.isChecked() && id_speed.isChecked()) {
                        ToastUtil.showToast("上行车速组合");
                        RequestMonthData(gczbs, inputYear, "S", 4);
                    }
                    if (tag.isChecked() && id_crowd.isChecked()) {
                        ToastUtil.showToast("断面拥挤度组合");
                        RequestMonthData(gczbs, inputYear, "S", 5);
                    }

                    break;
                case R.id.id_dowm:
                    tag = id_dowm;
                    tag.setChecked(true);
                    if (tag.isChecked() && id_auto_car.isChecked()) {
                        ToastUtil.showToast("下行机动车组合");
                        RequestMonthData(gczbs, inputYear, "X", 1);
                    }
                    if (tag.isChecked() && id_ck_car.isChecked()) {
                        ToastUtil.showToast("下行客车组合");
                        RequestMonthData(gczbs, inputYear, "X", 2);
                    }
                    if (tag.isChecked() && id_hc_car.isChecked()) {
                        ToastUtil.showToast("下行货车组合");
                        RequestMonthData(gczbs, inputYear, "X", 3);
                    }
                    if (tag.isChecked() && id_speed.isChecked()) {
                        ToastUtil.showToast("下行车速组合");
                        RequestMonthData(gczbs, inputYear, "X", 4);
                    }
                    if (tag.isChecked() && id_crowd.isChecked()) {
                        ToastUtil.showToast("断面拥挤度组合");
                        RequestMonthData(gczbs, inputYear, "X", 5);
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
                        RequestMonthData(gczbs, inputYear, "A", 1);
                    }
                    if (flag.isChecked() && id_up.isChecked()) {
                        ToastUtil.showToast("上行机动车组合");
                        RequestMonthData(gczbs, inputYear, "S", 1);
                    }
                    if (flag.isChecked() && id_dowm.isChecked()) {
                        ToastUtil.showToast("下行机动车组合");
                        RequestMonthData(gczbs, inputYear, "X", 1);
                    }
                    break;
                case R.id.id_ck_car:
                    flag = id_ck_car;
                    flag.setChecked(true);
                    if (flag.isChecked() && id_broken.isChecked()) {
                        ToastUtil.showToast("断面客车组合");
                        RequestMonthData(gczbs, inputYear, "A", 2);
                    }
                    if (flag.isChecked() && id_up.isChecked()) {
                        ToastUtil.showToast("上行客车组合");
                        RequestMonthData(gczbs, inputYear, "S", 2);
                    }
                    if (flag.isChecked() && id_dowm.isChecked()) {
                        ToastUtil.showToast("下行客车组合");
                        RequestMonthData(gczbs, inputYear, "X", 2);
                    }
                    break;
                case R.id.id_hc_car:
                    flag = id_hc_car;
                    flag.setChecked(true);
                    if (flag.isChecked() && id_broken.isChecked()) {
                        ToastUtil.showToast("断面货车组合");
                        RequestMonthData(gczbs, inputYear, "A", 3);
                    }
                    if (flag.isChecked() && id_up.isChecked()) {
                        ToastUtil.showToast("上行货车组合");
                        RequestMonthData(gczbs, inputYear, "S", 3);
                    }
                    if (flag.isChecked() && id_dowm.isChecked()) {
                        ToastUtil.showToast("下行货车组合");
                        RequestMonthData(gczbs, inputYear, "X", 3);
                    }
                    break;
                case R.id.id_speed:
                    flag = id_speed;
                    flag.setChecked(true);
                    if (flag.isChecked() && id_broken.isChecked()) {
                        ToastUtil.showToast("断面车速组合");
                        RequestMonthData(gczbs, inputYear, "A", 4);
                    }
                    if (flag.isChecked() && id_up.isChecked()) {
                        ToastUtil.showToast("上行车速组合");
                        RequestMonthData(gczbs, inputYear, "S", 4);
                    }
                    if (flag.isChecked() && id_dowm.isChecked()) {
                        ToastUtil.showToast("下行车速组合");

                        RequestMonthData(gczbs, inputYear, "X", 4);
                    }
                    break;
                case R.id.id_crowd:
                    flag = id_crowd;
                    flag.setChecked(true);
                    if (flag.isChecked() && id_broken.isChecked()) {
                        ToastUtil.showToast("断面拥挤度组合");
                        RequestMonthData(gczbs, inputYear, "A", 5);
                    }
                    if (flag.isChecked() && id_up.isChecked()) {
                        ToastUtil.showToast("上行拥挤度组合");
                        RequestMonthData(gczbs, inputYear, "S", 5);
                    }
                    if (flag.isChecked() && id_dowm.isChecked()) {
                        ToastUtil.showToast("下行拥挤度组合");

                        RequestMonthData(gczbs, inputYear, "X", 5);
                    }
                    break;


            }

        }
    }

    @Override
    public void onRefresh() {

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                id_broken.setChecked(true);
                id_auto_car.setChecked(true);
                tag = id_broken;
                tag.setChecked(true);
                flag = id_auto_car;
                flag.setChecked(true);

                RequestMonthData(gczbs,inputYear,"A",1);

                LogUtil.e("aaaaa", "我是刷新后的");
                onLoad();

            }
        },2000);

    }

    private void onLoad() {
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

