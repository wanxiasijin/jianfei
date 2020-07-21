package com.chenganrt.smartSupervision.business.electronic.wheel;

import android.app.Dialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.chenganrt.smartSupervision.R;
import com.chenganrt.smartSupervision.business.electronic.wheel.adapter.WheelView;


/**
 */

public class FFTWheelDialog extends FFTDialog {
    private final static int DIALOG_TYPE = 1;// 0  - 原生   1 - 自定义

    @Override
    protected int getDialogType() {
        return DIALOG_TYPE;
    }

    @Override
    protected Dialog createNativeDialog(FAlertDialog.Builder configBuilder) {
        return createSelfDialog(configBuilder);
    }

    @Override
    protected Dialog createSelfDialog(FAlertDialog.Builder configBuilder) {
        FAlertDialog dialog = getFAlertDialog(configBuilder);

        configBuilder.mGravity = Gravity.BOTTOM;
        initSelfTitleBar(configBuilder);
        initSelfButtonBar(dialog, configBuilder);
        initSelfWheel(dialog, configBuilder);

        setFAlertDialogWindow(dialog, configBuilder);
        return dialog;
    }

    protected void initSelfWheel(final FAlertDialog dialog, final FAlertDialog.Builder configBuilder) {
        View mContentView = configBuilder.mContentView;
        if (configBuilder.mWheelData1 != null || configBuilder.mWheelData2 != null || configBuilder.mWheelData3 != null) {
            WheelView wheel1 = (WheelView) mContentView.findViewById(R.id.wheel1);
            WheelView wheel2 = (WheelView) mContentView.findViewById(R.id.wheel2);
            WheelView wheel3 = (WheelView) mContentView.findViewById(R.id.wheel3);
            if (configBuilder.mWheelData1 != null && configBuilder.mWheelData1.length > 0) {
                initWheelView(wheel1);
            } else {
                wheel1.setVisibility(View.GONE);
            }
            if (configBuilder.mWheelData2 != null && configBuilder.mWheelData2.length > 0) {
                initWheelView(wheel2);
            } else {
                wheel2.setVisibility(View.GONE);
            }
            if (configBuilder.mWheelData3 != null && configBuilder.mWheelData3.length > 0) {
                initWheelView(wheel3);
            } else {
                wheel3.setVisibility(View.GONE);
            }
        } else if (configBuilder.mAdapter != null) {
            View layoutWheel = mContentView.findViewById(R.id.layout_wheelView);
            layoutWheel.setVisibility(View.GONE);
            ListView list = (ListView) LayoutInflater.from(configBuilder.mContext).inflate(R.layout.item_pay_list, null);
            list.setAdapter(configBuilder.mAdapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (configBuilder.mOnItemClickListener != null) {
                        configBuilder.mOnItemClickListener.onItemClick(dialog, parent, view, position, id);
                    }
                }
            });
            if (configBuilder.mAdapter.getCount() > 0) {
                View item = configBuilder.mAdapter.getView(0, null, null);
                item.measure(0, 0);
                int height = item.getMeasuredHeight() * configBuilder.mAdapter.getCount();
                if (height >= configBuilder.mMaxHeight) {
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT, configBuilder.mMaxHeight);
                    list.setLayoutParams(lp);
                }
            }
            initSelfCustomView(configBuilder, list);
        } else {
            View layoutWheel = mContentView.findViewById(R.id.layout_wheelView);
            layoutWheel.setVisibility(View.GONE);
        }
    }

    private void initWheelView(WheelView wheel) {
        wheel.setVisibleItems(5);
    }

    @Override
    protected void setWheelCurrentPostion(FAlertDialog.Builder configBuilder, int postion1, int postion2, int postion3) {
        View mContentView = configBuilder.mContentView;
        if (postion1 >= 0 && configBuilder.mWheelData1 != null && configBuilder.mWheelData1.length > postion1) {
            ((WheelView) mContentView.findViewById(R.id.wheel1)).setCurrentItem(postion1);
        }

        if (postion2 >= 0 && configBuilder.mWheelData2 != null && configBuilder.mWheelData2.length > postion2) {
            ((WheelView) mContentView.findViewById(R.id.wheel2)).setCurrentItem(postion2);
        }

        if (postion3 >= 0 && configBuilder.mWheelData3 != null && configBuilder.mWheelData3.length > postion3) {
            ((WheelView) mContentView.findViewById(R.id.wheel3)).setCurrentItem(postion3);
        }
    }
}
