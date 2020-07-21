package com.chenganrt.smartSupervision.business.personal;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.android.commonlib.utils.WindowUtil;
import com.chenganrt.smartSupervision.R;
import com.chenganrt.smartSupervision.business.app.BaseActivity;


import static android.graphics.Color.WHITE;

public class AboutMeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aboutme);
        layout_head.setVisibility(View.VISIBLE);
        layout_head.setBackgroundColor(WHITE);
        tv_title.setText("关于");
        tv_title.setTextColor(Color.BLACK);
        btn_left.setBackgroundResource(R.drawable.arrow_back);

    }






}
