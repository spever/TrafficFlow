package com.subzero.trafficflow.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.subzero.trafficflow.R;
import com.subzero.trafficflow.Utils.LogUtil;
import com.subzero.trafficflow.fragment.DetailsFragment;
import com.subzero.trafficflow.fragment.LabelFragment;
import com.subzero.trafficflow.fragment.QualityFragment;

import info.hoang8f.android.segmented.SegmentedGroup;

/**
 * 排行条目详情页面
 * Created by hui on 2016/3/10.
 */
public class RankItemDetailActivity extends BaseActivity implements View.OnClickListener {

    private Fragment[] mFragments;
    private FragmentManager manager;
    private FragmentTransaction fragmentTransaction;
    public SegmentedGroup rgGroup;
    private ImageView id_iv_back;
    private ImageView id_iv_edit;
    private String gczbs;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_rank_item_detail);

        initId();
        initListener();
        initContainer();

        gczbs = getIntent().getStringExtra("gczbs");


    }

    private void initListener() {
        id_iv_back.setOnClickListener(this);
        id_iv_edit.setOnClickListener(this);
    }

    private void initId() {
        id_iv_back = (ImageView) findViewById(R.id.id_iv_back);
        id_iv_edit = (ImageView) findViewById(R.id.id_iv_edit);
    }

    private void initContainer() {
        mFragments = new Fragment[3];
        mFragments[0] = new LabelFragment();
        mFragments[1] = new QualityFragment();
        mFragments[2] = new DetailsFragment();
        manager = getSupportFragmentManager();
        fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.add(R.id.id_content, mFragments[0]);
        fragmentTransaction.add(R.id.id_content, mFragments[1]);
        fragmentTransaction.add(R.id.id_content, mFragments[2]);
        fragmentTransaction.hide(mFragments[0]).hide(mFragments[1]).hide(mFragments[2]);

        fragmentTransaction.show(mFragments[0]).commit();
        setFragmentIndicator();

    }

    private void setFragmentIndicator() {

        rgGroup = (SegmentedGroup) findViewById(R.id.id_segmented);
        rgGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.id_label:
                        setShowFragment(0);
                        break;

                    case R.id.id_quality:
                        setShowFragment(1);
                        break;

                    case R.id.id_detail:
                        setShowFragment(2);
                        break;

                    default:
                        break;
                }
            }
        });
    }

    /**
     * 设置跳转fragment
     *
     * @param fragmentnum
     */
    public void setShowFragment(int fragmentnum) {
        fragmentTransaction = manager.beginTransaction()
                .hide(mFragments[0]).hide(mFragments[1])
                .hide(mFragments[2]);
        fragmentTransaction.show(mFragments[fragmentnum]).commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_iv_back:
                finish();
                break;
            case R.id.id_iv_edit:
                Intent intent = new Intent();
                intent.putExtra("gczbs",gczbs);
                intent.setClass(this,DataCheckActivity.class);
                startActivity(intent);
                break;
        }
    }
}
