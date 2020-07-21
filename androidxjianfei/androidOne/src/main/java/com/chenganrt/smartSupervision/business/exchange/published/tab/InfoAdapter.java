package com.chenganrt.smartSupervision.business.exchange.published.tab;

import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.commonlib.glide.Glide;
import com.android.commonlib.okhttp.bean.PublishedData;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chenganrt.smartSupervision.R;

import java.util.List;

import androidx.annotation.Nullable;

public class InfoAdapter extends BaseQuickAdapter<PublishedData, BaseViewHolder> {

    private Context mContext;
    private OnDeleteClickListener mOnDeleteClickListener;
    private OnEditClickListener mOnEditClickListener;

    public InfoAdapter(Context context, int layoutResId, @Nullable List<PublishedData> data) {
        super(layoutResId, data);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, PublishedData exchangeData) {
        baseViewHolder.setText(R.id.project, exchangeData.getTitle());
        TextView type = baseViewHolder.getView(R.id.type);
        type.setText(exchangeData.getWaste_type_detail());
        baseViewHolder.setText(R.id.area, exchangeData.getArea());
        baseViewHolder.setText(R.id.time, exchangeData.getUpdate_time());
        baseViewHolder.setText(R.id.browse_value, exchangeData.getViews());
        baseViewHolder.setText(R.id.collect_value, exchangeData.getCollection_volume());
        TextView amount = baseViewHolder.getView(R.id.amount);
        amount.setText(exchangeData.getAmount() + "万方");
        ImageView iv_cover = baseViewHolder.getView(R.id.img);
        if (exchangeData.getFiles() != null && exchangeData.getFiles().size() > 0) {
            Glide.getInstance().displayImage(mContext, exchangeData.getFiles().get(0), iv_cover);
        }
        TextView delete = baseViewHolder.getView(R.id.delete);
        TextView edit = baseViewHolder.getView(R.id.edit);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnDeleteClickListener.onDeleteClick(exchangeData);
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnEditClickListener.onEditClick(exchangeData);
            }
        });

        if (exchangeData.getIs_expire().equals("1")) {
            ImageView iv_expire = baseViewHolder.getView(R.id.iv_expire);
            iv_expire.setVisibility(View.VISIBLE);
            iv_cover.setLayerType(View.LAYER_TYPE_HARDWARE, getPaint());
            type.setLayerType(View.LAYER_TYPE_HARDWARE, getPaint());
            amount.setLayerType(View.LAYER_TYPE_HARDWARE, getPaint());
            delete.setLayerType(View.LAYER_TYPE_HARDWARE, getPaint());
            edit.setLayerType(View.LAYER_TYPE_HARDWARE, getPaint());
        }
    }

    private Paint getPaint() {
        Paint paint = new Paint();
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0f);
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        return paint;
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(PublishedData exchangeData);
    }

    public interface OnEditClickListener {
        void onEditClick(PublishedData exchangeData);
    }

    public void setDeleteClickListener(OnDeleteClickListener onDeleteClickListener) {
        mOnDeleteClickListener = onDeleteClickListener;
    }

    public void setEditClickListener(OnEditClickListener onEditClickListener) {
        mOnEditClickListener = onEditClickListener;
    }
}
