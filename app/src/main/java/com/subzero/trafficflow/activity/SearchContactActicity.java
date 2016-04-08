package com.subzero.trafficflow.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.subzero.trafficflow.HttpConfig.UrlConstants;
import com.subzero.trafficflow.R;
import com.subzero.trafficflow.SharePreference.PreferenceDB;
import com.subzero.trafficflow.Utils.JsonUtil;
import com.subzero.trafficflow.Utils.LogUtil;
import com.subzero.trafficflow.Utils.ToastUtil;
import com.subzero.trafficflow.adapter.ContactListAdapter;
import com.subzero.trafficflow.bean.Contact;
import com.subzero.trafficflow.response.ContactResponse;
import com.subzero.trafficflow.widget.BeeEditText;
import com.subzero.trafficflow.widget.xlistview.XListView;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import okhttp3.Call;

/**
 * 联系人搜索
 *
 * Created by hui on 2016/3/14.
 */
public class SearchContactActicity extends BaseActivity implements View.OnClickListener {

    private BeeEditText search_input;
    private ImageView iv_back;
    private String InputName;
    private TextView search_text_action;
    private ArrayList<Contact> listData;
    private XListView listView;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_search_contact);
        listView = (XListView) findViewById(R.id.id_contact_listview);
        listView.setPullRefreshEnable(false);
        listView.setPullLoadEnable(false);
        initListener();


    }

    private void initListener() {
        search_input = (BeeEditText) findViewById(R.id.search_input);
        iv_back = (ImageView) findViewById(R.id.nav_back_button);

        search_text_action = (TextView) findViewById(R.id.search_text_action);

        iv_back.setOnClickListener(this);
        search_text_action.setOnClickListener(this);

        listView.setOnItemClickListener(onItemClickListener);
    }

    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            String mobile = listData.get(position - 1).getMOBILE();
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mobile));
            if (ActivityCompat.checkSelfPermission(SearchContactActicity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            startActivity(intent);
        }
    };


    @Override
    public void initHttp(String url) {
        url = UrlConstants.CONTACT;
        super.initHttp(url);
    }

    protected void RequestContactData(String queryname) {


        base.addParams("gljgbs", PreferenceDB.getInstance().getGLJGBS())
                .addParams("scope", String.valueOf(1))
                .addParams("cols", "USER_ID,USER_NAME,SEX,MAIL,MOBILE,GLJGBS,GLJGBH,GLJGMC")
                .addParams("queryValue", queryname)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                LogUtil.e("response", "error");

            }

            @Override
            public void onResponse(String response) {
                LogUtil.e("response==search", response);
                String res = JsonUtil.getFieldValue(response, "result");

                ContactResponse contactResponse = JsonUtil.parseJsonToBean(res, ContactResponse.class);

                if (contactResponse.isSuccess()) {

                    listData = contactResponse.getData();
                    if (listData == null || listData.size() == 0) {
                        ToastUtil.showToast("没有查到该条数据！");
                        return;
                    }

                    listView.setAdapter(new ContactListAdapter(SearchContactActicity.this, listData));

                }

            }

        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nav_back_button:
                finish();
                break;
            case R.id.search_text_action:
                String put = search_input.getText().toString().trim();
                RequestContactData(put);
                break;
        }
    }

}
