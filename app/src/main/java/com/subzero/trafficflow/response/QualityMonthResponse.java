package com.subzero.trafficflow.response;

import com.subzero.trafficflow.bean.QualityMonth;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by hasee on 2016/3/31.
 */
public class QualityMonthResponse implements Serializable {
    private boolean success;
    private int length;
    private ArrayList<QualityMonth> data;

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

    public ArrayList<QualityMonth> getData() {
        return data;
    }

    public void setData(ArrayList<QualityMonth> data) {
        this.data = data;
    }
}
