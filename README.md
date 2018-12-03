# CatMouseView [![](https://img.shields.io/badge/platform-android-brightgreen.svg)](https://developer.android.com/index.html)  [![API](https://img.shields.io/badge/API-14%2B-blue.svg?style=flat)](https://android-arsenal.com/api?level=14)  [![RxTool](https://jitpack.io/v/HeYongRui/CatMouseView.svg)](https://jitpack.io/#HeYongRui/CatMouseView)  [![Twitter](https://img.shields.io/badge/Gradle-3.1.4-brightgreen.svg)](https://github.com/jiangzehui/polygonsview)

CatMouseView是一个可与用户交互的有趣的猫鼠动画自定义View，可以用于用户操作等待的loading视图等场景，避免用户等待的无聊。

![image](https://raw.githubusercontent.com/HeYongRui/CatMouseView/master/screenshot/GIF.gif) 

## Demo
[下载 APK-Demo](https://github.com/HeYongRui/CatMouseView/raw/master/apk/app-debug.apk)

## Installation
Step 1. Add the JitPack repository to your build file

```groovy
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```

Step 2. Add the dependency

```groovy
dependencies {
  implementation 'com.github.HeYongRui:CatMouseView:v1.0.0'
}
```

## Usage

Java:
```
 CatMouseView catMouseView = new CatMouseView(this);
 catMouseView.setBgFilletRadius(30);
 catMouseView.setAnimDuration(3000);
 catMouseView.setBgColor(Color.MAGENTA);
 catMouseView.setIsShowGraduallyText(true);
 catMouseView.startAnim();
```
XML
```
 <com.heyongrui.catmouseview.library.CatMouseView
        android:id="@+id/loadingview1"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="@dimen/dp_20"
        app:anim_duration="5000"
        app:bg_color="@color/colorPrimary"
        app:bg_fillet_radius="@dimen/dp_10"
        app:gradually_text="L O A D I N G..."
        app:is_show_gradually_text="false"/>
```
也可以直接使用封装好的Dialog形式
```
 CatMouseDialog loadingDialog = new CatMouseDialog(MainActivity.this);
 loadingDialog.show();
```
## 参考&Thx
感谢[Rogero0o](https://github.com/Rogero0o)提供的思路，我参考[CatLoadingView](https://github.com/Rogero0o/CatLoadingView)，对其进行了优化和一些改动，并加入了用户交互。
