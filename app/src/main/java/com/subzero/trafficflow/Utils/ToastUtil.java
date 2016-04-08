package com.subzero.trafficflow.Utils;

import android.widget.Toast;

import com.subzero.trafficflow.manager.MyApplication;

/**
 * @author luo
 *         管理Toast工具类
 * @author hackangel
 */
public class ToastUtil {

    private static Toast toast;
    public static boolean isDebug = true;

    /**
     * 可以连续弹吐司，不用等到上个吐司消失
     *
     * @param text
     */
    public static void showToast(String text) {
        if (isDebug) {
            if (toast == null) {
                toast = Toast.makeText(MyApplication.getContext(), text, Toast.LENGTH_SHORT);
            }

            toast.setText(text);
            toast.show();
        }

    }

    public static void showNormalToast(String text) {

        if (toast == null) {
            toast = Toast.makeText(MyApplication.getContext(), text, Toast.LENGTH_SHORT);
        }

        toast.setText(text);
        toast.show();
    }
}
