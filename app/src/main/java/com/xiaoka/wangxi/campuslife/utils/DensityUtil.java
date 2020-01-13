package com.xiaoka.wangxi.campuslife.utils;

/**
 * Created by liuzihao on 2017/11/15.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;

import com.xiaoka.wangxi.campuslife.XApp;

public class DensityUtil {

    @Deprecated
    public static int dip2px(Context paramContext, float paramFloat) {
        return (int) (0.5F + paramFloat
                * paramContext.getResources().getDisplayMetrics().density);
    }

    @Deprecated
    public static int px2dip(Context context, float paramFloat) {
        return (int) (0.5F + paramFloat
                / context.getResources().getDisplayMetrics().density);
    }

    @Deprecated
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    @Deprecated
    public static int px2sp(Context context, float value) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (value / fontScale + 0.5f);
    }

    public static int dip2px(float density, float value) {
        return (int) (0.5F + value * density);
    }

    public static int px2dip(float density, float value) {
        return (int) (0.5F + value / density);
    }

    public static int sp2px(float scaledDensity, float value) {
        return (int) (0.5F + value * scaledDensity);
    }

    public static int px2sp(float fontScale /** scaledDensity */
            , float value) {
        return (int) (value / fontScale + 0.5f);


    }

    public static final int getStatusHeighByDensity(Context context) {
        int h = 38;
        int density = context.getResources().getDisplayMetrics().densityDpi;

        switch (density) {
            case 120:
                h = 19;
                break;
            case 160:
                h = 25;
                break;
            case 240:
                h = 38;
                break;
            case 320:
                h = 50;
                break;
            case 400:
                h = 63;
                break;
            case 480:
                h = 75;
                break;
            default:
                break;
        }

        return h;
    }

    private static int displayWidth, displayHeight;

    private static void initDisplay(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((WindowManager) context.getApplicationContext().getSystemService(
                Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(dm);

        displayWidth = dm.widthPixels;

        displayHeight = dm.heightPixels;
    }


    public static final int getDisplayWidth(Context context) {
        if (displayWidth == 0) {
            initDisplay(context);
        }
        return displayWidth;
    }


    public static final int getDisplayHeight(Context context) {
        if (displayHeight == 0) {
            initDisplay(context);
        }
        return displayHeight;
    }

    public static int dp2px(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    public static int dp2px(Context context, float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    public static int px2dp(Context context, int px) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, px, context.getResources().getDisplayMetrics());
    }

    public static int px2sp(Context context, int px) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, px, context.getResources().getDisplayMetrics());
    }

    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    public static boolean isNavigationBarExist = false;

    /**
     * 判断是否显示了导航栏
     *
     * @param activity
     * @return
     */
    public static void isShowNavBar(Activity activity) {
        if (null == activity) {
            isNavigationBarExist = false;
        }
        /**
         * 获取应用区域高度
         */
        Rect outRect1 = new Rect();
        try {
            activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect1);
        } catch (ClassCastException e) {
            e.printStackTrace();
            isNavigationBarExist = false;
        }
        int activityHeight = outRect1.height();
        /**
         * 获取状态栏高度
         */
        int statuBarHeight = getStatusBarHeight();
        /**
         * 屏幕物理高度 减去 状态栏高度
         */
        int remainHeight = getRealHeight() - statuBarHeight - getNavigationBarHeight();
        /**
         * 剩余高度跟应用区域高度相等 说明导航栏显示 否则相反
         */
        if (activityHeight == remainHeight) {
            isNavigationBarExist = true;
        } else {
            isNavigationBarExist = false;
        }

    }

    /**
     * 获取状态栏高度
     *
     * @return
     */
    public static int getStatusBarHeight() {
        int result = 0;
        int resourceId = XApp.getInstance().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = XApp.getInstance().getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 活动屏幕信息
     */
    private static WindowManager wm;

    /**
     * 获取真实屏幕高度
     *
     * @return
     */
    public static int getRealHeight() {
        if (null == wm) {
            wm = (WindowManager)
                    XApp.getInstance().getSystemService(Context.WINDOW_SERVICE);
        }
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.getDefaultDisplay().getRealSize(point);
        } else {
            wm.getDefaultDisplay().getSize(point);
        }
        return point.y;
    }

    public static int getNavigationBarHeight() {
        return XApp.getInstance().getResources().getDimensionPixelSize(XApp.getInstance().getResources().getIdentifier("navigation_bar_height", "dimen", "android"));
    }
}
