# CatMouseView [![](https://img.shields.io/badge/platform-android-brightgreen.svg)](https://developer.android.com/index.html)  [![API](https://img.shields.io/badge/API-14%2B-blue.svg?style=flat)](https://android-arsenal.com/api?level=14)  [![RxTool](https://jitpack.io/v/HeYongRui/CatMouseView.svg)](https://jitpack.io/#HeYongRui/CatMouseView)  [![Twitter](https://img.shields.io/badge/Gradle-3.1.4-brightgreen.svg)](https://github.com/jiangzehui/polygonsview)

A Material design back port of Android's CalendarView. The goal is to have a Material look
and feel, rather than 100% parity with the platform's implementation.

![image](https://raw.githubusercontent.com/HeYongRui/CatMouseView/master/screenshot/GIF.gif) 

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

1. Add `MaterialCalendarView` into your layouts or view hierarchy.
2. Set a `OnDateSelectedListener` or call `MaterialCalendarView.getSelectedDates()` when you need it.

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
