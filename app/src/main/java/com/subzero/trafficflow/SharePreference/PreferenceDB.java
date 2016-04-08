package com.subzero.trafficflow.SharePreference;

import android.app.Activity;
import android.content.Context;
import android.service.voice.VoiceInteractionService;

import java.util.ArrayList;

/**
 * Created by li on 2015/9/18.
 */
public class PreferenceDB {

    private Context context;


    private PreferenceService service;

    private static PreferenceDB instance = null;
    private String accesstoken = null;

    private String userid = null;

    private boolean islogin = false;

//    USER_ID,USER_NAME,GLJGBS

    private String USER_ID = null;
    private String USER_NAME = null;
    private String GLJGBS = null;
    private String GLJGMC = null;


    private ArrayList<Activity> activityArrayList;


    public PreferenceDB(Context context) {
        service = new PreferenceService(context);
        activityArrayList = new ArrayList<>();
        instance = this;

    }

    public static PreferenceDB getInstance() {

        return instance;
    }

    public void cleanUser() {


        this.setIslogin(false);
        this.setUser(null);
        this.setUserAccesstoken(null);
        this.setUserId(null);
        this.setGLJGBS(null);
        this.setUserName(null);
        this.setGLJGMC(null);
        this.setMAIL(null);


    }

    public void clearActivity() {
        for (Activity activity : activityArrayList) {
            activity.finish();
        }
    }

    public ArrayList<Activity> getActivityArrayList() {
        return activityArrayList;
    }


    public void setUser(String userstr) {
        service.saveStringValue(PreferenceConstant.USER, userstr);
    }

    public String getUserStr() {
        return service.getStringValue(PreferenceConstant.USER);
    }

    public boolean islogin() {
        return service.getBooleanValue(PreferenceConstant.ISLOGIN, false);
    }


    public void setIslogin(boolean islogin) {
        service.saveBooleanValue(PreferenceConstant.ISLOGIN, islogin);
    }

    public void setUserAccesstoken(String accesstoken) {
        service.saveStringValue(PreferenceConstant.ACCESSTOKEN, accesstoken);
    }

    public String getUserAccesstoken() {
        return service.getStringValue(PreferenceConstant.ACCESSTOKEN);
    }

    public void setUserId(String userid) {
        service.saveStringValue(PreferenceConstant.USER_ID, userid);
    }

    public String getUserid() {
        return service.getStringValue(PreferenceConstant.USER_ID);
    }

    public void setUserName(String username) {
        service.saveStringValue(PreferenceConstant.USER_NAME, username);
    }

    public String getUserName() {
        return service.getStringValue(PreferenceConstant.USER_NAME);
    }

    public void setGLJGBS(String gljgbs) {
        service.saveStringValue(PreferenceConstant.GLJGBS, gljgbs);
    }

    public String getGLJGBS() {
        return service.getStringValue(PreferenceConstant.GLJGBS);
    }

    public void setGLJGMC(String gljgmc) {
        service.saveStringValue(PreferenceConstant.GLJGMC, gljgmc);
    }

    public String getGLJGMC() {
        return service.getStringValue(PreferenceConstant.GLJGMC);
    }

    public void setMAIL(String mail) {
        service.saveStringValue(PreferenceConstant.MAIL, mail);
    }

    public String getMAIL() {
        return service.getStringValue(PreferenceConstant.MAIL);
    }

    public void setSEX(String mail) {
        service.saveStringValue(PreferenceConstant.SEX, mail);
    }

    public String getSEX() {
        return service.getStringValue(PreferenceConstant.SEX);
    }

}
