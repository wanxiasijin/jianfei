package com.chenganrt.smartSupervision.business.exchange.detail;

import android.content.Context;
import android.widget.ImageView;

import com.android.commonlib.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chenganrt.smartSupervision.R;

import java.util.List;

import androidx.annotation.Nullable;

public class ImageListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private Context mContext;

    public ImageListAdapter(Context context, int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, String data) {
        ImageView imageView = (ImageView) baseViewHolder.getView(R.id.iv_detail);
        Glide.getInstance().displayImage(mContext, data, imageView);
    }
}