package com.chenganrt.smartSupervision.business.exchange.publish.tab;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.android.commonlib.okhttp.bean.ExchangeData;

import java.util.List;

import androidx.annotation.Nullable;

public class InfoAdapter extends BaseQuickAdapter<ExchangeData, BaseViewHolder> {

    public InfoAdapter(int layoutResId, @Nullable List<ExchangeData> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, ExchangeData exchangeData) {
//        baseViewHolder.setText(R.id.project, publishData.);
    }
}
