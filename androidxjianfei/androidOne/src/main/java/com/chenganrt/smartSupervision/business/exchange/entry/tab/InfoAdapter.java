package com.chenganrt.smartSupervision.business.exchange.entry.tab;

import android.content.Context;
import android.widget.ImageView;

import com.android.commonlib.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chenganrt.smartSupervision.R;
import com.android.commonlib.okhttp.bean.ExchangeData;

import java.util.List;

import androidx.annotation.Nullable;

public class InfoAdapter extends BaseQuickAdapter<ExchangeData, BaseViewHolder> {
    private Context mContext;

    public InfoAdapter(Context context, int layoutResId, @Nullable List<ExchangeData> data) {
        super(layoutResId, data);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, ExchangeData exchangeData) {
        baseViewHolder.setText(R.id.project, exchangeData.getTitle());
        baseViewHolder.setText(R.id.type, exchangeData.getWaste_type_detail());
        baseViewHolder.setText(R.id.area, exchangeData.getArea());
        baseViewHolder.setText(R.id.time, exchangeData.getUpdate_time());
        baseViewHolder.setText(R.id.amount, exchangeData.getAmount() + "万方");
        ImageView iv_cover = baseViewHolder.getView(R.id.iv_cover);
        if (exchangeData.getFiles() != null && exchangeData.getFiles().size() > 0) {
            Glide.getInstance().displayImage(mContext, exchangeData.getFiles().get(0), iv_cover);
        }
    }
}
