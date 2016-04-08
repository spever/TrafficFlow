package com.subzero.trafficflow.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.subzero.trafficflow.R;
import com.subzero.trafficflow.fragment.RankItemFragment;
import com.subzero.trafficflow.widget.PagerSlidingTab;

import java.util.ArrayList;

/**
 * Created by hui on 2016/3/10.
 */
public class RankActivity extends BaseActivity implements View.OnClickListener {


    public ArrayList<String> tabs = new ArrayList<>();
    private ImageView id_iv_title_back;
    private ImageView id_iv_title_search;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_rank);
        initId();
        initListener();
    }

    private void initListener() {
        id_iv_title_back.setOnClickListener(this);
        id_iv_title_search.setOnClickListener(this);
    }

    private void initId() {
        id_iv_title_back = (ImageView) findViewById(R.id.id_iv_title_back);
        id_iv_title_search = (ImageView) findViewById(R.id.id_iv_title_search);
    }

    @Override
    protected void initData() {


        tabs.add("机动车");
        tabs.add("货车");
        tabs.add("客车");
        tabs.add("拥挤度");
        tabs.add("车速");

        PagerSlidingTab tab = (PagerSlidingTab) findViewById(R.id.id_page_sliding);

        ViewPager id_rank_viewpager = (ViewPager) findViewById(R.id.id_rank_viewpager);

        RankAdapter adapter = new RankAdapter(getSupportFragmentManager());
        id_rank_viewpager.setAdapter(adapter);
        tab.setViewPager(id_rank_viewpager);

    }

    class RankAdapter extends FragmentPagerAdapter {

        public RankAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return RankItemFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return tabs.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabs.get(position);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.id_iv_title_back:
                finish();
                break;
            case R.id.id_iv_title_search:
                break;
        }

    }
}
