package com.subzero.trafficflow.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.ZoomControls;

import com.baidu.mapapi.map.MapView;
import com.subzero.trafficflow.R;

import info.hoang8f.android.segmented.SegmentedGroup;

/**首页拥挤度页面
 * Created by hui on 2016/3/21.
 */
public class CrowdFragment extends BaseFragment {
    private View view;
    private MapView mBaiduMap;
    private SegmentedGroup view_left;
    private SegmentedGroup view_right;
    private RadioButton id_hour;
    private RadioButton id_day;
    private RadioButton id_yesterday;
    private RadioButton id_dowm;
    private RadioButton id_up;

    @Override
    protected View initView() {
        view = View.inflate(getActivity(), R.layout.fragment_station_item, null);
        initId();
        View child = mBaiduMap.getChildAt(1);
        if (child != null && (child instanceof ImageView || child instanceof ZoomControls)) {
            child.setVisibility(View.INVISIBLE);
        }
        mBaiduMap.showZoomControls(false);
        mBaiduMap.showScaleControl(false);
        return view;
    }

    private void initId() {
        mBaiduMap = (MapView) view.findViewById(R.id.id_map_auto_car);
        view_left = (SegmentedGroup) view.findViewById(R.id.view_left);
        view_right = (SegmentedGroup) view.findViewById(R.id.view_right);
        id_hour = (RadioButton) view.findViewById(R.id.id_hour);
        id_day = (RadioButton) view.findViewById(R.id.id_day);
        id_yesterday = (RadioButton) view.findViewById(R.id.id_yesterday);
        id_dowm = (RadioButton) view.findViewById(R.id.id_dowm);
        id_up = (RadioButton) view.findViewById(R.id.id_up);
    }

    @Override
    protected void initData() {

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
