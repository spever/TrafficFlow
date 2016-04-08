package com.subzero.trafficflow.activity;

import android.content.Intent;
import android.service.carrier.CarrierService;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.subzero.trafficflow.R;

/**
 * Created by hasee on 2016/4/6.
 */
public class FilterActivity extends BaseActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private ImageView id_iv_title_back;
    private EditText id_et_number;
    private EditText id_et_name;
    private RadioGroup groupDown;
    private RadioButton id_auto_car,id_ck_car,id_hc_car,id_crowd;
    private Button id_btn_confrim;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_filter);
        initId();

        initListener();
    }

    private void initListener() {
        id_iv_title_back.setOnClickListener(this);
        groupDown.setOnCheckedChangeListener(this);
        id_btn_confrim.setOnClickListener(this);
    }

    private void initId() {
        id_iv_title_back = (ImageView) findViewById(R.id.id_iv_title_back);
        id_et_number = (EditText) findViewById(R.id.id_et_number);
        id_et_name = (EditText) findViewById(R.id.id_et_name);
        id_btn_confrim = (Button) findViewById(R.id.id_btn_confrim);

        groupDown = (RadioGroup) findViewById(R.id.rg_group_des);
        id_auto_car = (RadioButton) findViewById(R.id.id_auto_car);
        id_ck_car = (RadioButton) findViewById(R.id.id_ck_car);
        id_hc_car = (RadioButton) findViewById(R.id.id_hc_car);
        id_crowd = (RadioButton) findViewById(R.id.id_crowd);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.id_iv_title_back:
                finish();
                break;
            case R.id.id_btn_confrim:
                Intent intent = new Intent();
                intent.setClass(this,RouteLineDetailActivity.class);
                startActivity(intent);
                break;
        }

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

    }
}
