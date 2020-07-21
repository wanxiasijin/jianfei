package com.chenganrt.smartSupervision.business.exchange.collect.tab;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.android.commonlib.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chenganrt.smartSupervision.R;

import java.util.List;

import androidx.annotation.Nullable;

public class InfoAdapter extends BaseQuickAdapter<CollectData, BaseViewHolder> {

    private Context mContext;
    private OnCancelCollectClickListener mOnCancelCollectClickListener;

    public InfoAdapter(Context context, int layoutResId, @Nullable List<CollectData> data) {
        super(layoutResId, data);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, CollectData exchangeData) {
        baseViewHolder.setText(R.id.project, exchangeData.getTitle());
        baseViewHolder.setText(R.id.type, exchangeData.getWaste_type_detail());
        baseViewHolder.setText(R.id.area, exchangeData.getArea());
        baseViewHolder.setText(R.id.time, exchangeData.getUpdate_time());
        baseViewHolder.setText(R.id.browse_value, exchangeData.getViews());
        baseViewHolder.setText(R.id.collect_value, exchangeData.getCollection_volume());
        baseViewHolder.setText(R.id.amount, exchangeData.getAmount() + "万方");
        ImageView iv_cover = baseViewHolder.getView(R.id.img);
        if (exchangeData.getFiles() != null && exchangeData.getFiles().size() > 0) {
            Glide.getInstance().displayImage(mContext, exchangeData.getFiles().get(0), iv_cover);
        }
        baseViewHolder.getView(R.id.cancel_collect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnCancelCollectClickListener.onCancelCollectClick(exchangeData);
            }
        });
    }

    public interface OnCancelCollectClickListener {
        void onCancelCollectClick(CollectData exchangeData);
    }

    public void setCancelCollectClickListener(OnCancelCollectClickListener onCancelCollectClickListener) {
        mOnCancelCollectClickListener = onCancelCollectClickListener;
    }
}
