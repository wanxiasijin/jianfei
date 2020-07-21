package com.chenganrt.smartSupervision.business.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.chenganrt.smartSupervision.R;
import com.chenganrt.smartSupervision.business.exchange.filter.FilterActivity;
import com.chenganrt.smartSupervision.common.okhttputil.CallBackUtil;
import com.chenganrt.smartSupervision.common.okhttputil.HttpConstant;
import com.chenganrt.smartSupervision.common.okhttputil.OkhttpUtil;
import com.chenganrt.smartSupervision.model.response.UserTotalBean;
import com.chenganrt.smartSupervision.model.response.UserTotalParser;

import org.json.JSONException;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import okhttp3.Call;

//首页土木交换
@SuppressLint("ValidFragment")
public class ExchangeFragment extends Fragment {
    private TextView tv11,tv12,tv13,tv14,tv_check_details;
    public boolean isRefresh;
    public boolean getRefresh() {
        return isRefresh;
    }
    public void setRefresh(boolean refresh) {
        isRefresh = refresh;
    }
    private void initEarthworkExchange() {
        String url = HttpConstant.baseUrl + "/earthworkexchange/statistics";
        OkhttpUtil.okHttpGet(getActivity(),url, new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {
                //Toast.makeText(HomeActivity.this, "服务器错误", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onResponse(String response) {
                try {
                    UserTotalBean userTotalBean = UserTotalParser.parse(response);
                    setData(userTotalBean.getWaste_supply(),
                            userTotalBean.getWaste_demand(),
                            userTotalBean.getMultipurpose_use_supply(),
                            userTotalBean.getMultipurpose_use_demand());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void setData(int waste_supply, int waste_demand, int multipurpose_use_supply, int multipurpose_use_demand) {
        if (tv11 == null || tv12 == null || tv13 == null || tv14 == null)
            return;
        tv11.setText(waste_supply + "万方");
        tv13.setText(waste_demand + "万方");
        tv12.setText(multipurpose_use_supply + "万方");
        tv14.setText(multipurpose_use_demand + "万方");
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fr_simple_card2, null);
        tv11 = v.findViewById(R.id.tv11);
        tv12 = v.findViewById(R.id.tv12);
        tv13 = v.findViewById(R.id.tv13);
        tv14 = v.findViewById(R.id.tv14);
        tv_check_details = v.findViewById(R.id.tv_check_details);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tv_check_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), FilterActivity.class);
                startActivity(intent);
            }
        });
        initEarthworkExchange();

    }

}