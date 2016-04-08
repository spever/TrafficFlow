package com.subzero.trafficflow.response;

import com.subzero.trafficflow.bean.Day;
import com.subzero.trafficflow.bean.Hour;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by hasee on 2016/3/29.
 */
public class DayResponse implements Serializable {
    private boolean success;

    private int length;

    private ArrayList<Day> data;

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getLength() {
        return this.length;
    }

    public void setData(ArrayList<Day> data) {
        this.data = data;
    }

    public ArrayList<Day> getData() {
        return this.data;
    }


}
