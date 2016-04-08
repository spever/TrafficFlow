package com.subzero.trafficflow.response;

import com.subzero.trafficflow.bean.Minutes;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by hasee on 2016/3/25.
 */
public class MinutesResponse implements Serializable {

    public boolean success;

    public int length;

    public ArrayList<Minutes> data;

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

    public ArrayList<Minutes> getData() {
        return data;
    }

    public void setData(ArrayList<Minutes> data) {
        this.data = data;
    }
}
