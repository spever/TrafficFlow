package com.subzero.trafficflow.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.subzero.trafficflow.HttpConfig.UrlConstants;
import com.subzero.trafficflow.R;
import com.subzero.trafficflow.SharePreference.PreferenceDB;
import com.subzero.trafficflow.Utils.DateUtil;
import com.subzero.trafficflow.Utils.JsonUtil;
import com.subzero.trafficflow.Utils.LogUtil;
import com.subzero.trafficflow.adapter.QualityAdapter;
import com.subzero.trafficflow.bean.Device;
import com.subzero.trafficflow.bean.QualityDay;
import com.subzero.trafficflow.bean.QualityMonth;
import com.subzero.trafficflow.response.DeviceResponse;
import com.subzero.trafficflow.response.QualityDayResponse;
import com.subzero.trafficflow.response.QualityMonthResponse;
import com.subzero.trafficflow.widget.CircleProgressView;
import com.subzero.trafficflow.widget.MonPickerDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicIntegerArray;

import okhttp3.Call;

/**
 * 质量页面
 * Created by hui on 2016/3/10.
 */
public class QualityFragment extends BaseFragment implements View.OnClickListener {


    private Map<Integer, Boolean> isSelected;

    private List beSelectedData = new ArrayList();

    private View v;
    private ListView mListView;
    private CircleProgressView progressDayView1, progressDayView2, progressDayView3, progressMonthView1, progressMonthView2, progressMonthView3;
    private TextView id_tv_time_day;
    private TextView id_tv_time_month;
    private ImageView imageView2;
    private ImageView imageView3;
    private int iYear;
    private int iMonth;
    private int iDay;
    private Calendar calendar;
    private String str;

    private int type;
    private String gczbs;
    private PostFormBuilder common;
    private ArrayList<Device> list_device;
    private ArrayList<QualityDay> list_qualityDay;
    private ArrayList<QualityMonth> list_qualityMonth;

    @Override
    protected View initView() {
        v = View.inflate(getActivity(), R.layout.fragment_quality, null);
        initId();
        initListView();
        initListener();
        calendar = Calendar.getInstance();
        iYear = calendar.get(Calendar.YEAR);
        iMonth = calendar.get(Calendar.MONTH);
        iDay = calendar.get(Calendar.DAY_OF_MONTH);

        return v;
    }

