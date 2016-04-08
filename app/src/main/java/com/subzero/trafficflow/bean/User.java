package com.subzero.trafficflow.bean;

/**
 * Created by hui on 2016/3/10.
 */
public class User {

    /**
     * USER_ID : lxkjbjtest
     * USER_NAME : 零下科技部级系统移动端测试账号
     * SEX : 1
     * MAIL : liuxinming_0728@163.com
     * GLJGBS : 00000000000000000000000000000000
     * GLJGBH : 01000000000
     * GLJGMC : 交通运输部
     * PRIVS : 01,02,03,04,05,06
     * token : 0130F4927ACB4691A01A9B330AAA2845
     */

    private String USER_ID;

    private String USER_NAME;

    private String SEX;

    private String MAIL;

    private String GLJGBS;

    private String GLJGBH;

    private String GLJGMC;

    private String PRIVS;

    private String token;

    public void setUSER_ID(String USER_ID){
        this.USER_ID = USER_ID;
    }
    public String getUSER_ID(){
        return this.USER_ID;
    }
    public void setUSER_NAME(String USER_NAME){
        this.USER_NAME = USER_NAME;
    }
    public String getUSER_NAME(){
        return this.USER_NAME;
    }
    public void setSEX(String SEX){
        this.SEX = SEX;
    }
    public String getSEX(){
        return this.SEX;
    }
    public void setMAIL(String MAIL){
        this.MAIL = MAIL;
    }
    public String getMAIL(){
        return this.MAIL;
    }
    public void setGLJGBS(String GLJGBS){
        this.GLJGBS = GLJGBS;
    }
    public String getGLJGBS(){
        return this.GLJGBS;
    }
    public void setGLJGBH(String GLJGBH){
        this.GLJGBH = GLJGBH;
    }
    public String getGLJGBH(){
        return this.GLJGBH;
    }
    public void setGLJGMC(String GLJGMC){
        this.GLJGMC = GLJGMC;
    }
    public String getGLJGMC(){
        return this.GLJGMC;
    }
    public void setPRIVS(String PRIVS){
        this.PRIVS = PRIVS;
    }
    public String getPRIVS(){
        return this.PRIVS;
    }
    public void setToken(String token){
        this.token = token;
    }
    public String getToken(){
        return this.token;
    }
}
