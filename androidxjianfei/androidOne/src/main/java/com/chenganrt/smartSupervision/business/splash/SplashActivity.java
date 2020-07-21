package com.chenganrt.smartSupervision.business.splash;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chenganrt.smartSupervision.business.app.BaseActivity;
import com.chenganrt.smartSupervision.business.main.MainAppActivity;

public class SplashActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHeadVisibility(View.GONE);
		intoMainPage();
	}
	
	public void intoMainPage(){
		Thread myThread = new Thread() {//创建子线程
			@Override
			public void run() {
				try {
					sleep(500);//使程序休眠一秒
					Intent it = new Intent(getApplicationContext(), MainAppActivity.class);
					startActivity(it);
					finish();//关闭当前活动
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		myThread.start();//启动线程
	}
}
