package com.subzero.trafficflow.SharePreference;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONObject;

/**
 * Created by bjjg on 2015/3/31.
 */
public class PreferenceService {

    private Context context;
    private SharedPreferences sp ;
    private SharedPreferences.Editor editor = null;


    public PreferenceService(Context context){
        this.context = context;
        initPreferences();
    }

    private void initPreferences(){
        sp = context.getSharedPreferences("Setting", Context.MODE_PRIVATE);
        editor = sp.edit();
    }
    public void saveBooleanValue(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public boolean getBooleanValue(String key, boolean defValue) {
        System.out.println("key ------------->" + key);
        return sp.getBoolean(key, defValue);
    }

    public void saveIntValue(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

    public int getIntValue(String key, int defValue) {
        return sp.getInt(key, defValue);
    }

    public void saveDoubleValue(String key, double value) {
        editor.putFloat(key, (float) value);
        editor.commit();
    }

    public double getDoubleValue(String key, double defValue) {
        return sp.getFloat(key, (float) defValue);
    }

    public void saveStringValue(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public String getStringValue(String key) {
        return sp.getString(key, "");
    }
    public String getStringValue(String key,String value) {
        return sp.getString(key, value);
    }
}
