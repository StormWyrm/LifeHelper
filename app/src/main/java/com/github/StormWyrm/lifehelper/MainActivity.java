package com.github.StormWyrm.lifehelper;

import android.os.Bundle;
import android.view.View;

import com.github.StormWyrm.library.arounter.ARouterConstant;
import com.github.StormWyrm.library.arounter.ARouterUtils;
import com.github.StormWyrm.lifehelper.R;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_activity_main);

        findViewById(R.id.btn_picture)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ARouterUtils.navigation(ARouterConstant.ACTIVITY_PICTURE,
                                MainActivity.this,
                                ARouterUtils.callback);
                    }
                });

        findViewById(R.id.btn_weather)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ARouterUtils.navigation(
                                ARouterConstant.ACTIVITY_WEATHER,
                                MainActivity.this,
                                ARouterUtils.callback);
                    }
                });


        findViewById(R.id.btn_gank)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ARouterUtils.navigation(ARouterConstant.ACTIVITY_GANK,
                                MainActivity.this,
                                ARouterUtils.callback);
                    }
                });
    }
}