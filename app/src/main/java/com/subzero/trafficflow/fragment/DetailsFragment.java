package com.subzero.trafficflow.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.subzero.trafficflow.HttpConfig.UrlConstants;
import com.subzero.trafficflow.R;
import com.subzero.trafficflow.Utils.JsonUtil;
import com.subzero.trafficflow.Utils.LogUtil;
import com.subzero.trafficflow.bean.StationInfo;
import com.subzero.trafficflow.response.StationInfoResponse;
import com.zhy.http.okhttp.callback.StringCallback;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;

/**
 * 大详情页面
 * Created by hui on 2016/3/10.
 */
public class DetailsFragment extends BaseFragment {

    private View v;
    private ImageView id_iv_status;
    private TextView id_tv_name;
    private TextView id_tv_number;
    private TextView id_tv_stat_type;
    private TextView id_tv_district;
    private TextView id_tv_belong;
    private TextView id_tv_mile;
    private TextView id_tv_start;
    private TextView id_tv_end;
    private TextView id_tv_bank;
    private TextView id_tv_time;
    private TextView id_tv_depart;
    private TextView id_tv_tel;
    private String gczbs;

    @Override
    protected View initView() {
        v = View.inflate(getActivity(), R.layout.fragment_detail, null);
        initId();
        return v;
    }

    private void initId() {

        id_iv_status = (ImageView) v.findViewById(R.id.id_iv_status);
        id_tv_name = (TextView) v.findViewById(R.id.id_tv_name);
        id_tv_number = (TextView) v.findViewById(R.id.id_tv_number);
        id_tv_stat_type = (TextView) v.findViewById(R.id.id_tv_stat_type);
        id_tv_district = (TextView) v.findViewById(R.id.id_tv_district);
        id_tv_belong = (TextView) v.findViewById(R.id.id_tv_belong);
        id_tv_mile = (TextView) v.findViewById(R.id.id_tv_mile);
        id_tv_start = (TextView) v.findViewById(R.id.id_tv_start);
        id_tv_end = (TextView) v.findViewById(R.id.id_tv_end);
        id_tv_bank = (TextView) v.findViewById(R.id.id_tv_bank);
        id_tv_time = (TextView) v.findViewById(R.id.id_tv_time);
        id_tv_depart = (TextView) v.findViewById(R.id.id_tv_depart);
        id_tv_tel = (TextView) v.findViewById(R.id.id_tv_tel);
    }

    @Override
    public void initHttp(String url) {
        url = UrlConstants.BASE_INFOR;
        super.initHttp(url);
    }

