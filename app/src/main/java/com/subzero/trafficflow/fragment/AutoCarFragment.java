package com.subzero.trafficflow.fragment;

import android.content.Intent;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ZoomControls;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.subzero.trafficflow.HttpConfig.UrlConstants;
import com.subzero.trafficflow.R;
import com.subzero.trafficflow.SharePreference.PreferenceDB;
import com.subzero.trafficflow.Utils.LogUtil;
import com.subzero.trafficflow.Utils.ToastUtil;
import com.subzero.trafficflow.activity.RankActivity;
import com.zhy.http.okhttp.callback.StringCallback;



import info.hoang8f.android.segmented.SegmentedGroup;
import okhttp3.Call;

/**
 * 首页机动车页面
 * Created by hui on 2016/3/21.
 */
public class AutoCarFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener, BaiduMap.OnMapStatusChangeListener {

    private View view;
    private MapView mBaiduMap;
    private SegmentedGroup view_left;
    private SegmentedGroup view_right;
    private RadioButton id_hour;
    private RadioButton id_month;
    private RadioButton id_yesterday;
    private RadioButton id_down;
    private RadioButton id_up;


    private RadioButton tag;
    private RadioButton flag;
    private LatLng ll;


    @Override
    protected View initView() {
        view = View.inflate(getActivity(), R.layout.fragment_station_item, null);
        initId();
        initListener();
        View child = mBaiduMap.getChildAt(1);
        if (child != null && (child instanceof ImageView || child instanceof ZoomControls)) {
            child.setVisibility(View.INVISIBLE);
        }
        mBaiduMap.showZoomControls(false);
        mBaiduMap.showScaleControl(false);
        return view;
    }

    private void initListener() {
        view_left.setOnCheckedChangeListener(this);
        view_right.setOnCheckedChangeListener(this);
        mBaiduMap.getMap().setOnMapStatusChangeListener(this);

    }

    private void initId() {
        mBaiduMap = (MapView) view.findViewById(R.id.id_map_auto_car);
        view_left = (SegmentedGroup) view.findViewById(R.id.view_left);
        view_right = (SegmentedGroup) view.findViewById(R.id.view_right);
        id_hour = (RadioButton) view.findViewById(R.id.id_hour);
        id_month = (RadioButton) view.findViewById(R.id.id_month);
        id_yesterday = (RadioButton) view.findViewById(R.id.id_yesterday);
        id_down = (RadioButton) view.findViewById(R.id.id_down);
        id_up = (RadioButton) view.findViewById(R.id.id_up);
    }

    @Override
    protected void initData() {
        id_hour.setChecked(true);
        id_down.setChecked(true);

        tag = id_hour;
        tag.setChecked(true);
        flag = id_down;
        flag.setChecked(true);


        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int max_x = dm.widthPixels;
        int max_y = dm.heightPixels;

        LogUtil.e("屏幕宽高：", "屏幕宽:" + max_x + " 高:" + max_y);



    }

    private LatLng getSite(int type) {
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int max_x = dm.widthPixels;
        int max_y = dm.heightPixels;

        Point pt = new Point();
        if (type == 1) {
            //左上角
            pt.x = 0;
            pt.y = 0;
        }
        if (type == 2) {
            //左下角
            pt.x = 0;
            pt.y = dm.heightPixels;
        }
        if (type == 3) {
            //右上角
            pt.x = dm.widthPixels;
            pt.y = 0;
        }
        if (type == 4) {
            //右下角
            pt.x = dm.widthPixels;
            pt.y = dm.heightPixels;
        }

        ll = mBaiduMap.getMap().getProjection().fromScreenLocation(pt);

        return ll;

    }


    @Override
    public void onMapStatusChangeStart(MapStatus mapStatus) {

//        LatLng latLng = mapStatus.target;
//        double latitude = latLng.latitude;
//        double longitude = latLng.longitude;
//
//        ll = latLng;


    }

    @Override
    public void onMapStatusChange(MapStatus mapStatus) {



    }

