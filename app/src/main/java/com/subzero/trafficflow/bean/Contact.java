package com.subzero.trafficflow.bean;

/**
 * Created by hui on 2016/3/14.
 */
public class Contact {
//    USER_ID	String	用户唯一标识
//    USER_NAME	String	用户名
//    SEX	String	性别
//    MAIL	String	邮箱
//    MOBILE	String	手机号
//    GLJGBS	String	管理机构标识
//    GLJGBH	String	管理机构编号
//    GLJGMC	String	管理机构名称

    private String USER_ID;
    private String USER_NAME;
    private String SEX;
    private String MAIL;
    private String MOBILE;
    private String GLJGBS;
    private String GLJGBH;
    private String GLJGMC;

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    public String getUSER_NAME() {
        return USER_NAME;
    }

    public void setUSER_NAME(String USER_NAME) {
        this.USER_NAME = USER_NAME;
    }

    public String getSEX() {
        return SEX;
    }

    public void setSEX(String SEX) {
        this.SEX = SEX;
    }

    public String getMAIL() {
        return MAIL;
    }

    public void setMAIL(String MAIL) {
        this.MAIL = MAIL;
    }

    public String getMOBILE() {
        return MOBILE;
    }

    public void setMOBILE(String MOBILE) {
        this.MOBILE = MOBILE;
    }

    public String getGLJGBS() {
        return GLJGBS;
    }

    public void setGLJGBS(String GLJGBS) {
        this.GLJGBS = GLJGBS;
    }

    public String getGLJGBH() {
        return GLJGBH;
    }

    public void setGLJGBH(String GLJGBH) {
        this.GLJGBH = GLJGBH;
    }

    public String getGLJGMC() {
        return GLJGMC;
    }

    public void setGLJGMC(String GLJGMC) {
        this.GLJGMC = GLJGMC;
    }
}
