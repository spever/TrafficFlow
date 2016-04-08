package com.subzero.trafficflow.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.subzero.trafficflow.HttpConfig.UrlConstants;
import com.subzero.trafficflow.R;
import com.subzero.trafficflow.SharePreference.PreferenceDB;
import com.subzero.trafficflow.Utils.JsonUtil;
import com.subzero.trafficflow.Utils.LogUtil;
import com.subzero.trafficflow.activity.DataCheckActivity;
import com.subzero.trafficflow.activity.RankItemDetailActivity;
import com.subzero.trafficflow.adapter.RankItemAutoCarAdapter;
import com.subzero.trafficflow.adapter.RankItemCrowdLevelAdapter;
import com.subzero.trafficflow.adapter.RankItemHcCarAdapter;
import com.subzero.trafficflow.adapter.RankItemKcCarAdapter;
import com.subzero.trafficflow.adapter.RankItemSpeedAdapter;
import com.subzero.trafficflow.bean.AutoCar;
import com.subzero.trafficflow.bean.CrowdLevel;
import com.subzero.trafficflow.bean.HcCar;
import com.subzero.trafficflow.bean.KcCar;
import com.subzero.trafficflow.bean.RankItem;
import com.subzero.trafficflow.bean.Speed;
import com.subzero.trafficflow.response.AutoCarResponse;
import com.subzero.trafficflow.response.CrowdLevelResponse;
import com.subzero.trafficflow.response.HcCarResponse;
import com.subzero.trafficflow.response.KcCarResponse;
import com.subzero.trafficflow.response.SpeedResponse;
import com.subzero.trafficflow.widget.xlistview.XListView;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import okhttp3.Call;

/**
 * 排行每个条目的fragment
 * Created by hui on 2016/3/10.
 */
public class RankItemFragment extends BaseFragment {

    /* gljgbs	String	管理机构标识
    periodType	Integer	时间段
            详见代码定义对照表中时间段定义
    gczbh	String	观测站编号（可选，用于条件查询）
    gczmc	String	观测站名称（可选，用于条件查询）
    xzqhdm	String	行政区划代码（可选，用于条件查询）
    column	Integer	指标项
            详见代码定义对照表中指标项定义
    order	Integer	排序方式
            详见代码定义对照表中排序方式定义
    start	Integer	分页起始记录，空为不分页
    limit	Integer	分页记录数量，空为不分页 */

    private ArrayList<RankItem> list;

    private View v;
    private XListView xListView;
    private int position;

    public static RankItemFragment newInstance(int position) {
        RankItemFragment f = new RankItemFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt("position");

        LogUtil.e("position", position + "");
    }

    @Override
    protected View initView() {

        v = View.inflate(getActivity(), R.layout.fragment_rankitem, null);
        initXlistView();
        return v;
    }

    @Override
    protected void initData() {

        if (position == 0) {
            RequestRankItemData(position, "JDC_DL", 1);
        }
        if (position == 1) {
            RequestRankItemData(position, "KC_DL", 2);
        }
        if (position == 2) {
            RequestRankItemData(position, "HC_DL", 3);
        }
        if (position == 3) {
            RequestRankItemData(position, "", 4);
        }
        if (position == 4) {
            RequestRankItemData(position, "", 5);
        }
    }

    @Override
    public void initHttp(String url) {
        url = UrlConstants.MAP_SCOPE;
        super.initHttp(url);

    }

    /* ORDER	0	不排序
     ORDER	1	正序
     ORDER	2	倒序*/
    private void RequestRankItemData(final int position, String type, int column) {

        showProgressDialog("数据加载中.......");
        base.addParams("gljgbs", PreferenceDB.getInstance().getGLJGBS())
                .addParams("cols", "GCZBS,GCZBH,GCZMC,YDDJ,CONNECT_FLAG,LATITUDE,LONGITUDE" + (type = type.length() > 0 ? ("," + type) : ""))
                .addParams("periodType", "2")
                .addParams("column", String.valueOf(column))
                .addParams("start", "0")
                .addParams("limit", "20")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                LogUtil.e("error", "error");
            }

            @Override
            public void onResponse(String response) {

                dismissProgressDialog();

                String res = JsonUtil.getFieldValue(response, "result");

//                LogUtil.e("response_item==" + position, response);

                if (position == 0) {
                    LogUtil.e("response_item==" + position, response);
                    AutoCarResponse autoCarResponse = JsonUtil.parseJsonToBean(res, AutoCarResponse.class);
                    if (autoCarResponse != null && autoCarResponse.isSuccess()) {
                        ArrayList<AutoCar> list_auto = autoCarResponse.getData();
                        xListView.setAdapter(new RankItemAutoCarAdapter(list_auto, getActivity()));
                    }

                }
                if (position == 1) {
                    LogUtil.e("response_item==" + position, response);
                    HcCarResponse hcCarResponse = JsonUtil.parseJsonToBean(res, HcCarResponse.class);
                    if (hcCarResponse != null && hcCarResponse.isSuccess()) {
                        ArrayList<HcCar> list_hcCar = hcCarResponse.getData();
                        xListView.setAdapter(new RankItemHcCarAdapter(list_hcCar, getActivity()));
                    }
                }
                if (position == 2) {
                    LogUtil.e("response_item==" + position, response);
                    KcCarResponse kcCarResponse = JsonUtil.parseJsonToBean(res, KcCarResponse.class);
                    if (kcCarResponse != null && kcCarResponse.isSuccess()) {
                        ArrayList<KcCar> list_kcCar = kcCarResponse.getData();
                        xListView.setAdapter(new RankItemKcCarAdapter(list_kcCar, getActivity()));
                    }
                }
                if (position == 3) {
                    LogUtil.e("response_item==" + position, response);
                    CrowdLevelResponse crowdLevelResponse = JsonUtil.parseJsonToBean(res, CrowdLevelResponse.class);
                    if (crowdLevelResponse != null && crowdLevelResponse.isSuccess()) {
                        ArrayList<CrowdLevel> list_crowd = crowdLevelResponse.getData();
                        xListView.setAdapter(new RankItemCrowdLevelAdapter(list_crowd, getActivity()));
                    }
                }
                if (position == 4) {
                    LogUtil.e("response_item==" + position, response);
                    SpeedResponse speedResponse = JsonUtil.parseJsonToBean(res, SpeedResponse.class);
                    if (speedResponse != null && speedResponse.isSuccess()) {
                        ArrayList<Speed> list_speed = speedResponse.getData();
                        xListView.setAdapter(new RankItemSpeedAdapter(list_speed, getActivity()));
                    }
                }


            }
        });

    }

    private void initXlistView() {
        xListView = (XListView) v.findViewById(R.id.id_xlistview_rank);
        xListView.setPullLoadEnable(true);
        xListView.setPullRefreshEnable(true);

        xListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("gczbs", "13000020130708154357579UalLOw1oG");
                intent.setClass(getActivity(), RankItemDetailActivity.class);
                startActivity(intent);
            }
        });
    }


}
