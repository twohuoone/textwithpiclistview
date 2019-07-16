package com.zoro.textwithpiclistview;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zoro
 * Created on 2019/7/16
 * description:
 */
public class CheckPermissionUtils {

    private static CheckPermissionUtils instance;

    private static List<String> permissionList;

    private int requestCode;

    private PermissionCallBack permissionCallBack;

    private PermissionCallBack permissionErrorCallBack;


    private CheckPermissionUtils() {
    }

    public static CheckPermissionUtils getInstance() {
        if (null == instance) {
            instance = new CheckPermissionUtils();
            permissionList = new ArrayList<>();
        }
        return instance;
    }


    /**
     * 申请权限
     *
     * @param context
     * @param requestCode
     * @param permission
     */
    public void initPermission(Context context, int requestCode, PermissionCallBack permissionCallBack, String... permission) {
        this.requestCode = requestCode;
        this.permissionCallBack = permissionCallBack;
        permissionList.clear();
        if (Build.VERSION.SDK_INT >= 23) {
            for (int i = 0; i < permission.length; i++) {
                if (ContextCompat.checkSelfPermission(context, permission[i]) != PackageManager.PERMISSION_GRANTED) {
                    permissionList.add(permission[i]);
                }
            }
            if (permissionList.size() > 0) {
                String[] permissionArray = new String[permissionList.size()];
                for (int i = 0; i < permissionList.size(); i++) {
                    permissionArray[i] = permissionList.get(i);
                }
                ActivityCompat.requestPermissions((Activity) context, permissionArray, requestCode);
            } else {
                permissionCallBack.permissionNext();
            }
        } else {
            permissionCallBack.permissionNext();
        }
    }

    /**
     * 申请权限
     *
     * @param context
     * @param requestCode
     * @param permission
     */
    public void initPermissionCamera(Context context, int requestCode, PermissionCallBack permissionCallBack, PermissionCallBack permissionErrorCallBack, String... permission) {
        this.requestCode = requestCode;
        this.permissionCallBack = permissionCallBack;
        this.permissionErrorCallBack = permissionErrorCallBack;
        permissionList.clear();
        if (Build.VERSION.SDK_INT >= 23) {
            for (int i = 0; i < permission.length; i++) {
                if (ContextCompat.checkSelfPermission(context, permission[i]) != PackageManager.PERMISSION_GRANTED) {
                    permissionList.add(permission[i]);
                }
            }
            if (permissionList.size() > 0) {
                String[] permissionArray = new String[permissionList.size()];
                for (int i = 0; i < permissionList.size(); i++) {
                    permissionArray[i] = permissionList.get(i);
                }
                ActivityCompat.requestPermissions((Activity) context, permissionArray, requestCode);
            } else {
                permissionCallBack.permissionNext();
            }
        } else {
            permissionCallBack.permissionNext();
        }
    }


    /**
     * 申请权限的回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public void permissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (this.requestCode == requestCode) {
            if (grantResults.length > 0) {
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    } else {

                        ToastUtils.showShort("权限被拒绝，可能会影响某些功能使用");
                    }
                }
                if (null != permissionCallBack) {
                    permissionCallBack.permissionNext();
                }
            } else {
                ToastUtils.showShort("权限被拒绝，可能会影响某些功能使用");
            }
        } else {
            ToastUtils.showShort("请检查请求码是否一致");
        }
    }

    /**
     * 申请权限的回调，需要全部满足
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public void permissionsResultAnd(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (this.requestCode == requestCode) {
            if (grantResults.length > 0) {
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    } else {
                        ToastUtils.showShort("权限被拒绝，可能会影响某些功能使用");
                        return;
                    }
                }
                if (null != permissionCallBack) {
                    permissionCallBack.permissionNext();
                }
            } else {
                ToastUtils.showShort("权限被拒绝，可能会影响某些功能使用");
            }
        } else {
            ToastUtils.showShort("请检查请求码是否一致");
        }
    }

    /**
     * 申请权限的回调，不用全部满足
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public void permissionsResultOr(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (this.requestCode == requestCode) {
            if (grantResults.length > 0) {
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    } else {
                        ToastUtils.showShort("权限被拒绝，可能会影响某些功能使用");
                    }
                }
                if (null != permissionCallBack) {
                    permissionCallBack.permissionNext();
                }
            } else {
                ToastUtils.showShort("权限被拒绝，可能会影响某些功能使用");
            }
        } else {
            ToastUtils.showShort("请检查请求码是否一致");
        }
    }

    /**
     * 申请权限的回调，如果没有全部通过，返回错误callback
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public void permissionsResultCamera(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean isRefuse = true;
        if (this.requestCode == requestCode) {
            if (grantResults.length > 0) {
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    } else {
                        isRefuse = false;
                        ToastUtils.showShort("权限被拒绝，可能会影响某些功能使用");
                    }
                }
                if (isRefuse) {
                    permissionCallBack.permissionNext();
                } else {
                    permissionErrorCallBack.permissionNext();
                }
            } else {
                ToastUtils.showShort("权限被拒绝，可能会影响某些功能使用");
            }
        } else {
            ToastUtils.showShort("请检查请求码是否一致");
        }
    }
}

