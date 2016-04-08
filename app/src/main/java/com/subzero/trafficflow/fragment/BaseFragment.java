package com.subzero.trafficflow.fragment;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.subzero.trafficflow.HttpConfig.UrlConstants;
import com.subzero.trafficflow.R;
import com.subzero.trafficflow.SharePreference.PreferenceDB;
import com.subzero.trafficflow.Utils.StringUtils;
import com.subzero.trafficflow.manager.SystemBarTintManager;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;

import org.xutils.common.util.LogUtil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by hui on 2016/3/9.
 */
public abstract class BaseFragment extends Fragment {

    protected View view;

    public String url;
    protected PostFormBuilder base;


    public ProgressDialog progressDialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSystemBar();
        initHttp(url);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = initView();
        return view;
    }


    public void initHttp(String url) {

        base = OkHttpUtils.post().url(UrlConstants.BASE + url).addParams("license", UrlConstants.license).addParams("token", PreferenceDB.getInstance().getUserAccesstoken());
    }


    public void showProgressDialog(String msg) {
        if (progressDialog != null && progressDialog.isShowing()) {
            return;
        }
        progressDialog = new ProgressDialog(getActivity());
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

    private void initSystemBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            SystemBarTintManager tintManager = new SystemBarTintManager(getActivity());
            tintManager.setStatusBarTintColor(getResources().getColor(R.color.theme_color));
            tintManager.setStatusBarTintEnabled(true);
        }
    }

    public static void getUrl(HashMap map, String sendurl) {
        StringBuffer sbUrl = new StringBuffer();

        //编辑地址
        sbUrl.append(UrlConstants.BASE + sendurl);
//        String methedName = (String) map.get("methodname");
//        sbUrl.append("/" + methedName);
        //如果是post请求返回地址

        sbUrl.append("?");


        Iterator iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = (String) entry.getKey();
            String val = entry.getValue() + "";
            if (!StringUtils.isNullOrEmpty(key) && !StringUtils.isNullOrEmpty(val)) {
                sbUrl.append(key + "=");
                sbUrl.append(val + "&");
            }
        }
        sbUrl.deleteCharAt(sbUrl.length() - 1);
        com.subzero.trafficflow.Utils.LogUtil.e("url", sbUrl.toString());

    }

    protected abstract View initView();

    protected void initData() {
    }
}
