package com.subzero.trafficflow.response;

import com.subzero.trafficflow.bean.StationInfo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by hasee on 2016/4/1.
 */
public class StationInfoResponse implements Serializable {
    private boolean success;
    private StationInfo data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public StationInfo getData() {
        return data;
    }

    public void setData(StationInfo data) {
        this.data = data;
    }
}
