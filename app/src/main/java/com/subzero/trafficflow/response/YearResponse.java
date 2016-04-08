package com.subzero.trafficflow.response;

import com.subzero.trafficflow.bean.Year;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by hasee on 2016/3/30.
 */
public class YearResponse implements Serializable {
    private boolean success;

    private int length;

    private ArrayList<Year> data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public ArrayList<Year> getData() {
        return data;
    }

    public void setData(ArrayList<Year> data) {
        this.data = data;
    }
}
