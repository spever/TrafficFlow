package com.subzero.trafficflow.manager;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Handler;

import com.baidu.mapapi.SDKInitializer;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.subzero.trafficflow.SharePreference.PreferenceDB;
import com.subzero.trafficflow.Utils.LogUtil;
import com.subzero.trafficflow.activity.MainActivity;
import com.subzero.trafficflow.manager.ereza.CustomActivityOnCrash;
import com.subzero.trafficflow.manager.ereza.ErrorActivity;
import com.zhy.http.okhttp.OkHttpUtils;


import org.xutils.x;

import java.util.LinkedList;
import java.util.List;


/**
 * Created by hui on 2016/3/9.
 */
public class MyApplication extends Application {
    private static MyApplication instance;


    PreferenceDB preferenceDB;
    private static Handler mainHandler;
    private static Context context;
    private List<Activity> activityList = new LinkedList<Activity>();

    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(getApplicationContext());
        context = this;
        mainHandler = new Handler();
        initImageLoader(context);
        if (PreferenceDB.getInstance() == null) {
            preferenceDB = new PreferenceDB(this);
        }
        x.Ext.init(this);
        x.Ext.setDebug(true);
        initCrash();

    }

    // 单例模式中获取唯一的MyApplication实例
    public static synchronized MyApplication getInstance() {
        if (null == instance) {
            instance = new MyApplication();
        }
        return instance;
    }

    public static void initImageLoader(Context context) {

        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app

        ImageLoader.getInstance().init(config.build());
    }

    public static Context getContext() {
        return context;
    }

    public static Handler getHandler() {
        return mainHandler;
    }

    // 添加Activity到容器中
    public synchronized void addActivity(Activity activity) {
        if(!activityList.contains(activity)){
            LogUtil.e("sdsdsdsd", "addActivity");
            activityList.add(activity);
        }
    }

    // 删除Activity到容器中
    public synchronized void removeActivity(Activity activity) {
        if(activityList.contains(activity)){
            LogUtil.e("sdsdsdsd","removeActivity");
            activityList.remove(activity);
        }
    }

    // 遍历所有Activity并finish
    public void killAllActivity() {
        for (Activity activity : activityList) {
            LogUtil.e("sdsdsdsd","killAllActivity");
            activity.finish();
        }
    }

    /**版本要求 Android 4.0    api 14*/
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private void initCrash()
    {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.ICE_CREAM_SANDWICH)
        {
            CustomActivityOnCrash.install(this);
            CustomActivityOnCrash.setEnableAppRestart(true);
            CustomActivityOnCrash.setLaunchErrorActivityWhenInBackground(true);
            CustomActivityOnCrash.setShowErrorDetails(true);
            CustomActivityOnCrash.setErrorActivityClass(ErrorActivity.class);
            CustomActivityOnCrash.setRestartActivityClass(MainActivity.class);
        }
    }

}
