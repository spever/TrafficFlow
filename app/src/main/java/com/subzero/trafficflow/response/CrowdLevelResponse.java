package com.subzero.trafficflow.response;

import com.subzero.trafficflow.bean.CrowdLevel;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by hasee on 2016/3/25.
 */
public class CrowdLevelResponse implements Serializable {
    private boolean success;
    private int length;
    private ArrayList<CrowdLevel> data;

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

    public ArrayList<CrowdLevel> getData() {
        return data;
    }

    public void setData(ArrayList<CrowdLevel> data) {
        this.data = data;
    }
}
