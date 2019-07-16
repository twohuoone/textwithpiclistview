package com.zoro.textwithpiclistview;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements PopupWindow.OnDismissListener {

    public static final int TAKE_PHOTO = 1;//启动相机标识
    public static final int SELECT_PHOTO = 2;//启动相册标识

    @BindView(R.id.stwp_View)
    SubmitTextWithPicView stwpView;
    @BindView(R.id.llRootView)
    LinearLayout llRootView;

    private CameraAlbumPopWindow cameraAlbumPopWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ToastUtils.init(this);

        stwpView.setListener(new SubmitTextWithPicView.SubmitTextWithPcListener() {
            @Override
            public void addPhoto() {
                cameraAlbumPopWindow.showAtLocation(llRootView, Gravity.BOTTOM, 0, 0);
                backgroundAlpha(0.5f);
            }
        });
        cameraAlbumPopWindow = new CameraAlbumPopWindow(MainActivity.this, new CameraAlbumPopWindow.CameraAlbumListener() {
            @Override
            public void successCallBack(final File a) {
                String absolutePath = a.getAbsolutePath();
            }
        });
        cameraAlbumPopWindow.setOnDismissListener(this);
    }

    protected void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        this.getWindow().addFlags(2);
        lp.alpha = bgAlpha;
        this.getWindow().setAttributes(lp);
    }

    @Override
    public void onDismiss() {
        backgroundAlpha(1.0f);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            //打开相机后返回
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    /**
                     * 这种方法是通过内存卡的路径进行读取图片，所以的到的图片是拍摄的原图
                     */
                    cameraAlbumPopWindow.displayImage();
                }
                break;
            //打开相册后返回
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK) {
                    //判断手机系统版本号
                    if (Build.VERSION.SDK_INT > 19) {
                        //4.4及以上系统使用这个方法处理图片
                        cameraAlbumPopWindow.handleImgeOnKitKat(data);
                    } else {
                        cameraAlbumPopWindow.handleImageBeforeKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
    }

}
