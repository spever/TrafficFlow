package com.subzero.trafficflow.activity;

import android.service.carrier.CarrierService;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.subzero.trafficflow.R;
import com.subzero.trafficflow.Utils.LogUtil;
import com.subzero.trafficflow.widget.xlistview.XListView;

/**
 * Created by hui on 2016/3/14.
 */
public class EditActivity extends BaseActivity implements View.OnClickListener {

    private XListView xListView;
    private ImageView id_iv_title_back;
    private TextView id_tv_num1;
    private TextView id_tv_num2;
    private TextView id_tv_num3;
    private TextView id_tv_num4;
    private TextView id_tv_num5;
    private TextView id_tv_num6;
    private TextView id_tv_num7;
    private TextView id_tv_num8;
    private TextView id_tv_num9;
    private RelativeLayout rl_num1;
    private RelativeLayout rl_num2;
    private RelativeLayout rl_num3;
    private RelativeLayout rl_num4;
    private RelativeLayout rl_num5;
    private RelativeLayout rl_num6;
    private RelativeLayout rl_num7;
    private RelativeLayout rl_num8;
    private RelativeLayout rl_num9;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_edit);
        inId();
        initListen();
    }

    private void initListen() {
        id_iv_title_back.setOnClickListener(this);
        rl_num1.setOnClickListener(this);
        rl_num2.setOnClickListener(this);
        rl_num3.setOnClickListener(this);
        rl_num4.setOnClickListener(this);
        rl_num5.setOnClickListener(this);
        rl_num6.setOnClickListener(this);
        rl_num7.setOnClickListener(this);
        rl_num8.setOnClickListener(this);
        rl_num9.setOnClickListener(this);
    }

    private void inId() {
        id_iv_title_back = (ImageView) findViewById(R.id.id_iv_title_back);
        id_tv_num1 = (TextView) findViewById(R.id.id_tv_num1);
        id_tv_num2 = (TextView) findViewById(R.id.id_tv_num2);
        id_tv_num3 = (TextView) findViewById(R.id.id_tv_num3);
        id_tv_num4 = (TextView) findViewById(R.id.id_tv_num4);
        id_tv_num5 = (TextView) findViewById(R.id.id_tv_num5);
        id_tv_num6 = (TextView) findViewById(R.id.id_tv_num6);
        id_tv_num7 = (TextView) findViewById(R.id.id_tv_num7);
        id_tv_num8 = (TextView) findViewById(R.id.id_tv_num8);
        id_tv_num9 = (TextView) findViewById(R.id.id_tv_num9);
        rl_num1 = (RelativeLayout) findViewById(R.id.rl_num1);
        rl_num2 = (RelativeLayout) findViewById(R.id.rl_num2);
        rl_num3 = (RelativeLayout) findViewById(R.id.rl_num3);
        rl_num4 = (RelativeLayout) findViewById(R.id.rl_num4);
        rl_num5 = (RelativeLayout) findViewById(R.id.rl_num5);
        rl_num6 = (RelativeLayout) findViewById(R.id.rl_num6);
        rl_num7 = (RelativeLayout) findViewById(R.id.rl_num7);
        rl_num8 = (RelativeLayout) findViewById(R.id.rl_num8);
        rl_num9 = (RelativeLayout) findViewById(R.id.rl_num9);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_num1:

                break;
            case R.id.rl_num2:
                break;
            case R.id.rl_num3:
                break;
            case R.id.rl_num4:
                break;
            case R.id.rl_num5:
                break;
            case R.id.rl_num6:
                break;
            case R.id.rl_num7:
                break;
            case R.id.rl_num8:
                break;
            case R.id.rl_num9:
                break;
            case R.id.id_iv_title_back:
                finish();
                break;
        }

    }
}
