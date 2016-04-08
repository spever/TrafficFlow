package com.subzero.trafficflow.bean;

/**
 * Created by hui on 2016/3/11.
 */
public class Device {
    public String SBSBM;  //设备识别码
    public String MAKER_NAME;  //设备厂商名称
    public String MODEL_CODE;  //设备型号代码

    public String getSBSBM() {
        return SBSBM;
    }

    public void setSBSBM(String SBSBM) {
        this.SBSBM = SBSBM;
    }

    public String getMAKER_NAME() {
        return MAKER_NAME;
    }

    public void setMAKER_NAME(String MAKER_NAME) {
        this.MAKER_NAME = MAKER_NAME;
    }

    public String getMODEL_CODE() {
        return MODEL_CODE;
    }

    public void setMODEL_CODE(String MODEL_CODE) {
        this.MODEL_CODE = MODEL_CODE;
    }
}
