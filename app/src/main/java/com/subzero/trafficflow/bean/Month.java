package com.subzero.trafficflow.bean;

/**
 * Created by hui on 2016/3/17.
 */
public class Month {
    private int MONTH;

    private int JDC_DL;

    private int KC_DL;

    private int HC_DL;

    private String JDC_CS;

    private double YJD;

    private String YDDJ;

    public void setMONTH(int MONTH) {
        this.MONTH = MONTH;
    }

    public int getMONTH() {
        return this.MONTH;
    }

    public void setJDC_DL(int JDC_DL) {
        this.JDC_DL = JDC_DL;
    }

    public int getJDC_DL() {
        return this.JDC_DL;
    }

    public void setKC_DL(int KC_DL) {
        this.KC_DL = KC_DL;
    }

    public int getKC_DL() {
        return this.KC_DL;
    }

    public void setHC_DL(int HC_DL) {
        this.HC_DL = HC_DL;
    }

    public int getHC_DL() {
        return this.HC_DL;
    }

    public void setJDC_CS(String JDC_CS) {
        this.JDC_CS = JDC_CS;
    }

    public String getJDC_CS() {
        return this.JDC_CS;
    }

    public void setYJD(double YJD) {
        this.YJD = YJD;
    }

    public double getYJD() {
        return this.YJD;
    }

    public void setYDDJ(String YDDJ) {
        this.YDDJ = YDDJ;
    }

    public String getYDDJ() {
        return this.YDDJ;
    }

    ;
}
