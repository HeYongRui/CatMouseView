package com.heyongrui.catloadingview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.heyongrui.catloadingview.library.CatMouseDialog;
import com.heyongrui.catloadingview.library.CatMouseView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CatMouseView loadingview = findViewById(R.id.loadingview);
        findViewById(R.id.button1).setOnClickListener(view -> {
            CatMouseDialog loadingDialog = new CatMouseDialog(MainActivity.this);
            loadingDialog.show();
        });
        findViewById(R.id.button2).setOnClickListener(view -> {
            if (!loadingview.isRunning()) {
                loadingview.startAnim();
            } else {
                loadingview.stopAnim();
            }
        });
    }
}
