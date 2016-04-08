package com.subzero.trafficflow.activity;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.WindowManager;

import com.subzero.trafficflow.HttpConfig.UrlConstants;
import com.subzero.trafficflow.R;
import com.subzero.trafficflow.SharePreference.PreferenceDB;
import com.subzero.trafficflow.manager.SystemBarTintManager;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;

/**
 * Created by hui on 2016/3/9.
 */
public abstract class BaseActivity extends FragmentActivity {


    public  String url;
    public ProgressDialog progressDialog;
    protected  PostFormBuilder base;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initHttp(url);
        initSystemBar();
        initData();
    }



    public void initHttp(String url) {



        base = OkHttpUtils.post().url(UrlConstants.BASE + url).addParams("license", UrlConstants.license).addParams("token", PreferenceDB.getInstance().getUserAccesstoken());
    }



    public void showProgressDialog(String msg) {
        if (progressDialog != null && progressDialog.isShowing()) {
            return;
        }
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(msg);
        try {
            progressDialog.show();
        } catch (WindowManager.BadTokenException exception) {
            exception.printStackTrace();
        }
    }

    public void dismissProgressDialog() {
        if (null != progressDialog && progressDialog.isShowing() == true) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    protected void initSystemBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintColor(getResources().getColor(R.color.theme_color));
            tintManager.setStatusBarTintEnabled(true);
        }
    }

    protected abstract void initView();

    protected void initData() {
    }
}
