package com.subzero.trafficflow.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.subzero.trafficflow.HttpConfig.UrlConstants;
import com.subzero.trafficflow.R;
import com.subzero.trafficflow.SharePreference.PreferenceDB;
import com.subzero.trafficflow.Utils.JsonUtil;
import com.subzero.trafficflow.Utils.LogUtil;
import com.subzero.trafficflow.Utils.ToastUtil;
import com.subzero.trafficflow.response.UserResponse;
import com.zhy.http.okhttp.callback.StringCallback;

import java.text.MessageFormat;

import okhttp3.Call;

/**
 * 我的模块/设置页面
 * Created by hui on 2016/3/14.
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_back;
    private TextView tv_about_us, tv_login_out, id_tv_contact_name, id_tv_name_id, id_tv_manager, id_tv_mail;
    private ImageView id_sex;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_setting);
        initListener();
    }

    private void initListener() {
        iv_back = (ImageView) findViewById(R.id.id_iv_back);
        id_sex = (ImageView) findViewById(R.id.id_sex);
        tv_about_us = (TextView) findViewById(R.id.id_tv_about_us);
        tv_login_out = (TextView) findViewById(R.id.id_tv_login_out);
        id_tv_contact_name = (TextView) findViewById(R.id.id_tv_contact_name);
        id_tv_name_id = (TextView) findViewById(R.id.id_tv_name_id);
        id_tv_manager = (TextView) findViewById(R.id.id_tv_manager);
        id_tv_mail = (TextView) findViewById(R.id.id_tv_mail);

        iv_back.setOnClickListener(this);
        tv_about_us.setOnClickListener(this);
        tv_login_out.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_iv_back:
                finish();
                break;
            case R.id.id_tv_about_us:
                Intent intentAbout = new Intent();
                intentAbout.setClass(this, AboutUsActivity.class);
                startActivity(intentAbout);
                break;
            case R.id.id_tv_login_out:
                RequestLoginOut();
                break;
        }
    }


    @Override
    public void initHttp(String url) {
        url = UrlConstants.LOGIN_OUT;
        super.initHttp(url);
    }

    private void RequestLoginOut() {

        base.addParams("cols", "USER_ID,GLJGBS,GLJGBH").addParams("userId", PreferenceDB.getInstance().getUserid()).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                LogUtil.e("response", "error");

            }

            @Override
            public void onResponse(String response) {
                LogUtil.e("response", response);
                String res = JsonUtil.getFieldValue(response, "result");
                UserResponse userResponse = JsonUtil.parseJsonToBean(res, UserResponse.class);
                if (userResponse.isSuccess()) {
                    LogUtil.e("response", "退出成功！");

                } else {
                    ToastUtil.showToast(userResponse.getError());
                }

                PreferenceDB.getInstance().cleanUser();
                Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();

            }
        });

    }

    @Override
    protected void initData() {
        if (PreferenceDB.getInstance().getSEX() == null) {
            id_sex.setVisibility(View.INVISIBLE);
        } else if (PreferenceDB.getInstance().getSEX().equals("1")) {
            id_sex.setImageResource(R.mipmap.ico_boy);
        } else if (PreferenceDB.getInstance().getSEX().equals("2")) {
            id_sex.setImageResource(R.mipmap.ico_girl);
        } else {
            id_sex.setVisibility(View.INVISIBLE);
        }

        id_tv_contact_name.setText(PreferenceDB.getInstance().getUserName());
        id_tv_name_id.setText(MessageFormat.format(" (ID:{0})", PreferenceDB.getInstance().getUserid()));
        id_tv_mail.setText(PreferenceDB.getInstance().getMAIL());
        id_tv_manager.setText(PreferenceDB.getInstance().getGLJGMC());

    }
}
