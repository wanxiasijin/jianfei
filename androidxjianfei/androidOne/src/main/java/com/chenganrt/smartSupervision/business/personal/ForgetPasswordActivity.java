package com.chenganrt.smartSupervision.business.personal;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.chenganrt.smartSupervision.R;
import com.chenganrt.smartSupervision.business.app.BaseActivity;
import com.chenganrt.smartSupervision.business.login.UserSP;
import com.chenganrt.smartSupervision.common.okhttputil.CallBackUtil;
import com.chenganrt.smartSupervision.common.okhttputil.HttpConstant;
import com.chenganrt.smartSupervision.common.okhttputil.OkhttpUtil;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

import static android.graphics.Color.WHITE;

public class ForgetPasswordActivity extends BaseActivity implements View.OnClickListener  {
    private Button btn_save;
    private EditText et_now,et_new,et_new2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgetpassword);
        layout_head.setVisibility(View.VISIBLE);
        layout_head.setBackgroundColor(WHITE);
        tv_title.setText("修改密码");
        tv_title.setTextColor(Color.BLACK);
        btn_left.setBackgroundResource(R.drawable.arrow_back);
        btn_save = findViewById(R.id.btn_save);
        btn_save.setOnClickListener(this);
        et_now = (EditText) findViewById(R.id.et_now);
        et_new = (EditText) findViewById(R.id.et_new);
        et_new2 = (EditText) findViewById(R.id.et_new_password);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_save:
                String now = et_now.getText().toString().trim();
                String pass1 = et_new.getText().toString().trim();
                String pass2 = et_new2.getText().toString().trim();
                if (!TextUtils.isEmpty(now) && !TextUtils.isEmpty(pass1) && !TextUtils.isEmpty(pass2)) {
                    //两次密码一致
                    if (pass1.equals(pass2)) {
                        String url = HttpConstant.baseUrl + "/user/changePassword";
                        Map<String,String> map=new HashMap<>();
                        map.put("id", UserSP.getInstance().getUserId(this));
                        map.put("password",now);
                        map.put("new_password",pass1);
                        OkhttpUtil.okHttpPostJson(this,url,new Gson().toJson(map),new CallBackUtil.CallBackString() {
                            @Override
                            public void onFailure(Call call, Exception e) {
                                Toast.makeText(ForgetPasswordActivity.this, "密码重置失败！", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(ForgetPasswordActivity.this, "密码重置成功！", Toast.LENGTH_SHORT).show();
                                finish();

                            }
                        });
                    } else {
                        Toast.makeText(ForgetPasswordActivity.this, "两次密码输入不一致！", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ForgetPasswordActivity.this, "输入框不能为空！", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
