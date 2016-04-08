package com.subzero.trafficflow.response;

import com.subzero.trafficflow.bean.Device;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by hasee on 2016/3/31.
 */
public class DeviceResponse implements Serializable {
    private boolean success;

    private int length;

    private ArrayList<Device> data;

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

    public ArrayList<Device> getData() {
        return data;
    }

    public void setData(ArrayList<Device> data) {
        this.data = data;
    }
}
