package com.subzero.trafficflow.activity;

import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.RadioGroup;

import com.subzero.trafficflow.R;
import com.subzero.trafficflow.Utils.ToastUtil;
import com.subzero.trafficflow.fragment.DistrictFragment;
import com.subzero.trafficflow.fragment.MainlineFragment;
import com.subzero.trafficflow.fragment.MineFragment;
import com.subzero.trafficflow.fragment.RouteLineFragment;
import com.subzero.trafficflow.fragment.StationFragment;

/**
 * 主页面
 */
public class MainActivity extends FragmentActivity {

    private Fragment[] mFragments;
    private RadioGroup rgGroup;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private boolean mIsExit = false;

    public static final int STATIONFRAGMENT = 0;
    public static final int ROUTELINEERAGMENT = 1;
    public static final int DISTRICTFRAGMENT = 2;
    public static final int MAINLINEFRAGMENT = 3;
    public static final int MINEFRAGMENT = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFragments = new Fragment[5];
        fragmentManager = getSupportFragmentManager();
        mFragments[0] = new StationFragment();
        mFragments[1] = new RouteLineFragment();
        mFragments[2] = new DistrictFragment();
        mFragments[3] = new MainlineFragment();
        mFragments[4] = new MineFragment();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container, mFragments[0]);
        fragmentTransaction.add(R.id.container, mFragments[1]);
        fragmentTransaction.add(R.id.container, mFragments[2]);
        fragmentTransaction.add(R.id.container, mFragments[3]);
        fragmentTransaction.add(R.id.container, mFragments[4]);
        fragmentTransaction.hide(mFragments[0]).hide(mFragments[1]).hide(mFragments[2]).hide(mFragments[3]).hide(mFragments[4]);

        fragmentTransaction.show(mFragments[STATIONFRAGMENT]).commit();
        setFragmentIndicator();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

    }

    private void setFragmentIndicator() {

        rgGroup = (RadioGroup) findViewById(R.id.rg_group);
        rgGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.id_station:
                        setShowFragment(STATIONFRAGMENT);
                        break;

                    case R.id.id_routeline:
                        setShowFragment(ROUTELINEERAGMENT);
                        break;

                    case R.id.id_district:
                        setShowFragment(DISTRICTFRAGMENT);
                        break;
                    case R.id.id_main_line:
                        setShowFragment(MAINLINEFRAGMENT);
                        break;
                    case R.id.id_mine:
                        setShowFragment(MINEFRAGMENT);
                        break;

                    default:
                        break;
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if (mIsExit == false) {
                mIsExit = true;
                ToastUtil.showToast("再按一次退出交调系统");
                handler.sendEmptyMessageDelayed(0, 3000);
                return true;
            } else {
                finish();
            }
        }
        return true;
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mIsExit = false;
        }
    };

    /**
     * 设置跳转fragment
     *
     * @param fragmentnum
     */
    public void setShowFragment(int fragmentnum) {
        fragmentTransaction = fragmentManager.beginTransaction()
                .hide(mFragments[0]).hide(mFragments[1])
                .hide(mFragments[2]).hide(mFragments[3]).hide(mFragments[4]);
        fragmentTransaction.show(mFragments[fragmentnum]).commit();
    }


}
