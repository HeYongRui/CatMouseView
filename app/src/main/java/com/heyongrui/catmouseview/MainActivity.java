package com.heyongrui.catmouseview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.heyongrui.catmouseview.library.CatMouseDialog;
import com.heyongrui.catmouseview.library.CatMouseView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CatMouseView loadingview = findViewById(R.id.loadingview1);
        LinearLayout group = findViewById(R.id.loadingview2);
        findViewById(R.id.button1).setOnClickListener(view -> {
            CatMouseDialog loadingDialog = new CatMouseDialog(MainActivity.this);
            loadingDialog.show();
        });
        findViewById(R.id.button2).setOnClickListener(view -> {
            boolean running = loadingview.isRunning();
            if (running) {
                loadingview.stopAnim();
            } else {
                loadingview.startAnim();
            }
        });
        findViewById(R.id.button3).setOnClickListener(view -> {
            group.removeAllViews();
            CatMouseView catMouseView = creatDynamic();
            group.addView(catMouseView);
            catMouseView.startAnim();
        });
    }

    private CatMouseView creatDynamic() {
        CatMouseView catMouseView = new CatMouseView(this);
        catMouseView.setBgFilletRadius(30);
        catMouseView.setAnimDuration(3000);
        catMouseView.setBgColor(Color.MAGENTA);
        catMouseView.setIsShowGraduallyText(true);
        return catMouseView;
    }
}
