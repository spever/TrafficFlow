package com.subzero.trafficflow.response;

import com.subzero.trafficflow.bean.User;

import java.io.Serializable;


/**
 * Created by hui on 2016/3/9.
 */
public class UserResponse implements Serializable {

    public boolean success;
    public Integer length;
    public String error;

    public User data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public User getUser() {
        return data;
    }

    public void setUser(User data) {
        this.data = data;
    }
}
