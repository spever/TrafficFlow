package com.subzero.trafficflow.fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioGroup;

import com.subzero.trafficflow.R;

/**
 * 指标页面
 * Created by hui on 2016/3/10.
 */
public class LabelFragment extends BaseFragment {

    private View v;
    private RadioGroup rg_group;
    private MinutesFragment mMinuteFragment;
    private HourFragment mHourFragment;
    private DayFragment mDayFragment;
    private MonthFragment mMonthFragment;
    private YearFragment mYearFragment;

    @Override
    protected View initView() {
        v = View.inflate(getActivity(), R.layout.fragment_label, null);
        setDefaultFragment();
        initRadioGroup();

        return v;
    }



    private void setDefaultFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        mMinuteFragment = new MinutesFragment();
        transaction.replace(R.id.id_label_container, mMinuteFragment);
        transaction.commit();
    }

    private void initRadioGroup() {
        rg_group = (RadioGroup) v.findViewById(R.id.rg_group);
        rg_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                switch (checkedId) {
                    case R.id.id_minutes:
                        if (mMinuteFragment == null) {
                            mMinuteFragment = new MinutesFragment();
                        }
                        transaction.replace(R.id.id_label_container, mMinuteFragment);
                        break;

                    case R.id.id_hour:
                        if (mHourFragment == null) {
                            mHourFragment = new HourFragment();
                        }
                        transaction.replace(R.id.id_label_container, mHourFragment);
                        break;

                    case R.id.id_day:
                        if (mDayFragment == null) {
                            mDayFragment = new DayFragment();
                        }
                        transaction.replace(R.id.id_label_container, mDayFragment);
                        break;
                    case R.id.id_month:
                        if (mMonthFragment == null) {
                            mMonthFragment = new MonthFragment();
                        }
                        transaction.replace(R.id.id_label_container, mMonthFragment);
                        break;
                    case R.id.id_year:
                        if (mYearFragment == null) {
                            mYearFragment = new YearFragment();
                        }
                        transaction.replace(R.id.id_label_container, mYearFragment);
                        break;

                    default:
                        break;
                }

                transaction.commit();
            }

        });


    }
}
