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
import com.subzero.trafficflow.Utils.StringUtils;
import com.subzero.trafficflow.Utils.ToastUtil;
import com.subzero.trafficflow.adapter.MinutesItemAdapter;
import com.subzero.trafficflow.bean.Minutes;
import com.subzero.trafficflow.response.MinutesResponse;
import com.subzero.trafficflow.widget.xlistview.XListView;
import com.zhy.http.okhttp.callback.StringCallback;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import okhttp3.Call;

/**
 * 5分钟页面
 * Created by hui on 2016/3/11.
 */
public class MinutesFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener, View.OnClickListener, XListView.IXListViewListener {

    private View v;
    private XListView xListView;
    private View header;
    private LineChart mLineChart;
    private Legend mLegend;

    private RadioGroup groupDown, groupUp;
    private RadioButton id_broken;
    private RadioButton id_up;
    private RadioButton id_dowm;
    private TextView id_tv_time_minutes;
    private ImageView imageView2;
    private RadioButton id_auto_car;
    private RadioButton id_ck_car;
    private RadioButton id_hc_car;
    private RadioButton id_speed;

    private RadioButton tag;
    private RadioButton flag;

    private int iYear;
    private int iMonth;
    private int iDay;
    private Calendar calendar;
    private String str;
    private String gczbs;

    private Handler mHandler = new Handler();
    private ArrayList<Minutes> list_minutes;

    private LineData data;
    private String time;


    @Override
    protected View initView() {
        v = View.inflate(getActivity(), R.layout.fragment_label_item, null);
        initXlistView();
        initId();
        time = id_tv_time_minutes.getText().toString().trim();
        initListener();
        return v;
    }

    private void initListener() {
        groupUp.setOnCheckedChangeListener(this);
        groupDown.setOnCheckedChangeListener(this);
        id_tv_time_minutes.setOnClickListener(this);
        imageView2.setOnClickListener(this);
    }

    private void initXlistView() {
        xListView = (XListView) v.findViewById(R.id.id_xlist_label);
        header = View.inflate(getActivity(), R.layout.xlistview_header_minutes, null);
        xListView.addHeaderView(header);
        xListView.setXListViewListener(this);
        xListView.setPullLoadEnable(false);
        xListView.setPullRefreshEnable(true);

    }


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

                RequestMinutesData(gczbs, time, "A", 1);
                LogUtil.e("aaaaa", "我是刷新后的");
                onLoad();
            }
        }, 2000);

    }

    private void onLoad() {
        xListView.stopRefresh();// 停止下拉刷新
        // 获取当前时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String data = sdf.format(new Date());
        xListView.setRefreshTime(data);
    }

    @Override
    public void onLoadMore() {

    }


    @Override
    public void initHttp(String url) {
        url = UrlConstants.MINUTES;
        super.initHttp(url);
    }

