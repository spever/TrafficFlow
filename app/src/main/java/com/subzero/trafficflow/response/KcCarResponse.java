package com.subzero.trafficflow.response;

import com.subzero.trafficflow.bean.KcCar;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by hasee on 2016/3/25.
 */
public class KcCarResponse implements Serializable {
    private ArrayList<KcCar> data;
    private boolean success;
    private int length;

    public ArrayList<KcCar> getData() {
        return data;
    }

    public void setData(ArrayList<KcCar> data) {
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
