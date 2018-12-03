# CatMouseView [![](https://img.shields.io/badge/platform-android-brightgreen.svg)](https://developer.android.com/index.html)  [![API](https://img.shields.io/badge/API-14%2B-blue.svg?style=flat)](https://android-arsenal.com/api?level=14)  [![RxTool](https://jitpack.io/v/vondear/RxTool.svg)](https://jitpack.io/#HeYongRui/CatMouseView)  [![Twitter](https://img.shields.io/badge/Gradle-3.1.4-brightgreen.svg)](https://github.com/jiangzehui/polygonsview)

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

[Javadoc Available Here](http://prolificinteractive.github.io/material-calendarview/)

Example:

```xml
<com.prolificinteractive.materialcalendarview.MaterialCalendarView
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/calendarView"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:mcv_showOtherDates="all"
    app:mcv_selectionColor="#00F"
    />
```
