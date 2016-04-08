package com.subzero.trafficflow.fragment;

import android.graphics.Point;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import com.subzero.trafficflow.widget.BeeEditText;
import com.zhy.http.okhttp.callback.StringCallback;

import info.hoang8f.android.segmented.SegmentedGroup;
import okhttp3.Call;

/**
 * 站点页面
 * Created by hui on 2016/3/9.
 */
public class StationFragment extends BaseFragment{

    private View view;
    private RadioGroup rg_group;
    private AutoCarFragment mAutoCarFragment;
    private HcCarFragment mHcCarFragment;
    private KcCarFragment mKcCarFragment;
    private CrowdFragment mCrowdFragment;
    private SpeedFragment mSpeedFragment;



    @Override
    protected View initView() {
        view = View.inflate(getActivity(), R.layout.fragment_station, null);
        initId();

        setDefaultFragment();
        initRadioGroup();
        return view;
    }

    private void initId() {
        BeeEditText search_input = (BeeEditText) view.findViewById(R.id.search_input);
        search_input.setHint("搜索区划/站名/编号");
    }


    //设置默认选中机动车
    private void setDefaultFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        mAutoCarFragment = new AutoCarFragment();
        transaction.replace(R.id.id_label_container, mAutoCarFragment);
        transaction.commit();
    }

    private void initRadioGroup() {
        rg_group = (RadioGroup) view.findViewById(R.id.rg_group);
        rg_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                switch (checkedId) {
                    case R.id.id_auto_car:
                        if (mAutoCarFragment == null) {
                            mAutoCarFragment = new AutoCarFragment();
                        }
                        transaction.replace(R.id.id_label_container, mAutoCarFragment);
                        break;

                    case R.id.id_hc_car:
                        if (mHcCarFragment == null) {
                            mHcCarFragment = new HcCarFragment();
                        }
                        transaction.replace(R.id.id_label_container, mHcCarFragment);
                        break;

                    case R.id.id_ck_car:
                        if (mKcCarFragment == null) {
                            mKcCarFragment = new KcCarFragment();
                        }
                        transaction.replace(R.id.id_label_container, mKcCarFragment);
                        break;
                    case R.id.id_crowd:
                        if (mCrowdFragment == null) {
                            mCrowdFragment = new CrowdFragment();
                        }
                        transaction.replace(R.id.id_label_container, mCrowdFragment);
                        break;
                    case R.id.id_speed:
                        if (mSpeedFragment == null) {
                            mSpeedFragment = new SpeedFragment();
                        }
                        transaction.replace(R.id.id_label_container, mSpeedFragment);
                        break;

                    default:
                        break;
                }

                transaction.addToBackStack(null);
                transaction.commit();
            }

        });


    }


}
