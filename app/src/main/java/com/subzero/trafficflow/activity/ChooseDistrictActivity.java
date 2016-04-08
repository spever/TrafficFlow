package com.subzero.trafficflow.activity;

import android.widget.ImageView;

import com.subzero.trafficflow.HttpConfig.UrlConstants;
import com.subzero.trafficflow.R;
import com.subzero.trafficflow.Utils.LogUtil;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by hasee on 2016/4/6.
 */
public class ChooseDistrictActivity extends BaseActivity {

    private ImageView id_iv_title_back;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_choose_district);
        initId();
    }

    private void initId() {
        id_iv_title_back = (ImageView) findViewById(R.id.id_iv_title_back);
    }

    @Override
    public void initHttp(String url) {
        url = UrlConstants.DISTRICT_DOWN_INFO;
        super.initHttp(url);
    }

    private void RequestDistrictInfo(String xzqhdm) {
        base.addParams("xzqhdm", xzqhdm)
                .addParams("cols", "XZQHDM,XZQHMC,SJDM,DSDM,QXDM,SJMC,DSMC,QXMC")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {

                LogUtil.e("error", "error");
            }

            @Override
            public void onResponse(String response) {

                LogUtil.e("district---info", response);
            }
        });
    }

    @Override
    protected void initData() {

        RequestDistrictInfo("520000");
    }
}