    @Override
    public void onMapStatusChangeFinish(MapStatus mapStatus) {
        getSite(1);
        LogUtil.e("左上角经纬度为", "左上角经度 x:" + ll.latitude + " 左上角纬度 y:" + ll.longitude);
//
//        getSite(2);
//        LogUtil.e("左下角经纬度为", "左下角经纬度 x:" + ll.latitude + " y:" + ll.longitude);
//
//        getSite(3);
//        LogUtil.e("右上角经纬度为", "右上角经纬度 x:" + ll.latitude + " y:" + ll.longitude);
//
        getSite(4);
        LogUtil.e("右下角经纬度为", "右下角经度 x:" + ll.latitude + "右下角纬度 y:" + ll.longitude);

        RequestAutoCarData(0, 1, String.valueOf(getSite(1).latitude), String.valueOf(getSite(1).longitude), String.valueOf(getSite(4).latitude), String.valueOf(getSite(4).longitude));
//        RequestAutoCarData(0, 1, "26.49724812121212", "106.92274523232323", "26.44510823322323","106.96960412234312");


    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (group == view_left) {
            switch (checkedId) {
                case R.id.id_hour:
                    tag = id_hour;
                    tag.setChecked(true);
                    if (id_down.isChecked()) {
                        ToastUtil.showToast("上小时与下箭头组合");

                    }
                    if (id_up.isChecked()) {
                        ToastUtil.showToast("上小时与上箭头组合");

                    }
                    break;
                case R.id.id_yesterday:
                    tag = id_yesterday;
                    tag.setChecked(true);
                    if (id_down.isChecked()) {
                        ToastUtil.showToast("昨天与下箭头组合");
                        startActivity(new Intent(getActivity(), RankActivity.class));
                    }
                    if (id_up.isChecked()) {
                        ToastUtil.showToast("昨天与上箭头组合");

                    }

                    break;
                case R.id.id_month:
                    tag = id_month;
                    tag.setChecked(true);
                    if (id_down.isChecked()) {
                        ToastUtil.showToast("上月与下箭头组合");

                    }
                    if (id_up.isChecked()) {
                        ToastUtil.showToast("上月与上箭头组合");

                    }
                    break;
            }

        }

        if (group == view_right) {
            switch (checkedId) {
                case R.id.id_down:
                    flag = id_down;
                    flag.setChecked(true);
                    if (id_hour.isChecked()) {
                        ToastUtil.showToast("上小时与下箭头组合");
                    }
                    if (id_yesterday.isChecked()) {
                        ToastUtil.showToast("昨天与上箭头组合");

                    }
                    if (id_month.isChecked()) {
                        ToastUtil.showToast("上月上箭头组合");

                    }
                    break;
                case R.id.id_up:
                    flag = id_up;
                    flag.setChecked(true);
                    if (id_hour.isChecked()) {
                        ToastUtil.showToast("上小时与下箭头组合");
                    }
                    if (id_yesterday.isChecked()) {
                        ToastUtil.showToast("昨天与上箭头组合");

                    }
                    if (id_month.isChecked()) {
                        ToastUtil.showToast("上月上箭头组合");

                    }
                    break;
            }

        }

    }

    @Override
    public void initHttp(String url) {
        url = UrlConstants.MAP_SCOPE;
        super.initHttp(url);
    }


    //    PERIOD_TYPE	0	上小时
    //    PERIOD_TYPE	1	昨日
    //    PERIOD_TYPE	2	上月
//    COLUMN	1	机动车
//    COLUMN	2	货车
//    COLUMN	3	客车
//    COLUMN	4	拥挤度
//    COLUMN	5	车速

    //    ltLat	String（移动端无法组装BigDecimal）	屏幕左上角经度
//    ltLon	String	屏幕左上角纬度
//    rbLat	String	屏幕右下角经度
//    rbLon	String	屏幕右下角纬度
    private void RequestAutoCarData(int PERIOD_TYPE, int COLUMN, String ltLat, String ltLon, String rbLat, String rbLon) {
        base.addParams("gljgbs", PreferenceDB.getInstance().getGLJGBS())
                .addParams("cols","GCZBS,GCZBH,GCZMC,JDC_DL,JDC_CS,YDDJ,LATITUDE,LONGITUDE,CONNECT_FLAG")
                .addParams("periodType", String.valueOf(PERIOD_TYPE))
                .addParams("column", String.valueOf(COLUMN))
                .addParams("ltLat", ltLat)
                .addParams("ltLon", ltLon)
                .addParams("rbLat", rbLat)
                .addParams("rbLon", rbLon)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                LogUtil.e("response","error");

                e.printStackTrace();

            }

            @Override
            public void onResponse(String response) {

                LogUtil.e("response", response);


            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mBaiduMap.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mBaiduMap.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mBaiduMap.onPause();
    }
}
