package com.subzero.trafficflow.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.github.mikephil.charting.data.BubbleData;
import com.google.gson.reflect.TypeToken;
import com.subzero.trafficflow.HttpConfig.UrlConstants;
import com.subzero.trafficflow.R;
import com.subzero.trafficflow.SharePreference.PreferenceDB;
import com.subzero.trafficflow.Utils.JsonUtil;
import com.subzero.trafficflow.Utils.LogUtil;
import com.subzero.trafficflow.activity.SearchContactActicity;
import com.subzero.trafficflow.activity.SettingActivity;
import com.subzero.trafficflow.bean.Contact;
import com.subzero.trafficflow.bean.User;
import com.subzero.trafficflow.response.ContactResponse;
import com.zhy.http.okhttp.callback.StringCallback;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * 我的模块
 * Created by hui on 2016/3/9.
 */
public class MineFragment extends BaseFragment implements View.OnClickListener {

    private View v;
    private RadioGroup radioGroup;
    private Fragment[] mFragments;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private ImageView id_iv_setting, id_iv_search_contact;

    private static final String UpFragment = "100";
    private static final String downFragment = "101";

    public  ArrayList<Contact> listdata = new ArrayList<>();
    private ArrayList<Contact> listData;

    @Override
    protected View initView() {
        v = View.inflate(getActivity(), R.layout.fragment_mine, null);
        initRadioGroup();
        initFragment();
        initListener();
        return v;
    }

    private void initListener() {
        id_iv_setting = (ImageView) v.findViewById(R.id.id_iv_setting);
        id_iv_search_contact = (ImageView) v.findViewById(R.id.id_iv_search_contact);
        id_iv_setting.setOnClickListener(this);
        id_iv_search_contact.setOnClickListener(this);
    }

    @Override
    public void initHttp(String url) {
        url = UrlConstants.CONTACT;
        super.initHttp(url);
    }

    protected void RequestContactData(int type) {


        base.addParams("gljgbs", PreferenceDB.getInstance().getGLJGBS())
                .addParams("scope", String.valueOf(type))
                .addParams("cols", "USER_ID,USER_NAME,SEX,MAIL,MOBILE,GLJGBS,GLJGBH,GLJGMC")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                LogUtil.e("response", "error");

            }

            @Override
            public void onResponse(String response) {
                LogUtil.e("response==mine", response);
                String res = JsonUtil.getFieldValue(response, "result");

                ContactResponse contactResponse = JsonUtil.parseJsonToBean(res, ContactResponse.class);

                if (contactResponse.isSuccess()) {

                    listData = contactResponse.getData();

                }

            }

        });


    }

    private void initFragment() {

        mFragments = new Fragment[2];
        fragmentManager = getFragmentManager();
        mFragments[0] = new UpPartFragment();
        mFragments[1] = new DownPartFragment();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.id_contact_container, mFragments[0]);
        fragmentTransaction.add(R.id.id_contact_container, mFragments[1]);
        fragmentTransaction.hide(mFragments[0]).hide(mFragments[1]);
        fragmentTransaction.show(mFragments[0]).commit();


        setFragmentIndicator();
    }

    private void setFragmentIndicator() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {

                    case R.id.id_up_part:
                        setShowFragment(0);
                        id_iv_search_contact.setTag("id_up_part");

                        break;
                    case R.id.id_down_part:
                        setShowFragment(1);

                        id_iv_search_contact.setTag("id_down_part");

                        break;

                }

            }
        });
    }

    private void setShowFragment(int fragmentNum) {
        fragmentTransaction = fragmentManager.beginTransaction().
                hide(mFragments[0]).hide(mFragments[1]);
        fragmentTransaction.show(mFragments[fragmentNum]).commit();
    }

    private void initRadioGroup() {
        radioGroup = (RadioGroup) v.findViewById(R.id.rg_group);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_iv_setting:
                Intent intentSetting = new Intent();
                intentSetting.setClass(getActivity(), SettingActivity.class);
                startActivity(intentSetting);
                break;
            case R.id.id_iv_search_contact:
                Intent intentSearch = new Intent();
                intentSearch.setClass(getActivity(), SearchContactActicity.class);
                startActivity(intentSearch);
                break;
        }
    }
}
