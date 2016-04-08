package com.subzero.trafficflow.response;

import com.subzero.trafficflow.bean.HcCar;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by hasee on 2016/3/25.
 */
public class HcCarResponse implements Serializable {

    private ArrayList<HcCar> data;
    private boolean success;
    private int length;

    public ArrayList<HcCar> getData() {
        return data;
    }

    public void setData(ArrayList<HcCar> data) {
        this.data = data;
    }

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
}
