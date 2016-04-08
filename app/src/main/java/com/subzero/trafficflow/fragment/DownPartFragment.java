package com.subzero.trafficflow.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;

import com.subzero.trafficflow.HttpConfig.UrlConstants;
import com.subzero.trafficflow.R;
import com.subzero.trafficflow.SharePreference.PreferenceDB;
import com.subzero.trafficflow.Utils.JsonUtil;
import com.subzero.trafficflow.Utils.LogUtil;
import com.subzero.trafficflow.adapter.ContactListAdapter;
import com.subzero.trafficflow.bean.Contact;
import com.subzero.trafficflow.response.ContactResponse;
import com.subzero.trafficflow.widget.xlistview.XListView;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import okhttp3.Call;

/**
 * 联系人下属单位
 * Created by hui on 2016/3/14.
 */
public class DownPartFragment extends BaseFragment {


    private View v;
    private XListView xListView;
    private ArrayList<Contact> listData;

    @Override
    protected View initView() {

        v = View.inflate(getActivity(), R.layout.fragment_contact_listview, null);

        return v;
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
                LogUtil.e("response==down", response);
                String res = JsonUtil.getFieldValue(response, "result");

                ContactResponse contactResponse = JsonUtil.parseJsonToBean(res, ContactResponse.class);

                if (contactResponse.isSuccess()) {

                    listData = contactResponse.getData();
                    xListView.setAdapter(new ContactListAdapter(getActivity(), listData));

                }

            }

        });


    }

    @Override
    protected void initData() {

        xListView = (XListView) v.findViewById(R.id.id_contact_listview);
        xListView.setPullRefreshEnable(false);
        xListView.setPullLoadEnable(false);
        RequestContactData(2);

        xListView.setOnItemClickListener(listener);

    }

    AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            LogUtil.e("pos", position + "");

            String mobile = listData.get(position - 1).getMOBILE();
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mobile));
            startActivity(intent);


        }
    };

}
