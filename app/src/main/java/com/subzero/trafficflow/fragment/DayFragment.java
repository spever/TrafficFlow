package com.subzero.trafficflow.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Handler;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
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
import com.subzero.trafficflow.bean.Day;
import com.subzero.trafficflow.response.DayResponse;
import com.subzero.trafficflow.widget.MonPickerDialog;
import com.subzero.trafficflow.widget.xlistview.XListView;
import com.zhy.http.okhttp.callback.StringCallback;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import okhttp3.Call;

/**
 * 每日页面
 * Created by hui on 2016/3/11.
 */
public class DayFragment extends BaseFragment implements View.OnClickListener, XListView.IXListViewListener, RadioGroup.OnCheckedChangeListener {

    private View v;
    private View header;
    private XListView xListView;

    private LineChart mLineChart;
    private Legend mLegend;

    private RadioGroup groupDown, groupUp;
    private RadioButton id_broken;
    private RadioButton id_up;
    private RadioButton id_dowm;
    private TextView id_tv_time_day;

    private RadioButton tag;
    private RadioButton flag;

    private ImageView imageView2;
    private RadioButton id_auto_car;
    private RadioButton id_ck_car;
    private RadioButton id_hc_car;
    private RadioButton id_speed;

    private Handler mHandler = new Handler();

    private int iYear;
    private int iMonth;
    private int iDay;
    private Calendar calendar;
    private String str;
    private String gczbs;
    private ArrayList<Day> list_day;
    private BarChart mBarChart;

    private ArrayList<BarEntry> entries;
    public BarDataSet dataset;
    private ArrayList<String> labels;

    private String timeStart, timeEnd;
    private RadioButton id_crowd;

    private DatePicker datepicker_start, datepicker_end;


    @Override
    protected View initView() {
        v = View.inflate(getActivity(), R.layout.fragment_label_item, null);

        initXlistView();
        initId();
        initListener();
        return v;
    }

    private void initListener() {
        imageView2.setOnClickListener(this);
        id_tv_time_day.setOnClickListener(this);
        groupUp.setOnCheckedChangeListener(this);
        groupDown.setOnCheckedChangeListener(this);

    }

    private void initXlistView() {
        xListView = (XListView) v.findViewById(R.id.id_xlist_label);
        header = View.inflate(getActivity(), R.layout.xlistview_header_day, null);
        xListView.addHeaderView(header);
        xListView.setXListViewListener(this);
        xListView.setPullRefreshEnable(true);
        xListView.setPullLoadEnable(false);
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
        id_crowd = (RadioButton) header.findViewById(R.id.id_crowd);

        imageView2 = (ImageView) header.findViewById(R.id.imageView2);

        id_tv_time_day = (TextView) header.findViewById(R.id.id_tv_calendar);

        mBarChart = (BarChart) header.findViewById(R.id.id_chart_bar);
        mLineChart = (LineChart) header.findViewById(R.id.id_chart_line);
    }

    @Override
    public void initHttp(String url) {
        url = UrlConstants.DAY;
        super.initHttp(url);
    }

