package com.chenganrt.smartSupervision.business.home;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.android.commonlib.view.flycotablayout.CommonTabLayout;
import com.android.commonlib.view.flycotablayout.listener.CustomTabEntity;
import com.chenganrt.smartSupervision.R;
import com.chenganrt.smartSupervision.business.app.BaseActivity;
import com.chenganrt.smartSupervision.business.login.UserSP;
import com.chenganrt.smartSupervision.common.okhttputil.CallBackUtil;
import com.chenganrt.smartSupervision.common.okhttputil.HttpConstant;
import com.chenganrt.smartSupervision.common.okhttputil.OkhttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.fragment.app.Fragment;
import okhttp3.Call;

import static android.graphics.Color.WHITE;

public class MatchingMessageActivity extends BaseActivity {

    private CommonTabLayout mTabLayout;
    private String[] mTitles = { "电子联单消息", "撮合消息"};
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matchmessage_layout);
        initTitleBar();
        initComonTabLayout();
        initUnReadCount();
    }
    private void initUnReadCount() {
        String url = HttpConstant.baseUrl + "/sys/ssitgNoticeMsg/unReadCount";
        Map<String,String> map=new HashMap<>();
        map.put("userId", UserSP.getInstance().getUserId(this));
        OkhttpUtil.okHttpGet(this,url,map,new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {
            }
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.has("data")&&!jsonObject.isNull("data")){
                        int number1= (int) jsonObject.optJSONObject("data").optInt("total");
                        int number2= (int) jsonObject.optJSONObject("data").optInt("earth_count");
                        if(number1-number2>0){
                            mTabLayout.showMsg(0, number1-number2);
                        }
                        if(number2>0){
                            mTabLayout.showMsg(1, number2);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void initTitleBar() {
        getSupportActionBar().hide();
//        StatusBarUtil.setColor(this, WHITE );
        layout_head.setVisibility(View.VISIBLE);
        layout_head.setBackgroundColor(WHITE);
        tv_title.setText("撮合消息");
        tv_title.setTextColor(Color.BLACK);
        btn_left.setBackgroundResource(R.drawable.arrow_back);
    }

    private void initComonTabLayout() {
        mTabLayout = (CommonTabLayout) findViewById(R.id.commontablayout);
        ElectronicConcatenatedMessagesFragment electronicConcatenatedMessagesFragment = new ElectronicConcatenatedMessagesFragment();
        mFragments.add(electronicConcatenatedMessagesFragment);
        mTabEntities.add(new TabEntity(mTitles[0], 0, 0));

        EarthmenExchangeMessagesFragment earthmenExchangeMessagesFragment = new EarthmenExchangeMessagesFragment();
        mFragments.add(earthmenExchangeMessagesFragment);
        mTabEntities.add(new TabEntity(mTitles[1], 0, 0));
        mTabLayout.setTabData(mTabEntities, this, R.id.fl_change, mFragments);
    }

}
