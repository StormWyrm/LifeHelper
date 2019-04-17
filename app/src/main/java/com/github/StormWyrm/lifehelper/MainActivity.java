package com.github.StormWyrm.lifehelper;

import android.os.Bundle;
import android.view.View;

import com.github.StormWyrm.library.arounter.ARouterConstant;
import com.github.StormWyrm.library.arounter.ARouterUtil;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_picture)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ARouterUtil.navigation(ARouterConstant.ACTIVITY_PICTURE);
                    }
                });

        findViewById(R.id.btn_weather)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ARouterUtil.navigation(ARouterConstant.ACTIVITY_WEATHER);
                    }
                });
    }
}
