package com.subzero.trafficflow.bean;

/**
 * Created by hasee on 2016/3/25.
 */
public class HcCar {
    private String GCZBS;

    private String GCZBH;

    private String GCZMC;

    private String YDDJ;

    private int CONNECT_FLAG;

    private String LATITUDE;

    private String LONGITUDE;

    private int KC_DL;

    public void setGCZBS(String GCZBS) {
        this.GCZBS = GCZBS;
    }

    public String getGCZBS() {
        return this.GCZBS;
    }

    public void setGCZBH(String GCZBH) {
        this.GCZBH = GCZBH;
    }

    public String getGCZBH() {
        return this.GCZBH;
    }

    public void setGCZMC(String GCZMC) {
        this.GCZMC = GCZMC;
    }

    public String getGCZMC() {
        return this.GCZMC;
    }

    public void setYDDJ(String YDDJ) {
        this.YDDJ = YDDJ;
    }

    public String getYDDJ() {
        return this.YDDJ;
    }

    public void setCONNECT_FLAG(int CONNECT_FLAG) {
        this.CONNECT_FLAG = CONNECT_FLAG;
    }

    public int getCONNECT_FLAG() {
        return this.CONNECT_FLAG;
    }

    public void setLATITUDE(String LATITUDE) {
        this.LATITUDE = LATITUDE;
    }

    public String getLATITUDE() {
        return this.LATITUDE;
    }

    public void setLONGITUDE(String LONGITUDE) {
        this.LONGITUDE = LONGITUDE;
    }

    public String getLONGITUDE() {
        return this.LONGITUDE;
    }

    public void setKC_DL(int KC_DL) {
        this.KC_DL = KC_DL;
    }

    public int getKC_DL() {
        return this.KC_DL;
    }
}
