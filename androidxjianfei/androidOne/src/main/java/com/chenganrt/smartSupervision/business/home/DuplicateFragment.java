package com.chenganrt.smartSupervision.business.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chenganrt.smartSupervision.R;
import com.chenganrt.smartSupervision.business.electronic.okhttp.UICallback;
import com.chenganrt.smartSupervision.business.electronic.parse.entiy.AnalyseEntity;
import com.chenganrt.smartSupervision.business.electronic.ui.adapter.HomeAction;
import com.chenganrt.smartSupervision.business.electronic.util.AppTools;
import com.chenganrt.smartSupervision.business.login.UserSP;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

//首页电子联单
@SuppressLint("ValidFragment")
public class DuplicateFragment extends Fragment {

    private TextView sign,confirm,cancel,notSign;
    private HomeAction homeAction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fr_simple_card, null);
        sign = (TextView) v.findViewById(R.id.to_sign);
        confirm = (TextView) v.findViewById(R.id.tv_confirm);
        cancel = (TextView) v.findViewById(R.id.tv_cancel);
        notSign = (TextView) v.findViewById(R.id.not_sign);
        return v;
    }
    public void setData(String tab1, String tab2, String tab3, String tab4) {
        if (sign == null || confirm == null || cancel == null || notSign == null)
            return;
        sign.setText(tab1);
        confirm.setText(tab2);
        cancel.setText(tab3);
        notSign.setText(tab4);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getAnalyseData(AppTools.getStringDateShort(), AppTools.getStringDateShort(), "1", "", false);
    }
    private void getAnalyseData(String bTime, String eTime, String type, String Vehicle, boolean isLoading) {
        if (homeAction == null) homeAction = new HomeAction();
        homeAction.statisticAction(UserSP.getInstance().getUserName(getActivity()),
                String.valueOf(UserSP.getInstance().getUserType(getActivity())),
                bTime,
                eTime,
                type,
                Vehicle,
                new UICallback(getActivity(), isLoading) {
                    @Override
                    public void handleSuccess(Message msg) {
                        AnalyseEntity analyseEntity = (AnalyseEntity) msg.obj;
                        if (analyseEntity == null) return;
                         setData(String.valueOf(analyseEntity.getToSign()),
                                String.valueOf(analyseEntity.getConfirmed()),
                                String.valueOf(analyseEntity.getCancelled()),
                                String.valueOf(analyseEntity.getSoilExcepted()));
                    }

                    @Override
                    public void handleFailure(Message msg) {
                        super.handleFailure(msg);
                    }
                });}
}