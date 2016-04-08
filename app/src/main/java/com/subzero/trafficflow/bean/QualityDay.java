package com.subzero.trafficflow.bean;

/**
 * Created by hasee on 2016/3/31.
 */
public class QualityDay {
    private String SBSBM;

    private String MAKER_NAME;

    private String MODEL_CODE;

    private double PCT_FULL;

    private double PCT_ERROR;

    private double PCT_TIME;

    private String QUALITY;

    public void setSBSBM(String SBSBM) {
        this.SBSBM = SBSBM;
    }

    public String getSBSBM() {
        return this.SBSBM;
    }

    public void setMAKER_NAME(String MAKER_NAME) {
        this.MAKER_NAME = MAKER_NAME;
    }

    public String getMAKER_NAME() {
        return this.MAKER_NAME;
    }

    public void setMODEL_CODE(String MODEL_CODE) {
        this.MODEL_CODE = MODEL_CODE;
    }

    public String getMODEL_CODE() {
        return this.MODEL_CODE;
    }

    public void setPCT_FULL(double PCT_FULL) {
        this.PCT_FULL = PCT_FULL;
    }

    public double getPCT_FULL() {
        return this.PCT_FULL;
    }

    public void setPCT_ERROR(double PCT_ERROR) {
        this.PCT_ERROR = PCT_ERROR;
    }

    public double getPCT_ERROR() {
        return this.PCT_ERROR;
    }

    public void setPCT_TIME(double PCT_TIME) {
        this.PCT_TIME = PCT_TIME;
    }

    public double getPCT_TIME() {
        return this.PCT_TIME;
    }

    public void setQUALITY(String QUALITY) {
        this.QUALITY = QUALITY;
    }

    public String getQUALITY() {
        return this.QUALITY;
    }
}
