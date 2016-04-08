package com.subzero.trafficflow.Utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.subzero.trafficflow.manager.MyApplication;


/**
 * @author luo
 *         Created by Administrator on 2015/8/28.
 */
public class CommonUtil {
    /**
     * 主线程更新UI
     *
     * @param runnable
     */
    public static void runOnUIThread(Runnable runnable) {
        MyApplication.getHandler().post(runnable);
    }


    /**
     * 获取字符串资源
     *
     * @param resId
     * @return
     */
    public static String getString(int resId) {
        return MyApplication.getContext().getResources().getString(resId);
    }


    /**
     * 获取图片资源
     *
     * @param redId
     * @return
     */
    public static Drawable getDrawable(int redId) {
        return MyApplication.getContext().getResources().getDrawable(redId);
    }


    /**
     * 获取字符串数组资源
     *
     * @param redId
     * @return
     */
    public static String[] getStringArray(int redId) {
        return MyApplication.getContext().getResources().getStringArray(redId);
    }


    /**
     * 获取dp资源，返回值是转换后的像素值
     *
     * @param redId
     * @return
     */
    public static float getDimen(int redId) {
        return MyApplication.getContext().getResources().getDimension(redId);
    }


    /**
     * 根据手机分辨率从dp转成px
     *
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f) - 15;
    }


    /**
     * 获取手机状态栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        java.lang.reflect.Field field = null;
        int x = 0;
        int statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
            return statusBarHeight;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusBarHeight;
    }

    /**
     * 获取手机状态栏高度
     *
     * @return
     */
    public static int getStatusBarHeight() {
        int result = 0;
        int resourceId = MyApplication.getContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = MyApplication.getContext().getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 将指定的childView从他的父节点中移除
     *
     * @param child
     */
    public static void removeSelfFromParent(View child) {
        ViewParent parent = child.getParent();
        if (parent instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) parent;
            //将child从父节点中移除
            group.removeView(child);
        }
    }



}