    private void RequestDayData(String gczbs, String startDate, String endDate, String xsfx, final int type) {

        showProgressDialog("数据加载中......");
        base.addParams("startDate", startDate)
                .addParams("endDate", endDate)
                .addParams("xsfx", xsfx) //行驶方向
                .addParams("gczbs", gczbs)
                .addParams("cols", "GCRQ,JDC_DL,KC_DL,HC_DL,JDC_CS,YJD,YDDJ")
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

                LogUtil.e("res==day", res);
                DayResponse dayResponse = JsonUtil.parseJsonToBean(res, DayResponse.class);


                LogUtil.e("response", dayResponse + "");
                if (dayResponse != null && dayResponse.isSuccess()) {

                    list_day = dayResponse.getData();

                    if (type == 1) {
                        initBarCharttData();
                        mBarChart.setVisibility(View.VISIBLE);
                        mLineChart.setVisibility(View.GONE);
                    } else {
                        initLineChartData();
                        mBarChart.setVisibility(View.GONE);
                        mLineChart.setVisibility(View.VISIBLE);
                    }

                    xListView.setAdapter(new DayItemAdapter(list_day, getActivity(), type));


                }

            }
        });
    }


    @Override
    protected void initData() {

        gczbs = getActivity().getIntent().getStringExtra("gczbs");
        String[] time = id_tv_time_day.getText().toString().split("\\n");
        timeStart = time[0];
        timeEnd = time[1];

        calendar = Calendar.getInstance();
        iYear = calendar.get(Calendar.YEAR);
        iMonth = calendar.get(Calendar.MONTH);
        iDay = calendar.get(Calendar.DAY_OF_MONTH);

        RequestDayData(gczbs, timeStart, timeEnd, "A", 1);

    }


    private void initBarCharttData() {

        initEntriesData();
        initLableData();
        show();

    }

    private void initLableData() {
        labels = new ArrayList<>();
        for (int i = 0; i < list_day.size(); i++) {
            labels.add(DateUtil.DateTotime(list_day.get(i).getGCRQ(), "dd"));
        }

    }

    private void initEntriesData() {
        entries = new ArrayList<>();
        for (int i = 0; i < list_day.size(); i++) {
            entries.add(new BarEntry(list_day.get(i).getJDC_DL(), i));
        }
    }

    private void show() {

        dataset = new BarDataSet(entries, "# of Calls");
        dataset.setColors(ColorTemplate.COLORFUL_COLORS);
        BarData data = new BarData(labels, dataset);
        mBarChart.setScaleEnabled(true);
        mBarChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        mBarChart.getXAxis().setAvoidFirstLastClipping(true);
        mBarChart.getXAxis().setTextColor(Color.WHITE);

        mBarChart.setDescription(" ");
        mBarChart.getAxisRight().setEnabled(false);
        mBarChart.getAxisRight().setTextColor(Color.WHITE);
        ;
        mBarChart.setData(data);

        mBarChart.animateX(4000);
        mBarChart.animateY(2000);

    }

    private void initLineChartData() {


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


        for (int i = 0; i < list_day.size(); i++) {
            xVals.add(DateUtil.DateTotime(list_day.get(i).getGCRQ(), "dd"));
            yVals.add(new Entry(list_day.get(i).getJDC_DL(), i));
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

        LineData data = new LineData(xVals, set1);
        mLineChart.setData(data);

        mLineChart.animateX(5000);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageView2:
            case R.id.id_tv_calendar:
//                selectTime();
                selectStartEndTime();
                break;
        }
    }

    private void selectStartEndTime() {
        calendar.setTime(DateUtil.strToDate("yyyy-MM", id_tv_time_day.getText().toString()));
        getActivity().setTheme(android.R.style.Theme_Holo_Light);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = View.inflate(getActivity(), R.layout.common_datetime, null);
        datepicker_start = (DatePicker) view.findViewById(R.id.datepicker_start);
        datepicker_end = (DatePicker) view.findViewById(R.id.datepicker_end);

        builder.setView(view);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        datepicker_start.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), null);
        datepicker_end.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), null);

        builder.setTitle("选取时间：");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                StringBuffer sb = new StringBuffer();
                sb.append(String.format("%d-%02d-%02d", datepicker_start.getYear(), datepicker_start.getMonth() + 1, datepicker_start.getDayOfMonth()));
                sb.append("\n");
                sb.append(String.format("%d-%02d-%02d", datepicker_end.getYear(), datepicker_end.getMonth() + 1, datepicker_end.getDayOfMonth()));

                id_tv_time_day.setText(sb);

                String[] times = id_tv_time_day.getText().toString().split("\\n");

                timeStart = times[0];
                timeEnd = times[1];

                id_broken.setChecked(true);
                id_auto_car.setChecked(true);
                tag = id_broken;
                tag.setChecked(true);
                flag = id_auto_car;
                flag.setChecked(true);

                RequestDayData(gczbs, timeStart, timeEnd, "A", 1);


                LogUtil.e("time", timeStart + "        ===                " + timeEnd);
                dialog.cancel();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.create().show();
    }

