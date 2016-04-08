package com.subzero.trafficflow.response;

import com.subzero.trafficflow.bean.Hour;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hasee on 2016/3/29.
 */
public class HourResponse implements Serializable {
    private boolean success;

    private int length;

    private ArrayList<Hour> data;

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

    public void setData(ArrayList<Hour> data) {
        this.data = data;
    }

    public ArrayList<Hour> getData() {
        return this.data;
    }


}
