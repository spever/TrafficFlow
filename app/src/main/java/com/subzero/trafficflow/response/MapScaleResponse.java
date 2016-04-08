package com.subzero.trafficflow.response;

import com.subzero.trafficflow.bean.MapScale;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by hasee on 2016/4/1.
 */
public class MapScaleResponse implements Serializable {
    private boolean success;
    private int length;
    private ArrayList<MapScale> data;

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

    public ArrayList<MapScale> getData() {
        return data;
    }

    public void setData(ArrayList<MapScale> data) {
        this.data = data;
    }
}