//    private void selectTime() {
//        calendar.setTime(DateUtil.strToDate("yyyy-MM", id_tv_time_day.getText().toString()));
//        new MonPickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//
//                calendar.set(Calendar.YEAR, year);
//                calendar.set(Calendar.MONTH, monthOfYear);
//                id_tv_time_day.setText(DateUtil.clanderTodatetime(calendar, "yyyy-MM"));
//
//            }
//        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE)).show();
//
//    }

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

                RequestDayData(gczbs, timeStart, timeEnd, "A", 1);
                LogUtil.e("aaaaa", "我是刷新后的");
                onLoad();
            }
        }, 2000);

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

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        if (group == groupUp) {
            switch (checkedId) {
                case R.id.id_broken:
                    tag = id_broken;
                    tag.setChecked(true);
                    if (tag.isChecked() && id_auto_car.isChecked()) {
                        ToastUtil.showToast("断面机动车组合");
                        RequestDayData(gczbs, timeStart, timeEnd, "A", 1);
                    }
                    if (tag.isChecked() && id_ck_car.isChecked()) {
                        ToastUtil.showToast("断面客车组合");
                        RequestDayData(gczbs, timeStart, timeEnd, "A", 2);
                    }
                    if (tag.isChecked() && id_hc_car.isChecked()) {
                        ToastUtil.showToast("断面货车组合");
                        RequestDayData(gczbs, timeStart, timeEnd, "A", 3);
                    }
                    if (tag.isChecked() && id_speed.isChecked()) {
                        ToastUtil.showToast("断面车速组合");
                        RequestDayData(gczbs, timeStart, timeEnd, "A", 4);
                    }
                    if (tag.isChecked() && id_crowd.isChecked()) {
                        ToastUtil.showToast("断面拥挤度组合");
                        RequestDayData(gczbs, timeStart, timeEnd, "A", 5);
                    }
                    break;

                case R.id.id_up:
                    tag = id_up;
                    tag.setChecked(true);
                    if (tag.isChecked() && id_auto_car.isChecked()) {
                        ToastUtil.showToast("上行机动车组合");
                        RequestDayData(gczbs, timeStart, timeEnd, "S", 1);
                    }
                    if (tag.isChecked() && id_ck_car.isChecked()) {
                        ToastUtil.showToast("上行客车组合");
                        RequestDayData(gczbs, timeStart, timeEnd, "S", 2);
                    }
                    if (tag.isChecked() && id_hc_car.isChecked()) {
                        ToastUtil.showToast("上行货车组合");
                        RequestDayData(gczbs, timeStart, timeEnd, "S", 3);
                    }
                    if (tag.isChecked() && id_speed.isChecked()) {
                        ToastUtil.showToast("上行车速组合");
                        RequestDayData(gczbs, timeStart, timeEnd, "S", 4);
                    }
                    if (tag.isChecked() && id_crowd.isChecked()) {
                        ToastUtil.showToast("断面拥挤度组合");
                        RequestDayData(gczbs, timeStart, timeEnd, "S", 5);
                    }

                    break;
                case R.id.id_dowm:
                    tag = id_dowm;
                    tag.setChecked(true);
                    if (tag.isChecked() && id_auto_car.isChecked()) {
                        ToastUtil.showToast("下行机动车组合");
                        RequestDayData(gczbs, timeStart, timeEnd, "X", 1);
                    }
                    if (tag.isChecked() && id_ck_car.isChecked()) {
                        ToastUtil.showToast("下行客车组合");
                        RequestDayData(gczbs, timeStart, timeEnd, "X", 2);
                    }
                    if (tag.isChecked() && id_hc_car.isChecked()) {
                        ToastUtil.showToast("下行货车组合");
                        RequestDayData(gczbs, timeStart, timeEnd, "X", 3);
                    }
                    if (tag.isChecked() && id_speed.isChecked()) {
                        ToastUtil.showToast("下行车速组合");
                        RequestDayData(gczbs, timeStart, timeEnd, "X", 4);
                    }
                    if (tag.isChecked() && id_crowd.isChecked()) {
                        ToastUtil.showToast("断面拥挤度组合");
                        RequestDayData(gczbs, timeStart, timeEnd, "X", 5);
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
                        RequestDayData(gczbs, timeStart, timeEnd, "A", 1);
                    }
                    if (flag.isChecked() && id_up.isChecked()) {
                        ToastUtil.showToast("上行机动车组合");
                        RequestDayData(gczbs, timeStart, timeEnd, "S", 1);
                    }
                    if (flag.isChecked() && id_dowm.isChecked()) {
                        ToastUtil.showToast("下行机动车组合");
                        RequestDayData(gczbs, timeStart, timeEnd, "X", 1);
                    }
                    break;
                case R.id.id_ck_car:
                    flag = id_ck_car;
                    flag.setChecked(true);
                    if (flag.isChecked() && id_broken.isChecked()) {
                        ToastUtil.showToast("断面客车组合");
                        RequestDayData(gczbs, timeStart, timeEnd, "A", 2);
                    }
                    if (flag.isChecked() && id_up.isChecked()) {
                        ToastUtil.showToast("上行客车组合");
                        RequestDayData(gczbs, timeStart, timeEnd, "S", 2);
                    }
                    if (flag.isChecked() && id_dowm.isChecked()) {
                        ToastUtil.showToast("下行客车组合");
                        RequestDayData(gczbs, timeStart, timeEnd, "X", 2);
                    }
                    break;
                case R.id.id_hc_car:
                    flag = id_hc_car;
                    flag.setChecked(true);
                    if (flag.isChecked() && id_broken.isChecked()) {
                        ToastUtil.showToast("断面货车组合");
                        RequestDayData(gczbs, timeStart, timeEnd, "A", 3);
                    }
                    if (flag.isChecked() && id_up.isChecked()) {
                        ToastUtil.showToast("上行货车组合");
                        RequestDayData(gczbs, timeStart, timeEnd, "S", 3);
                    }
                    if (flag.isChecked() && id_dowm.isChecked()) {
                        ToastUtil.showToast("下行货车组合");
                        RequestDayData(gczbs, timeStart, timeEnd, "X", 3);
                    }
                    break;
                case R.id.id_speed:
                    flag = id_speed;
                    flag.setChecked(true);
                    if (flag.isChecked() && id_broken.isChecked()) {
                        ToastUtil.showToast("断面车速组合");
                        RequestDayData(gczbs, timeStart, timeEnd, "A", 4);
                    }
                    if (flag.isChecked() && id_up.isChecked()) {
                        ToastUtil.showToast("上行车速组合");
                        RequestDayData(gczbs, timeStart, timeEnd, "S", 4);
                    }
                    if (flag.isChecked() && id_dowm.isChecked()) {
                        ToastUtil.showToast("下行车速组合");

                        RequestDayData(gczbs, timeStart, timeEnd, "X", 4);
                    }
                    break;
                case R.id.id_crowd:
                    flag = id_crowd;
                    flag.setChecked(true);
                    if (flag.isChecked() && id_broken.isChecked()) {
                        ToastUtil.showToast("断面拥挤度组合");
                        RequestDayData(gczbs, timeStart, timeEnd, "A", 5);
                    }
                    if (flag.isChecked() && id_up.isChecked()) {
                        ToastUtil.showToast("上行拥挤度组合");
                        RequestDayData(gczbs, timeStart, timeEnd, "S", 5);
                    }
                    if (flag.isChecked() && id_dowm.isChecked()) {
                        ToastUtil.showToast("下行拥挤度组合");

                        RequestDayData(gczbs, timeStart, timeEnd, "X", 5);
                    }
                    break;


            }

        }
    }
}
