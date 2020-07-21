package com.chenganrt.smartSupervision.business.exchange.filter.search;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.commonlib.utils.DimenUtil;
import com.chenganrt.smartSupervision.R;
import com.chenganrt.smartSupervision.utils.db.entity.SearchRecord;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class SearchLabelContainerView extends LabelBaseFlowView {

    private Context mContext;
    private List<SearchRecord> mLabels;
    private List<TextView> mViews = new ArrayList<>();

    public boolean ismEnableMultipleSelected() {
        return mEnableMultipleSelected;
    }

    public void setmEnableMultipleSelected(boolean mEnableMultipleSelected) {
        this.mEnableMultipleSelected = mEnableMultipleSelected;
    }

    private boolean mEnableMultipleSelected;

    private OnLabelItemListener mOnLabelItemClickListener;

    public interface OnLabelItemListener {
        void onLabelItemClick(SearchRecord label);
    }

    public SearchLabelContainerView(Context context) {
        this(context, null);
    }

    public SearchLabelContainerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SearchLabelContainerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mLabels = new ArrayList<>();
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray typedArray = null;
        try {
            typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.FilterLabelView);
            mScreenWidthMarginLeft = typedArray.getInteger(R.styleable.FilterLabelView_screen_width, 0);
            int[] screenSize = DimenUtil.getScreenSizes(mContext);
            mScreenWidth = screenSize[0] - mScreenWidthMarginLeft;
            Timber.d("mScreenWidthMarginLeft:" + mScreenWidthMarginLeft);
            Timber.d("mScreenWidth:" + mScreenWidth);
            Resources res = mContext.getResources();
            mHorizonPadding = res.getDimensionPixelSize(R.dimen.label_layout_padding);
            Timber.d("mHorizonPadding:" + mHorizonPadding);
            mHorizonMargin = res.getDimensionPixelSize(R.dimen.label_margin);
            Timber.d("mHorizonMargin:" + mHorizonMargin);
            mVerticalMargin = mHorizonMargin;
        } catch (Exception e) {
            Timber.d(e);
        } finally {
            if (typedArray != null) {
                typedArray.recycle();
            }
        }
    }

    public void setOnLabelItemClickListener(OnLabelItemListener mOnLabelItemClickListener) {
        this.mOnLabelItemClickListener = mOnLabelItemClickListener;
    }

    public void setLabels(List<SearchRecord> labels) {
        removeAllViews();
        if (mLabels != null) {
            mLabels.clear();
            mLabels.addAll(labels);
        }
        update();
    }

    private void update() {
        if (mLabels == null || mLabels.size() == 0) {
            return;
        }
        if (mViews != null && mViews.size() > 0) {
            mViews.clear();
        }

        for (int i = 0; i < mLabels.size(); i++) {
            LinearLayout labelLayout = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.label_item_view, null);
            TextView label = labelLayout.findViewById(R.id.tv_label);
            label.setText(mLabels.get(i).getKeyword());
            label.setTag(mLabels.get(i));
            mViews.add(label);
            labelLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnLabelItemClickListener == null) {
                        return;
                    }

                    if (!mEnableMultipleSelected) {
                        for (int i = 0; i < mViews.size(); i++) {
                            mLabels.get(i).setChecked(false);
                            mViews.get(i).setTag(mLabels.get(i));
                            mViews.get(i).setBackgroundColor(mContext.getResources().getColor(R.color.search_label_color));
                            mViews.get(i).setTextColor(mContext.getResources().getColor(R.color.black));
                        }
                    }

                    SearchRecord labelData = (SearchRecord) v.findViewById(R.id.tv_label).getTag();
                    if (labelData.isChecked()) {
                        label.setBackgroundColor(mContext.getResources().getColor(R.color.search_label_color));
                        label.setTextColor(mContext.getResources().getColor(R.color.black));
                    } else {
                        label.setBackgroundColor(mContext.getResources().getColor(R.color.green_theam2));
                        label.setTextColor(mContext.getResources().getColor(R.color.white));
                    }
                    labelData.setChecked(!labelData.isChecked());
                    label.setTag(labelData);
                    mOnLabelItemClickListener.onLabelItemClick(labelData);
                }
            });
            addView(labelLayout);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int count = getChildCount();
        int right = mScreenWidth - mHorizonPadding;
        int row = 0;
        int totalX = l + mHorizonPadding;
        int totalY = 0;
        for (int i = 0; i < count; i++) {
            final View child = this.getChildAt(i);
            int width = child.getMeasuredWidth();
            int height = child.getMeasuredHeight();
            if (totalX == l + mHorizonPadding) {
                totalX += width;
            } else {
                totalX += (mHorizonPadding + width);
            }
            if (totalX > right) {
                row++;
                totalX = l + mHorizonPadding + width;
            }
            totalY = row * (height + mVerticalMargin) + height + mVerticalMargin;
            child.layout(totalX - width, totalY - height, totalX, totalY);
        }
        super.onLayout(true, l, t, r, totalY + mVerticalMargin);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mLabels.clear();
        mLabels = null;
        mOnLabelItemClickListener = null;
    }
}
