# textwithpiclistview
仿微信发布朋友圈（当前只支持一张一张上传）

本项目使用了两个自定义空间SubmitTextWithPicView和CameraAlbumPopWindow：

SubmitTextWithPicView的使用方式现在默认是通过xml文件添加，然后在style中创建了SubmitTextWithPicView，其内部有picLimit和hintContent，通过我垃圾的命名应该可以看出他的具体使命
<declare-styleable name="SubmitTextWithPicView">
        <attr name="picLimit" format="integer" />
        <attr name="hintContent" format="string" />
    </declare-styleable>
    
使用方式：
<com.zoro.SubmitTextWithPicView
                android:id="@+id/stwp_View"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_marginLeft="16dp"
                android:layout_marginTop="16dp"
                android:layout_marginRight="16dp"
                app:picLimit="9"
                app:hintContent="请描述问题"/>
                
 代码内的使用请看注释，蛮人机的
 
 
 
 CameraAlbumPopWindow的使用稍微麻烦一点，需要在AndroidManifest设置权限获取功能Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE
, Manifest.permission.CAMERA这三个，因为内部嵌套了自动获取权限的功能

具体使用代码：
  cameraAlbumPopWindow = new CameraAlbumPopWindow(MainActivity.this, new CameraAlbumPopWindow.CameraAlbumListener() {
            @Override
            public void successCallBack(final File a) {
                String absolutePath = a.getAbsolutePath();
            }
        });
      
      
 这边还需要通过activity之间的回调处理数据：
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
    本项目只适合在activity，如果需要通过fragment进行唤醒   那你需要自己更改context的传入值了  
  cameraAlbumPopWindow = new CameraAlbumPopWindow(MainActivity.this, new CameraAlbumPopWindow.CameraAlbumListener() {xuyao
  cameraAlbumPopWindow = new CameraAlbumPopWindow(MainActivity.this, new CameraAlbumPopWindow.CameraAlbumListener() {
