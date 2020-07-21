package com.chenganrt.smartSupervision.business.personal;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.chenganrt.smartSupervision.R;
import com.chenganrt.smartSupervision.business.app.BaseActivity;
import com.chenganrt.smartSupervision.business.login.UserSP;
import com.chenganrt.smartSupervision.common.okhttputil.CallBackUtil;
import com.chenganrt.smartSupervision.common.okhttputil.HttpConstant;
import com.chenganrt.smartSupervision.common.okhttputil.OkhttpUtil;
import com.chenganrt.smartSupervision.model.response.UserDateBeanParser;
import com.chenganrt.smartSupervision.model.response.UserDdtaBean;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

import static android.graphics.Color.WHITE;


public class UserDataActivity extends BaseActivity {
    private TextView tv_phone;
    private TextView tv_company;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userdata);
        initTitleBar();
        initView();
        initData();
    }
    private void initTitleBar() {
        layout_head.setVisibility(View.VISIBLE);
        layout_head.setBackgroundColor(WHITE);
        tv_title.setText("用户资料");
        tv_title.setTextColor(Color.BLACK);
        btn_left.setBackgroundResource(R.drawable.arrow_back);
    }
    private void initView() {
        tv_phone=findViewById(R.id.tv_phone);
        tv_company=findViewById(R.id.tv_company);

    }

    private void initData() {
        String url = HttpConstant.baseUrl + "/user/info";
        Map<String, String> map = new HashMap<>();
        map.put("id", UserSP.getInstance().getUserId(this));
        OkhttpUtil.okHttpGet(this,url,map,new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {
            }
            @Override
            public void onResponse(String response) {
                try {
                    UserDdtaBean userDdtaBean = UserDateBeanParser.parse(response);
                    tv_phone.setText(userDdtaBean.getMobile_phone());
                    tv_company.setText("");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
