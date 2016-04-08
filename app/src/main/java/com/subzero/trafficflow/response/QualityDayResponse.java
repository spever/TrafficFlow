package com.subzero.trafficflow.response;

import com.subzero.trafficflow.bean.QualityDay;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by hasee on 2016/3/31.
 */
public class QualityDayResponse  implements Serializable {
    private boolean success;
    private int length;
    private ArrayList<QualityDay> data;

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

    public ArrayList<QualityDay> getData() {
        return data;
    }

    public void setData(ArrayList<QualityDay> data) {
        this.data = data;
    }
}
