package com.chenganrt.smartSupervision.business.personal;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.commonlib.okhttp.bean.User;
import com.chenganrt.smartSupervision.R;
import com.chenganrt.smartSupervision.business.app.BaseActivity;
import com.chenganrt.smartSupervision.business.home.RefreshEvent;
import com.chenganrt.smartSupervision.business.login.LoginActivity;
import com.chenganrt.smartSupervision.business.login.UserSP;
import com.chenganrt.smartSupervision.common.okhttputil.CallBackUtil;
import com.chenganrt.smartSupervision.common.okhttputil.HttpConstant;
import com.chenganrt.smartSupervision.common.okhttputil.OkhttpUtil;
import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

import static android.graphics.Color.WHITE;

public class MoreSettingActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout ll_forgetpassword;
    private LinearLayout ll_userdata;
    private TextView tv_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moresetting);
//        StatusBarUtil.setColor(this, WHITE );
        layout_head.setVisibility(View.VISIBLE);
        layout_head.setBackgroundColor(WHITE);
        tv_title.setText("设置");
        tv_title.setTextColor(Color.BLACK);
        btn_left.setBackgroundResource(R.drawable.arrow_back);
        initView();


    }

    private void initView() {
        ll_userdata= (LinearLayout) findViewById(R.id.ll_userdata);
        ll_userdata.setOnClickListener(this);

        ll_forgetpassword= (LinearLayout) findViewById(R.id.ll_forgetpassword);
        ll_forgetpassword.setOnClickListener(this);

        tv_logout= (TextView) findViewById(R.id.tv_logout);
        tv_logout.setOnClickListener(this);
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_userdata:
                Intent intent8 = new Intent(this, UserDataActivity.class);
                startActivity(intent8);
                break;
            case R.id.ll_forgetpassword:
                Intent intent1 = new Intent(this, ForgetPasswordActivity.class);
                startActivity(intent1);
                break;
            case R.id.tv_logout:
                requestLogout();
                break;
        }

    }

    private void requestLogout() {
        String url = HttpConstant.baseUrl + "/user/logout";
        Map<String,String> map=new HashMap<>();
        map.put("id", UserSP.getInstance().getUserId(this));
        OkhttpUtil.okHttpPostJson(this,url,new Gson().toJson(map),new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {
            }
            @Override
            public void onResponse(String response) {
                User user=new User();
                UserSP.getInstance().setUserInfo(MoreSettingActivity.this,user);
               // UserSP.getInstance().setIsRefresh(MoreSettingActivity.this,true);
                EventBus.getDefault().post(new RefreshEvent("position"));
                finish();
            }
        });
    }
//    private void showPop() {
//        View dialogView = LayoutInflater.from(this).inflate(R.layout.buttom_dialog, null);
//        ShareBottomPopupDialog shareBottomPopupDialog = new ShareBottomPopupDialog(this, dialogView);
//        shareBottomPopupDialog.showPopup(ll_mian);
//        bt_cancel = (Button) dialogView.findViewById(R.id.bt_cancel);
//        ll_phone = (LinearLayout) dialogView.findViewById(R.id.ll_phone);
//        View.OnClickListener clickListener = new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                switch (view.getId()) {
//                    case R.id.bt_cancel:
//                        break;
//                    case R.id.ll_phone:
//                        if (ContextCompat.checkSelfPermission(MoreSettingActivity.this, Manifest.
//                                permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                            ActivityCompat.requestPermissions(MoreSettingActivity.this, new
//                                    String[]{Manifest.permission.CALL_PHONE}, 1);
//                        } else {
//                            call();
//                        }
//                        break;
//
//                }
//                shareBottomPopupDialog.dismiss();
//
//            }
//        };
//        ll_phone.setOnClickListener(clickListener);
//        bt_cancel.setOnClickListener(clickListener);
//
//    }
//
//
//    private void call() {
//        try {
//            Intent intent = new Intent(Intent.ACTION_CALL);
//            intent.setData(Uri.parse("tel:13135342156"));
//            startActivity(intent);
//        } catch (SecurityException e) {
//            e.printStackTrace();
//        }
//    }
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions,int[] grantResults) {
//        switch (requestCode) {
//            case 1:
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    call();
//                } else {
//                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
//                }
//                break;
//            default:
//        }
//    }

}
