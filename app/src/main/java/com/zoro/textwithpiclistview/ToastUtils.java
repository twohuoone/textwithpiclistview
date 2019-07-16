package com.zoro.textwithpiclistview;

import android.content.Context;
import android.widget.Toast;

/**
 * 创建： on 2016/4/16 10:57
 * 备注： Toast工具类
 */
public class ToastUtils {

    private static String oldMsg;
    private static long time;
    private static Context mContext;
    private static ToastUtils instance;

    public static ToastUtils getInstance() {
        if (null == instance) {
            instance = new ToastUtils();
        }
        checkEvn();
        return instance;
    }

    private static void checkEvn() {
        if (null == mContext) {
            return;
        }
    }

    public static void init(Context context) {
        mContext = context;
    }

    /**
     * 短时间显示Toast
     *
     * @param message
     */
    public static void showShort(CharSequence message) {
        if (!message.toString().equals(oldMsg)) { // 当显示的内容不一样时，即断定为不是同一个Toast
            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
            time = System.currentTimeMillis();
        } else {
            // 显示内容一样时，只有间隔时间大于2秒时才显示
            if (System.currentTimeMillis() - time > 2000) {
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                time = System.currentTimeMillis();
            }
        }
        oldMsg = message.toString();
    }


    /**
     * 长时间显示Toast
     *
     * @param message
     */
    public static void showLong(CharSequence message) {
        if (!message.toString().equals(oldMsg)) { // 当显示的内容不一样时，即断定为不是同一个Toast
            Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
            time = System.currentTimeMillis();
        } else {
            // 显示内容一样时，只有间隔时间大于2秒时才显示
            if (System.currentTimeMillis() - time > 2000) {
                Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
                time = System.currentTimeMillis();
            }
        }
        oldMsg = message.toString();
    }
}
