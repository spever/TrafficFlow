package com.subzero.trafficflow.response;

import com.subzero.trafficflow.bean.MinutesCheck;

import java.io.Serializable;

/**
 * Created by hasee on 2016/4/1.
 */
public class MinutesCheckResponse implements Serializable {
    private boolean success;
    private MinutesCheck data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public MinutesCheck getData() {
        return data;
    }

    public void setData(MinutesCheck data) {
        this.data = data;
    }
}
