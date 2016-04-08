package com.subzero.trafficflow.response;

import com.subzero.trafficflow.bean.Speed;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by hasee on 2016/3/25.
 */
public class SpeedResponse implements Serializable {
    private boolean success;
    private int length;
    private ArrayList<Speed> data;

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

    public ArrayList<Speed> getData() {
        return data;
    }

    public void setData(ArrayList<Speed> data) {
        this.data = data;
    }
}