    private void initListener() {
        id_tv_time_day.setOnClickListener(this);
        id_tv_time_month.setOnClickListener(this);
        imageView2.setOnClickListener(this);
        imageView3.setOnClickListener(this);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LogUtil.e("test", position + "");
            }
        });
    }

    private void initId() {
        id_tv_time_day = (TextView) v.findViewById(R.id.id_tv_time_day);
        id_tv_time_month = (TextView) v.findViewById(R.id.id_tv_time_month);
        imageView2 = (ImageView) v.findViewById(R.id.imageView2);
        imageView3 = (ImageView) v.findViewById(R.id.imageView3);
        progressDayView1 = (CircleProgressView) v.findViewById(R.id.id_day_progress1);
        progressDayView2 = (CircleProgressView) v.findViewById(R.id.id_day_progress2);
        progressDayView3 = (CircleProgressView) v.findViewById(R.id.id_day_progress3);
        progressMonthView1 = (CircleProgressView) v.findViewById(R.id.id_month_progress1);
        progressMonthView2 = (CircleProgressView) v.findViewById(R.id.id_month_progress2);
        progressMonthView3 = (CircleProgressView) v.findViewById(R.id.id_month_progress3);

    }


    private void initCircleProgress(CircleProgressView view, double progress, int size, String txtHint1, int roundColor, int roundProgressColor, float rounWidth) {
        view.setProgress(progress);
        view.setTextSize(size);
        view.setmTxtHint1(txtHint1);
        view.setRoundColor(roundColor);
        view.setRoundProgressColor(roundProgressColor);
        view.setRoundWidth(rounWidth);
    }

    private void initListView() {
        mListView = (ListView) v.findViewById(R.id.id_listview);
    }

    private void RequestQuality(int type, String inputYear, String inputMonth, String time, String sbsbm) {
        if (type == 1) {
            String url = UrlConstants.QUALITY_DEVICE;
            OkHttpUtils.post().url(UrlConstants.BASE + url)
                    .addParams("license", UrlConstants.license)
                    .addParams("token", PreferenceDB.getInstance().getUserAccesstoken())
                    .addParams("gczbs", gczbs)
                    .addParams("cols", "SBSBM,MAKER_NAME,MODEL_CODE")
                    .build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e) {

                    LogUtil.e("error---1", "ssssssssss111111");
                    e.printStackTrace();

                }

                @Override
                public void onResponse(String response) {

                    LogUtil.e("response---1", response);

                    String res = JsonUtil.getFieldValue(response, "result");
                    DeviceResponse deviceResponse = JsonUtil.parseJsonToBean(res, DeviceResponse.class);
                    if (deviceResponse != null && deviceResponse.isSuccess()) {
                        list_device = deviceResponse.getData();
                        mListView.setAdapter(new QualityAdapter(getActivity(), list_device));

                        if (isSelected != null)
                            isSelected = null;
                        isSelected = new HashMap<Integer, Boolean>();
                        for (int i = 0; i < list_device.size(); i++) {
                            isSelected.put(i, false);
                        }
                        // 清除已经选择的项
                        if (beSelectedData.size() > 0) {
                            beSelectedData.clear();
                        }
                    }


                }
            });

        } else if (type == 2) {
            String url = UrlConstants.QUALITY_DAY;
            OkHttpUtils.post().url(UrlConstants.BASE + url)
                    .addParams("license", UrlConstants.license)
                    .addParams("token", PreferenceDB.getInstance().getUserAccesstoken())
                    .addParams("year", inputYear)
                    .addParams("gcrq", time)
                    .addParams("sbsbm", sbsbm)
                    .addParams("cols", "SBSBM,MAKER_NAME,MODEL_CODE,PCT_FULL,PCT_ERROR,PCT_TIME,QUALITY")
                    .build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e) {
                    LogUtil.e("error---3", "ssssssssss22222");

                }

                @Override
                public void onResponse(String response) {

                    LogUtil.e("response---2", response);

                    String res = JsonUtil.getFieldValue(response, "result");
                    QualityDayResponse qualityDayResponse = JsonUtil.parseJsonToBean(res, QualityDayResponse.class);
                    if (qualityDayResponse != null && qualityDayResponse.isSuccess()) {
                        list_qualityDay = qualityDayResponse.getData();

                        if (list_qualityDay != null && list_qualityDay.size() > 0) {

                            initCircleProgress(progressDayView1, list_qualityDay.get(0).getPCT_FULL(), 18, "完整率", Color.WHITE, Color.GREEN, 20f);
                            initCircleProgress(progressDayView2, list_qualityDay.get(0).getPCT_ERROR(), 18, "异常率", Color.WHITE, Color.RED, 20f);
                            initCircleProgress(progressDayView3, list_qualityDay.get(0).getPCT_TIME(), 18, "及时率", Color.WHITE, Color.BLUE, 20f);
                        }
                    }


                }
            });
        } else if (type == 3) {
            String url = UrlConstants.QUALITY_MONTH;
            OkHttpUtils.post().url(UrlConstants.BASE + url)
                    .addParams("license", UrlConstants.license)
                    .addParams("token", PreferenceDB.getInstance().getUserAccesstoken())
                    .addParams("year", inputYear)
                    .addParams("month", inputMonth)
                    .addParams("sbsbm", sbsbm)
                    .addParams("cols", "SBSBM,MAKER_NAME,MODEL_CODE,PCT_FULL,PCT_ERROR,PCT_TIME,QUALITY")
                    .build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e) {
                    LogUtil.e("error---3", "ssssssssss333333");

                }

                @Override
                public void onResponse(String response) {

                    LogUtil.e("response---3", response);

                    String res = JsonUtil.getFieldValue(response, "result");

                    QualityMonthResponse qualityMonthResponse = JsonUtil.parseJsonToBean(res, QualityMonthResponse.class);


                    if (qualityMonthResponse != null && qualityMonthResponse.isSuccess()) {
                        list_qualityMonth = qualityMonthResponse.getData();

                        LogUtil.e("month_list", list_qualityMonth.toString());

                        if (list_qualityMonth != null && list_qualityMonth.size() > 0) {

                            initCircleProgress(progressMonthView1, list_qualityMonth.get(0).getPCT_FULL(), 18, "完整率", Color.WHITE, Color.GREEN, 20f);
                            initCircleProgress(progressMonthView2, list_qualityMonth.get(0).getPCT_ERROR(), 18, "异常率", Color.WHITE, Color.RED, 20f);
                            initCircleProgress(progressMonthView3, list_qualityMonth.get(0).getPCT_TIME(), 18, "及时率", Color.WHITE, Color.BLUE, 20f);
                        }

                    }

                }
            });

        }

    }

    @Override
    protected void initData() {

        gczbs = getActivity().getIntent().getStringExtra("gczbs");
        LogUtil.e("gczbs-----quality", gczbs);


        RequestQuality(1, "", "", "", "");

//        String sbsbm = list_device.get(0).getSBSBM();
//
//        LogUtil.e("sbsbm========================",sbsbm);
        RequestQuality(2, "2016", "", "2016-3-8", "0071111309090030");
        RequestQuality(3, "2016", "1", "", "0071111309090030");


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_tv_time_day:
            case R.id.imageView2:
                selectDayTime();
                break;
            case R.id.id_tv_time_month:
            case R.id.imageView3:
                selectMonthTime();
                break;
        }

    }

    private void selectMonthTime() {
        calendar.setTime(DateUtil.strToDate("yyyy-MM", id_tv_time_month.getText().toString()));
        new MonPickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                id_tv_time_month.setText(DateUtil.clanderTodatetime(calendar, "yyyy-MM"));

                String[] out = id_tv_time_month.getText().toString().trim().split("-");
                RequestQuality(3, out[0], out[1], "", "0071111309090030");

            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE)).show();

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

            id_tv_time_day.setText(str);

            String in = id_tv_time_day.getText().toString().trim().substring(0, 4);
            LogUtil.e("in------------", in);

            RequestQuality(2, in, "", id_tv_time_day.getText().toString().trim(), "0071111309090030");

        }
    };


    private void selectDayTime() {
        DatePickerDialog picker = new DatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, listener, iYear, iMonth, iDay);
        picker.setCancelable(true);
        picker.setCanceledOnTouchOutside(true);
        picker.show();

    }

}