//    XSFX	A	断面
//    XSFX	S	上行
//    XSFX	X	下行

    private void RequestMinutesData(String gczbs, String date, String xsfx, final int type) {

        showProgressDialog("数据加载中......");
        base.addParams("gcrq", date)
                .addParams("xsfx", xsfx) //行驶方向
                .addParams("gczbs", gczbs)
                .addParams("cols", "HOUR,MINUTE,SJXH,JDC_DL,KC_DL,HC_DL,JDC_CS,YDDJ")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {

                LogUtil.e("error", "error");
                e.printStackTrace();

            }

            @Override
            public void onResponse(String response) {
                dismissProgressDialog();
                LogUtil.e("response=======================", response);
                String res = JsonUtil.getFieldValue(response, "result");

                LogUtil.e("res==", res);
                MinutesResponse minutesResponse = JsonUtil.parseJsonToBean(res, MinutesResponse.class);


                if (minutesResponse != null && minutesResponse.isSuccess()) {

                    list_minutes = minutesResponse.getData();

                        initChartData();

                        xListView.setAdapter(new MinutesItemAdapter(list_minutes, getActivity(), type));


                }

            }
        });
    }

    @Override
    protected void initData() {

        gczbs = getActivity().getIntent().getStringExtra("gczbs");
        LogUtil.e("ssssssssssssssssss", gczbs);

        calendar = Calendar.getInstance();
        iYear = calendar.get(Calendar.YEAR);
        iMonth = calendar.get(Calendar.MONTH);
        iDay = calendar.get(Calendar.DAY_OF_MONTH);

        id_broken.setChecked(true);
        id_auto_car.setChecked(true);
        tag = id_broken;
        tag.setChecked(true);
        flag = id_auto_car;
        flag.setChecked(true);

        RequestMinutesData(gczbs, time, "A", 1);

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

        id_tv_time_minutes = (TextView) header.findViewById(R.id.id_tv_time_minutes);
        imageView2 = (ImageView) header.findViewById(R.id.imageView2);

    }

    private void initChartData() {
        mLineChart = (LineChart) header.findViewById(R.id.id_chart_line);
        mLineChart.setDescription(" ");
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
        xAxis.setAvoidFirstLastClipping(false); //设置x轴起点和终点label不超出屏幕
        xAxis.setDrawAxisLine(false);           //设置显示x轴
        xAxis.setSpaceBetweenLabels(0); // 设置x轴label不间隔

        YAxis leftAxis = mLineChart.getAxisLeft();  //得到图表的左侧Y轴实例
        leftAxis.setTextColor(Color.WHITE);
//        leftAxis.setAxisMaxValue(5f); // 设置Y轴最大值
//        leftAxis.setAxisMinValue(1f);// 设置Y轴最小值。
        leftAxis.setStartAtZero(true);   //设置图表起点从0开始
        leftAxis.enableGridDashedLine(10f, 5f, 0f); //设置横向表格为虚线

        leftAxis.setDrawLimitLinesBehindData(true);
        mLineChart.setHighlightPerDragEnabled(false); //设置可拖拽的value线
        mLineChart.getAxisRight().setEnabled(false);

        setData();
        mLineChart.animateX(5000);
        mLineChart.invalidate();

    }

    //count 表示坐标点个数，range表示等下y值生成的范围
    private void setData() {
        ArrayList<String> xVals = new ArrayList<String>();
        ArrayList<Entry> yVals = new ArrayList<Entry>();

        LogUtil.e("list-----",list_minutes.size()  +"==");
        for (int i = 0; i < list_minutes.size(); i++) {
            yVals.add(new Entry((float) list_minutes.get(i).getJDC_CS(), i));
            int urlTime = list_minutes.get(i).getSJXH();
            String time = "";
            switch (urlTime) {
                case 1:
                    time = "00:00";
                    xVals.add(time);
                    break;
                case 2:
                    time = "00:05";
                    xVals.add(time);
                    break;
                case 3:
                    time = "00:10";
                    xVals.add(time);
                    break;
                case 4:
                    time = "00:15";
                    xVals.add(time);
                    break;
                case 5:
                    time = "00:20";
                    xVals.add(time);
                    break;
                case 6:
                    time = "00:25";
                    xVals.add(time);
                    break;
                case 7:
                    time = "00:30";
                    xVals.add(time);
                    break;
                case 8:
                    time = "00:35";
                    xVals.add(time);
                    break;
                case 9:
                    time = "00:40";
                    xVals.add(time);
                    break;
                case 10:
                    time = "00:45";
                    xVals.add(time);
                    break;
                case 11:
                    time = "00:50";
                    xVals.add(time);
                    break;
                case 12:
                    time = "00:55";
                    xVals.add(time);
                    break;
                case 13:
                    time = "01:00";
                    xVals.add(time);
                    break;
                case 14:
                    time = "01:05";
                    xVals.add(time);
                    break;
                case 15:
                    time = "01:10";
                    xVals.add(time);
                    break;
                case 16:
                    time = "01:15";
                    xVals.add(time);
                    break;
                case 17:
                    time = "01:20";
                    xVals.add(time);
                    break;
                case 18:
                    time = "01:25";
                    xVals.add(time);
                    break;
                case 19:
                    time = "01:30";
                    xVals.add(time);
                    break;
                case 20:
                    time = "01:35";
                    xVals.add(time);
                    break;
                case 21:
                    time = "01:40";
                    xVals.add(time);
                    break;
                case 22:
                    time = "01:45";
                    xVals.add(time);
                    break;
                case 23:
                    time = "01:50";
                    xVals.add(time);
                    break;
                case 24:
                    time = "01:55";
                    xVals.add(time);
                    break;
                case 25:
                    time = "02:00";
                    xVals.add(time);
                    break;
                case 26:
                    time = "02:05";
                    xVals.add(time);
                    break;
                case 27:
                    time = "02:10";
                    xVals.add(time);
                    break;
                case 28:
                    time = "02:15";
                    xVals.add(time);
                    break;
                case 29:
                    time = "02:20";
                    xVals.add(time);
                    break;
                case 30:
                    time = "02:25";
                    xVals.add(time);
                    break;
                case 31:
                    time = "02:30";
                    xVals.add(time);
                    break;
                case 32:
                    time = "02:35";
                    xVals.add(time);
                    break;
                case 33:
                    time = "02:40";
                    xVals.add(time);
                    break;
                case 34:
                    time = "02:45";
                    xVals.add(time);
                    break;
                case 35:
                    time = "02:50";
                    xVals.add(time);
                    break;
                case 36:
                    time = "02:55";
                    xVals.add(time);
                    break;
                case 37:
                    time = "03:00";
                    xVals.add(time);
                    break;
                case 38:
                    time = "03:05";
                    xVals.add(time);
                    break;
                case 39:
                    time = "03:10";
                    xVals.add(time);
                    break;
                case 40:
                    time = "03:15";
                    xVals.add(time);
                    break;
                case 41:
                    time = "03:20";
                    xVals.add(time);
                    break;
                case 42:
                    time = "03:25";
                    xVals.add(time);
                    break;
                case 43:
                    time = "03:30";
                    xVals.add(time);
                    break;
                case 44:
                    time = "03:35";
                    xVals.add(time);
                    break;
                case 45:
                    time = "03:40";
                    xVals.add(time);
                    break;
                case 46:
                    time = "03:45";
                    xVals.add(time);
                    break;
                case 47:
                    time = "03:50";
                    xVals.add(time);
                    break;
                case 48:
                    time = "03:55";
                    xVals.add(time);
                    break;
                case 49:
                    time = "04:00";
                    xVals.add(time);
                    break;
                case 50:
                    time = "04:05";
                    xVals.add(time);
                    break;
                case 51:
                    time = "04:10";
                    xVals.add(time);
                    break;
                case 52:
                    time = "04:15";
                    xVals.add(time);
                    break;
                case 53:
                    time = "04:20";
                    xVals.add(time);
                    break;
                case 54:
                    time = "04:25";
                    xVals.add(time);
                    break;
                case 55:
                    time = "04:30";
                    xVals.add(time);
                    break;
                case 56:
                    time = "04:35";
                    xVals.add(time);
                    break;
                case 57:
                    time = "04:40";
                    xVals.add(time);
                    break;
                case 58:
                    time = "04:45";
                    xVals.add(time);
                    break;
                case 59:
                    time = "04:50";
                    xVals.add(time);
                    break;
                case 60:
                    time = "04:55";
                    xVals.add(time);
                    break;
                case 61:
                    time = "05:00";
                    xVals.add(time);
                    break;
                case 62:
                    time = "05:05";
                    xVals.add(time);
                    break;
                case 63:
                    time = "05:10";
                    xVals.add(time);
                    break;
                case 64:
                    time = "05:15";
                    xVals.add(time);
                    break;
                case 65:
                    time = "05:20";
                    xVals.add(time);
                    break;
                case 66:
                    time = "05:25";
                    xVals.add(time);
                    break;
                case 67:
                    time = "05:30";
                    xVals.add(time);
                    break;
                case 68:
                    time = "05:35";
                    xVals.add(time);
                    break;
                case 69:
                    time = "05:40";
                    xVals.add(time);
                    break;
                case 70:
                    time = "05:45";
                    xVals.add(time);
                    break;
                case 71:
                    time = "05:50";
                    xVals.add(time);
                    break;
                case 72:
                    time = "05:55";
                    xVals.add(time);
                    break;
                case 73:
                    time = "06:00";
                    xVals.add(time);
                    break;
                case 74:
                    time = "06:05";
                    xVals.add(time);
                    break;
                case 75:
                    time = "06:10";
                    xVals.add(time);
                    break;
                case 76:
                    time = "06:15";
                    xVals.add(time);
                    break;
                case 77:
                    time = "06:20";
                    xVals.add(time);
                    break;
                case 78:
                    time = "06:25";
                    xVals.add(time);
                    break;
                case 79:
                    time = "06:30";
                    xVals.add(time);
                    break;
                case 80:
                    time = "06:35";
                    xVals.add(time);
                    break;
                case 81:
                    time = "06:40";
                    xVals.add(time);
                    break;
                case 82:
                    time = "06:45";
                    xVals.add(time);
                    break;
                case 83:
                    time = "06:50";
                    xVals.add(time);
                    break;
                case 84:
                    time = "06:55";
                    xVals.add(time);
                    break;
                case 85:
                    time = "07:00";
                    xVals.add(time);
                    break;
                case 86:
                    time = "07:05";
                    xVals.add(time);
                    break;
                case 87:
                    time = "07:10";
                    xVals.add(time);
                    break;
                case 88:
                    time = "07:15";
                    xVals.add(time);
                    break;
                case 89:
                    time = "07:20";
                    xVals.add(time);
                    break;
                case 90:
                    time = "07:25";
                    xVals.add(time);
                    break;
                case 91:
                    time = "07:30";
                    xVals.add(time);
                    break;
                case 92:
                    time = "07:35";
                    xVals.add(time);
                    break;
                case 93:
                    time = "07:40";
                    xVals.add(time);
                    break;
                case 94:
                    time = "07:45";
                    xVals.add(time);
                    break;
                case 95:
                    time = "07:50";
                    xVals.add(time);
                    break;
                case 96:
                    time = "07:55";
                    xVals.add(time);
                    break;
                case 97:
                    time = "08:00";
                    xVals.add(time);
                    break;
                case 98:
                    time = "08:05";
                    xVals.add(time);
                    break;
                case 99:
                    time = "08:10";
                    xVals.add(time);
                    break;
                case 100:
                    time = "08:15";
                    xVals.add(time);
                    break;
                case 101:
                    time = "08:20";
                    xVals.add(time);
                    break;
                case 102:
                    time = "08:25";
                    xVals.add(time);
                    break;
                case 103:
                    time = "08:30";
                    xVals.add(time);
                    break;
                case 104:
                    time = "08:35";
                    xVals.add(time);
                    break;
                case 105:
                    time = "08:40";
                    xVals.add(time);
                    break;
                case 106:
                    time = "08:45";
                    xVals.add(time);
                    break;
                case 107:
                    time = "08:50";
                    xVals.add(time);
                    break;
                case 108:
                    time = "08:55";
                    xVals.add(time);
                    break;
                case 109:
                    time = "09:00";
                    xVals.add(time);
                    break;
                case 110:
                    time = "09:05";
                    xVals.add(time);
                    break;
                case 111:
                    time = "09:10";
                    xVals.add(time);
                    break;
                case 112:
                    time = "09:15";
                    xVals.add(time);
                    break;
                case 113:
                    time = "09:20";
                    xVals.add(time);
                    break;
                case 114:
                    time = "09:25";
                    xVals.add(time);
                    break;
                case 115:
                    time = "09:30";
                    xVals.add(time);
                    break;
                case 116:
                    time = "09:35";
                    xVals.add(time);
                    break;
                case 117:
                    time = "09:40";
                    xVals.add(time);
                    break;
                case 118:
                    time = "09:45";
                    xVals.add(time);
                    break;
                case 119:
                    time = "09:50";
                    xVals.add(time);
                    break;
                case 120:
                    time = "09:55";
                    xVals.add(time);
                    break;
                case 121:
                    time = "10:00";
                    xVals.add(time);
                    break;
                case 122:
                    time = "10:05";
                    xVals.add(time);
                    break;
                case 123:
                    time = "10:10";
                    xVals.add(time);
                    break;
                case 124:
                    time = "10:15";
                    xVals.add(time);
                    break;
                case 125:
                    time = "10:20";
                    xVals.add(time);
                    break;
                case 126:
                    time = "10:25";
                    xVals.add(time);
                    break;
                case 127:
                    time = "10:30";
                    xVals.add(time);
                    break;
                case 128:
                    time = "10:35";
                    xVals.add(time);
                    break;
                case 129:
                    time = "10:40";
                    xVals.add(time);
                    break;
                case 130:
                    time = "10:45";
                    xVals.add(time);
                    break;
                case 131:
                    time = "10:50";
                    xVals.add(time);
                    break;
                case 132:
                    time = "10:55";
                    xVals.add(time);
                    break;
                case 133:
                    time = "11:00";
                    xVals.add(time);
                    break;
                case 134:
                    time = "11:05";
                    xVals.add(time);
                    break;
                case 135:
                    time = "11:10";
                    xVals.add(time);
                    break;
                case 136:
                    time = "11:15";
                    xVals.add(time);
                    break;
                case 137:
                    time = "11:20";
                    xVals.add(time);
                    break;
                case 138:
                    time = "11:25";
                    xVals.add(time);
                    break;
                case 139:
                    time = "11:30";
                    xVals.add(time);
                    break;
                case 140:
                    time = "11:35";
                    xVals.add(time);
                    break;
                case 141:
                    time = "11:40";
                    xVals.add(time);
                    break;
                case 142:
                    time = "11:45";
                    xVals.add(time);
                    break;
                case 143:
                    time = "11:50";
                    xVals.add(time);
                    break;
                case 144:
                    time = "11:55";
                    xVals.add(time);
                    break;
                case 145:
                    time = "12:00";
                    xVals.add(time);
                    break;
                case 146:
                    time = "12:05";
                    xVals.add(time);
                    break;
                case 147:
                    time = "12:10";
                    xVals.add(time);
                    break;
                case 148:
                    time = "12:15";
                    xVals.add(time);
                    break;
                case 149:
                    time = "12:20";
                    xVals.add(time);
                    break;
                case 150:
                    time = "12:25";
                    xVals.add(time);
                    break;
                case 151:
                    time = "12:30";
                    xVals.add(time);
                    break;
                case 152:
                    time = "12:35";
                    xVals.add(time);
                    break;
                case 153:
                    time = "12:40";
                    xVals.add(time);
                    break;
                case 154:
                    time = "12:45";
                    xVals.add(time);
                    break;
                case 155:
                    time = "12:50";
                    xVals.add(time);
                    break;
                case 156:
                    time = "12:55";
                    xVals.add(time);
                    break;
                case 157:
                    time = "13:00";
                    xVals.add(time);
                    break;
                case 158:
                    time = "13:05";
                    xVals.add(time);
                    break;
                case 159:
                    time = "13:10";
                    xVals.add(time);
                    break;
                case 160:
                    time = "13:15";
                    xVals.add(time);
                    break;
                case 161:
                    time = "13:20";
                    xVals.add(time);
                    break;
                case 162:
                    time = "13:25";
                    xVals.add(time);
                    break;
                case 163:
                    time = "13:30";
                    xVals.add(time);
                    break;
                case 164:
                    time = "13:35";
                    xVals.add(time);
                    break;
                case 165:
                    time = "13:40";
                    xVals.add(time);
                    break;
                case 166:
                    time = "13:45";
                    xVals.add(time);
                    break;
                case 167:
                    time = "13:50";
                    xVals.add(time);
                    break;
                case 168:
                    time = "13:55";
                    xVals.add(time);
                    break;
                case 169:
                    time = "14:00";
                    xVals.add(time);
                    break;
                case 170:
                    time = "14:05";
                    xVals.add(time);
                    break;
                case 171:
                    time = "14:10";
                    xVals.add(time);
                    break;
                case 172:
                    time = "14:15";
                    xVals.add(time);
                    break;
                case 173:
                    time = "14:20";
                    xVals.add(time);
                    break;
                case 174:
                    time = "14:25";
                    xVals.add(time);
                    break;
                case 175:
                    time = "14:30";
                    xVals.add(time);
                    break;
                case 176:
                    time = "14:35";
                    xVals.add(time);
                    break;
                case 177:
                    time = "14:40";
                    xVals.add(time);
                    break;
                case 178:
                    time = "14:45";
                    xVals.add(time);
                    break;
                case 179:
                    time = "14:50";
                    xVals.add(time);
                    break;
                case 180:
                    time = "14:55";
                    xVals.add(time);
                    break;
                case 181:
                    time = "15:00";
                    xVals.add(time);
                    break;
                case 182:
                    time = "15:05";
                    xVals.add(time);
                    break;
                case 183:
                    time = "15:10";
                    xVals.add(time);
                    break;
                case 184:
                    time = "15:15";
                    xVals.add(time);
                    break;
                case 185:
                    time = "15:20";
                    xVals.add(time);
                    break;
                case 186:
                    time = "15:25";
                    xVals.add(time);
                    break;
                case 187:
                    time = "15:30";
                    xVals.add(time);
                    break;
                case 188:
                    time = "15:35";
                    xVals.add(time);
                    break;
                case 189:
                    time = "15:40";
                    xVals.add(time);
                    break;
                case 190:
                    time = "15:45";
                    xVals.add(time);
                    break;
                case 191:
                    time = "15:50";
                    xVals.add(time);
                    break;
                case 192:
                    time = "15:55";
                    xVals.add(time);
                    break;
                case 193:
                    time = "16:00";
                    xVals.add(time);
                    break;
                case 194:
                    time = "16:05";
                    xVals.add(time);
                    break;
                case 195:
                    time = "16:10";
                    xVals.add(time);
                    break;
                case 196:
                    time = "16:15";
                    xVals.add(time);
                    break;
                case 197:
                    time = "16:20";
                    xVals.add(time);
                    break;
                case 198:
                    time = "16:25";
                    xVals.add(time);
                    break;
                case 199:
                    time = "16:30";
                    xVals.add(time);
                    break;
                case 200:
                    time = "16:35";
                    xVals.add(time);
                    break;
                case 201:
                    time = "16:40";
                    xVals.add(time);
                    break;
                case 202:
                    time = "16:45";
                    xVals.add(time);
                    break;
                case 203:
                    time = "16:50";
                    xVals.add(time);
                    break;
                case 204:
                    time = "16:55";
                    xVals.add(time);
                    break;
                case 205:
                    time = "17:00";
                    xVals.add(time);
                    break;
                case 206:
                    time = "17:05";
                    xVals.add(time);
                    break;
                case 207:
                    time = "17:10";
                    xVals.add(time);
                    break;
                case 208:
                    time = "17:15";
                    xVals.add(time);
                    break;
                case 209:
                    time = "17:20";
                    xVals.add(time);
                    break;
                case 210:
                    time = "17:25";
                    xVals.add(time);
                    break;
                case 211:
                    time = "17:30";
                    xVals.add(time);
                    break;
                case 212:
                    time = "17:35";
                    xVals.add(time);
                    break;
                case 213:
                    time = "17:40";
                    xVals.add(time);
                    break;
                case 214:
                    time = "17:45";
                    xVals.add(time);
                    break;
                case 215:
                    time = "17:50";
                    xVals.add(time);
                    break;
                case 216:
                    time = "17:55";
                    xVals.add(time);
                    break;
                case 217:
                    time = "18:00";
                    xVals.add(time);
                    break;
                case 218:
                    time = "18:05";
                    xVals.add(time);
                    break;
                case 219:
                    time = "18:10";
                    xVals.add(time);
                    break;
                case 220:
                    time = "18:15";
                    xVals.add(time);
                    break;
                case 221:
                    time = "18:20";
                    xVals.add(time);
                    break;
                case 222:
                    time = "18:25";
                    xVals.add(time);
                    break;
                case 223:
                    time = "18:30";
                    xVals.add(time);
                    break;
                case 224:
                    time = "18:35";
                    xVals.add(time);
                    break;
                case 225:
                    time = "18:40";
                    xVals.add(time);
                    break;
                case 226:
                    time = "18:45";
                    xVals.add(time);
                    break;
                case 227:
                    time = "18:50";
                    xVals.add(time);
                    break;
                case 228:
                    time = "18:55";
                    xVals.add(time);
                    break;
                case 229:
                    time = "19:00";
                    xVals.add(time);
                    break;
                case 230:
                    time = "19:05";
                    xVals.add(time);
                    break;
                case 231:
                    time = "19:10";
                    xVals.add(time);
                    break;
                case 232:
                    time = "19:15";
                    xVals.add(time);
                    break;
                case 233:
                    time = "19:20";
                    xVals.add(time);
                    break;
                case 234:
                    time = "19:25";
                    xVals.add(time);
                    break;
                case 235:
                    time = "19:30";
                    xVals.add(time);
                    break;
                case 236:
                    time = "19:35";
                    xVals.add(time);
                    break;
                case 237:
                    time = "19:40";
                    xVals.add(time);
                    break;
                case 238:
                    time = "19:45";
                    xVals.add(time);
                    break;
                case 239:
                    time = "19:50";
                    xVals.add(time);
                    break;
                case 240:
                    time = "19:55";
                    xVals.add(time);
                    break;
                case 241:
                    time = "20:00";
                    xVals.add(time);
                    break;
                case 242:
                    time = "20:05";
                    xVals.add(time);
                    break;
                case 243:
                    time = "20:10";
                    xVals.add(time);
                    break;
                case 244:
                    time = "20:15";
                    xVals.add(time);
                    break;
                case 245:
                    time = "20:20";
                    xVals.add(time);
                    break;
                case 246:
                    time = "20:25";
                    xVals.add(time);
                    break;
                case 247:
                    time = "20:30";
                    xVals.add(time);
                    break;
                case 248:
                    time = "20:35";
                    xVals.add(time);
                    break;
                case 249:
                    time = "20:40";
                    xVals.add(time);
                    break;
                case 250:
                    time = "20:45";
                    xVals.add(time);
                    break;
                case 251:
                    time = "20:50";
                    xVals.add(time);
                    break;
                case 252:
                    time = "20:55";
                    xVals.add(time);
                    break;
                case 253:
                    time = "21:00";
                    xVals.add(time);
                    break;
                case 254:
                    time = "21:05";
                    xVals.add(time);
                    break;
                case 255:
                    time = "21:10";
                    xVals.add(time);
                    break;
                case 256:
                    time = "21:15";
                    xVals.add(time);
                    break;
                case 257:
                    time = "21:20";
                    xVals.add(time);
                    break;
                case 258:
                    time = "21:25";
                    xVals.add(time);
                    break;
                case 259:
                    time = "21:30";
                    xVals.add(time);
                    break;
                case 260:
                    time = "21:35";
                    xVals.add(time);
                    break;
                case 261:
                    time = "21:40";
                    xVals.add(time);
                    break;
                case 262:
                    time = "21:45";
                    xVals.add(time);
                    break;
                case 263:
                    time = "21:50";
                    xVals.add(time);
                    break;
                case 264:
                    time = "21:55";
                    xVals.add(time);
                    break;
                case 265:
                    time = "22:00";
                    xVals.add(time);
                    break;
                case 266:
                    time = "22:05";
                    xVals.add(time);
                    break;
                case 267:
                    time = "22:10";
                    xVals.add(time);
                    break;
                case 268:
                    time = "22:15";
                    xVals.add(time);
                    break;
                case 269:
                    time = "22:20";
                    xVals.add(time);
                    break;
                case 270:
                    time = "22:25";
                    xVals.add(time);
                    break;
                case 271:
                    time = "22:30";
                    xVals.add(time);
                    break;
                case 272:
                    time = "22:35";
                    xVals.add(time);
                    break;
                case 273:
                    time = "22:40";
                    xVals.add(time);
                    break;
                case 274:
                    time = "22:45";
                    xVals.add(time);
                    break;
                case 275:
                    time = "22:50";
                    xVals.add(time);
                    break;
                case 276:
                    time = "22:55";
                    xVals.add(time);
                    break;
                case 277:
                    time = "23:00";
                    xVals.add(time);
                    break;
                case 278:
                    time = "23:05";
                    xVals.add(time);
                    break;
                case 279:
                    time = "23:10";
                    xVals.add(time);
                    break;
                case 280:
                    time = "23:15";
                    xVals.add(time);
                    break;
                case 281:
                    time = "23:20";
                    xVals.add(time);
                    break;
                case 282:
                    time = "23:25";
                    xVals.add(time);
                    break;
                case 283:
                    time = "23:30";
                    xVals.add(time);
                    break;
                case 284:
                    time = "23:35";
                    xVals.add(time);
                    break;
                case 285:
                    time = "23:40";
                    xVals.add(time);
                    break;
                case 286:
                    time = "23:45";
                    xVals.add(time);
                    break;
                case 287:
                    time = "23:50";
                    xVals.add(time);
                    break;
                case 288:
                    time = "23:55";
                    xVals.add(time);
                    break;

            }

        }

        LogUtil.e("X====",xVals.size()+"");
        LogUtil.e("Y====",yVals.size()+"");

        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(yVals, "DataSet 1");
//        set1.setDrawCubic(true);
        set1.setLineWidth(1.0f);
        set1.setCircleSize(3f);
        set1.setCircleColor(Color.parseColor("#fd4634"));
        set1.setDrawFilled(true);//设置曲线填充是启用
        set1.setFillColor(getResources().getColor(R.color.line_filled));//射置曲线填充颜色
        set1.setFillAlpha(128);//射置曲线填充颜色透明度
        // create a data object with the datasets

        set1.setDrawValues(false);//去掉values值
        set1.setDrawCircles(false);//去掉values小圆圈


        data = new LineData(xVals, set1);
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
                        RequestMinutesData(gczbs, time, "A", 1);
                    }
                    if (tag.isChecked() && id_ck_car.isChecked()) {
                        ToastUtil.showToast("断面客车组合");
                        RequestMinutesData(gczbs, time, "A", 2);
                    }
                    if (tag.isChecked() && id_hc_car.isChecked()) {
                        ToastUtil.showToast("断面货车组合");
                        RequestMinutesData(gczbs, time, "A", 3);
                    }
                    if (tag.isChecked() && id_speed.isChecked()) {
                        ToastUtil.showToast("断面车速组合");
                        RequestMinutesData(gczbs, time, "A", 4);
                    }
                    break;

                case R.id.id_up:
                    tag = id_up;
                    tag.setChecked(true);
                    if (tag.isChecked() && id_auto_car.isChecked()) {
                        ToastUtil.showToast("上行机动车组合");
                        RequestMinutesData(gczbs, time, "S", 1);
                    }
                    if (tag.isChecked() && id_ck_car.isChecked()) {
                        ToastUtil.showToast("上行客车组合");
                        RequestMinutesData(gczbs, time, "S", 2);
                    }
                    if (tag.isChecked() && id_hc_car.isChecked()) {
                        ToastUtil.showToast("上行货车组合");
                        RequestMinutesData(gczbs, time, "S", 3);
                    }
                    if (tag.isChecked() && id_speed.isChecked()) {
                        ToastUtil.showToast("上行车速组合");
                        RequestMinutesData(gczbs, time, "S", 4);
                    }

                    break;
                case R.id.id_dowm:
                    tag = id_dowm;
                    tag.setChecked(true);
                    if (tag.isChecked() && id_auto_car.isChecked()) {
                        ToastUtil.showToast("下行机动车组合");
                        RequestMinutesData(gczbs, time, "X", 1);
                    }
                    if (tag.isChecked() && id_ck_car.isChecked()) {
                        ToastUtil.showToast("下行客车组合");
                        RequestMinutesData(gczbs, time, "X", 2);
                    }
                    if (tag.isChecked() && id_hc_car.isChecked()) {
                        ToastUtil.showToast("下行货车组合");
                        RequestMinutesData(gczbs, time, "X", 3);
                    }
                    if (tag.isChecked() && id_speed.isChecked()) {
                        ToastUtil.showToast("下行车速组合");
                        RequestMinutesData(gczbs, time, "X", 4);
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
                        RequestMinutesData(gczbs, time, "A", 1);
                    }
                    if (flag.isChecked() && id_up.isChecked()) {
                        ToastUtil.showToast("上行机动车组合");
                        RequestMinutesData(gczbs,time, "S", 1);
                    }
                    if (flag.isChecked() && id_dowm.isChecked()) {
                        ToastUtil.showToast("下行机动车组合");
                        RequestMinutesData(gczbs, time, "X", 1);
                    }
                    break;
                case R.id.id_ck_car:
                    flag = id_ck_car;
                    flag.setChecked(true);
                    if (flag.isChecked() && id_broken.isChecked()) {
                        ToastUtil.showToast("断面客车组合");
                        RequestMinutesData(gczbs, time, "A", 2);
                    }
                    if (flag.isChecked() && id_up.isChecked()) {
                        ToastUtil.showToast("上行客车组合");
                        RequestMinutesData(gczbs, time, "S", 2);
                    }
                    if (flag.isChecked() && id_dowm.isChecked()) {
                        ToastUtil.showToast("下行客车组合");
                        RequestMinutesData(gczbs, time, "X", 2);
                    }
                    break;
                case R.id.id_hc_car:
                    flag = id_hc_car;
                    flag.setChecked(true);
                    if (flag.isChecked() && id_broken.isChecked()) {
                        ToastUtil.showToast("断面货车组合");
                        RequestMinutesData(gczbs, time, "A", 3);
                    }
                    if (flag.isChecked() && id_up.isChecked()) {
                        ToastUtil.showToast("上行货车组合");
                        RequestMinutesData(gczbs, time, "S", 3);
                    }
                    if (flag.isChecked() && id_dowm.isChecked()) {
                        ToastUtil.showToast("下行货车组合");
                        RequestMinutesData(gczbs, time, "X", 3);
                    }
                    break;
                case R.id.id_speed:
                    flag = id_speed;
                    flag.setChecked(true);
                    if (flag.isChecked() && id_broken.isChecked()) {
                        ToastUtil.showToast("断面车速组合");
                        RequestMinutesData(gczbs, time, "A", 4);
                    }
                    if (flag.isChecked() && id_up.isChecked()) {
                        ToastUtil.showToast("上行车速组合");
                        RequestMinutesData(gczbs, time, "S", 4);
                    }
                    if (flag.isChecked() && id_dowm.isChecked()) {
                        ToastUtil.showToast("下行车速组合");

                        LogUtil.e("id_tv_time_minutes================",time);
                        RequestMinutesData(gczbs, time, "X", 4);
                    }
                    break;
            }

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_tv_time_minutes:
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

            id_tv_time_minutes.setText(str);

            time = id_tv_time_minutes.getText().toString().trim();

            id_broken.setChecked(true);
            id_auto_car.setChecked(true);
            tag = id_broken;
            tag.setChecked(true);
            flag = id_auto_car;
            flag.setChecked(true);

            RequestMinutesData(gczbs,time,"A",1);

        }
    };
}
