package com.chenganrt.smartSupervision.business.electronic.mainctrl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.chenganrt.smartSupervision.business.electronic.activity.BaseActivity;


public class AssitantActivity extends BaseActivity {
    public static void excuteProtocol(Context ctx, String protocol) {
        Intent intent = new Intent(ctx, AssitantActivity.class);
        intent.putExtra("protocol", protocol);
        if (!(ctx instanceof Activity)) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        ctx.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String protocal = getIntent().getStringExtra("protocol");
        ProtocolUtilV2.parseFftBannerUrl(this, protocal);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        });
    }
}
