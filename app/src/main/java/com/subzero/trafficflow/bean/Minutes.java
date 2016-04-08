package com.subzero.trafficflow.bean;

/**
 * Created by hui on 2016/3/17.
 */
public class Minutes {
    //    HOUR	Integer	小时
//    MINUTE	Integer	分钟
//    SJXH	Integer	时间序号 从00:00至23:55之间每5分钟为一时间点。此处代表时间点的排序值。取值为1至288。1对应00:00，288对应23:55
//    JDC_DL	Integer	机动车当量
//    KC_DL	Integer	客车当量
//    HC_DL	Integer	货车当量
//    JDC_CS	Number	机动车速度
//    YDDJ	String	拥堵等级
    private int HOUR;

    private int MINUTE;

    private int SJXH;

    private int JDC_DL;

    private int KC_DL;

    private int HC_DL;

    private double JDC_CS;

    private String YDDJ;

    public void setHOUR(int HOUR) {
        this.HOUR = HOUR;
    }

    public int getHOUR() {
        return this.HOUR;
    }

    public void setMINUTE(int MINUTE) {
        this.MINUTE = MINUTE;
    }

    public int getMINUTE() {
        return this.MINUTE;
    }

    public void setSJXH(int SJXH) {
        this.SJXH = SJXH;
    }

    public int getSJXH() {
        return this.SJXH;
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

    public void setJDC_CS(int JDC_CS) {
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

}