    private void RequestStationBaseInfo(String inputYear, String gczbs) {
        base.addParams("year", inputYear)
                .addParams("gczbs", gczbs)
                .addParams("cols", "GCZBS,GCZBH,GCZMC,GCZLX,GCZDJ,XZQHMC,LXBH,LXMC,LXLX,ZH,GCLC,QDMC,QDZH,ZDMC,ZDZH,JSDJ,JZRQ,GLJGMC,TEL,CONNECT_FLAG")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(String response) {

                LogUtil.e("response", response);

                String res = JsonUtil.getFieldValue(response, "result");
                LogUtil.e("res====info", res);
                StationInfoResponse infoResponse = JsonUtil.parseJsonToBean(res, StationInfoResponse.class);
                if (infoResponse != null && infoResponse.isSuccess()) {


//                    CONNECT_FLAG	0	人工
//                    CONNECT_FLAG	1	在线
//                    CONNECT_FLAG	2	离线
//                    CONNECT_FLAG	9	停测

                    int flag = infoResponse.getData().getCONNECT_FLAG();
                    if (flag == 0) {

                    } else if (flag == 1) {
                        id_iv_status.setImageResource(R.mipmap.on_line);
                    } else if (flag == 2) {
                        id_iv_status.setImageResource(R.mipmap.off_line);
                    } else if (flag == 3) {

                    }

//                    GCZLX	L	连续
//                    GCZLX	J	间隙
//                    GCZLX	B	比重
//                    GCZDJ	0	国家
//                    GCZDJ	1	省级
//                    GCZDJ	2	试用

                    String type = infoResponse.getData().getGCZLX();

                    String typeContent = "";

                    if (type.equals("L")) {
                        typeContent = "连续";
                    } else if (type.equals("J")) {
                        typeContent = "间隙";
                    } else if (type.equals("B")) {
                        typeContent = "比重";
                    }

                    String another = infoResponse.getData().getGCZDJ();

                    String anotherContent = "";
                    if (another.equals("0")) {
                        anotherContent = "国家";
                    } else if (another.equals("1")) {
                        anotherContent = "省级";
                    } else if (another.equals("2")) {
                        anotherContent = "使用";
                    }

                    id_tv_stat_type.setText(MessageFormat.format("{0}{1}", anotherContent, typeContent));
                    id_tv_district.setText(infoResponse.getData().getXZQHMC());
                    id_tv_belong.setText(MessageFormat.format("{0}[{1}]", infoResponse.getData().getLXBH(), infoResponse.getData().getLXMC()));
                    id_tv_mile.setText(MessageFormat.format("{0}km处,代表{1}km", infoResponse.getData().getZH(), infoResponse.getData().getGCLC()));
                    id_tv_start.setText(MessageFormat.format("{0}{1}[{2}km]", infoResponse.getData().getLXBH(), infoResponse.getData().getQDMC(), infoResponse.getData().getQDZH()));
                    id_tv_end.setText(MessageFormat.format("{0}[{1}km]", infoResponse.getData().getZDMC(), infoResponse.getData().getZDZH()));

//                    LXLX	10	国道
//                    LXLX	11	普通国道
//                    LXLX	12	国家高速
//                    LXLX	20	省道
//                    LXLX	21	普通省道
//                    LXLX	22	省级高速
//                    LXLX	30	县道
//                    LXLX	40	乡道
//                    LXLX	50	村道
//                    LXLX	60	专道

                    int LXLX = Integer.parseInt(infoResponse.getData().getLXLX());
                    String meanLXLX = "";
                    switch (LXLX) {
                        case 10:
                            meanLXLX = "国道";
                            break;
                        case 11:
                            meanLXLX = "普通国道";
                            break;
                        case 12:
                            meanLXLX = "国家高速";
                            break;
                        case 21:
                            meanLXLX = "省道";
                            break;
                        case 22:
                            meanLXLX = "普通省道";
                            break;
                        case 30:
                            meanLXLX = "县道";
                            break;
                        case 40:
                            meanLXLX = "乡道";
                            break;
                        case 50:
                            meanLXLX = "村道";
                            break;
                        case 60:
                            meanLXLX = "专道";
                            break;
                    }

                    int JSDJ = Integer.parseInt(infoResponse.getData().getJSDJ());
                    String mean = "";
                    switch (JSDJ) {
                        case 0:
                            mean = "高速公路";
                            break;
                        case 1:
                            mean = "一级公路";
                            break;
                        case 2:
                            mean = "二级公路";
                            break;
                        case 3:
                            mean = "三级公路";
                            break;
                        case 4:
                            mean = "四级公路";
                            break;
                        case 5:
                            mean = "等外公路";
                            break;
                    }


                    id_tv_bank.setText(MessageFormat.format("{0}[{1}]", meanLXLX, mean));
                    id_tv_time.setText(MessageFormat.format("{0}", new SimpleDateFormat("yyyy-MM-dd").format(new Date(infoResponse.getData().getJZRQ()))));
                    id_tv_depart.setText(infoResponse.getData().getGLJGMC());
                    id_tv_tel.setText(infoResponse.getData().getTEL());
                }

            }
        });

    }

    @Override
    protected void initData() {
        gczbs = getActivity().getIntent().getStringExtra("gczbs");
        RequestStationBaseInfo("2015", gczbs);
    }
}
