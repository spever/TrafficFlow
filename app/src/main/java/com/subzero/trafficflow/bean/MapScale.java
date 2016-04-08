package com.subzero.trafficflow.bean;

/**
 * Created by hasee on 2016/4/1.
 */
public class MapScale {
    private String GCZBS;

    private String GCZBH;

    private String GCZMC;

    private int JDC_DL;

    private double JDC_CS;

    private String YDDJ;

    private String LATITUDE;

    private String LONGITUDE;

    private int CONNECT_FLAG;

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

    public void setJDC_DL(int JDC_DL) {
        this.JDC_DL = JDC_DL;
    }

    public int getJDC_DL() {
        return this.JDC_DL;
    }

    public void setJDC_CS(double JDC_CS) {
        this.JDC_CS = JDC_CS;
    }

    public double getJDC_CS() {
        return this.JDC_CS;
    }

    public void setYDDJ(String YDDJ) {
        this.YDDJ = YDDJ;
    }

    public String getYDDJ() {
        return this.YDDJ;
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

    public void setCONNECT_FLAG(int CONNECT_FLAG) {
        this.CONNECT_FLAG = CONNECT_FLAG;
    }

    public int getCONNECT_FLAG() {
        return this.CONNECT_FLAG;
    }
}
