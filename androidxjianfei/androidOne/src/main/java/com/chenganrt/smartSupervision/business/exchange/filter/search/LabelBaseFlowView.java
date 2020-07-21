package com.chenganrt.smartSupervision.business.exchange.filter.search;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class LabelBaseFlowView extends ViewGroup {
    public int mScreenWidth;
    public int mScreenWidthMarginLeft;
    public int mHorizonPadding;
    public int mHorizonMargin;
    public int mVerticalMargin;

    public LabelBaseFlowView(Context context) {
        this(context, null);
    }

    public LabelBaseFlowView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LabelBaseFlowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int childCount = getChildCount();
        for (int index = 0; index < childCount; index++) {
            final View child = getChildAt(index);
            child.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
        }

        setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec), getWrapHeight());
    }

    private int getWrapHeight() {
        final int count = getChildCount();
        int right = mScreenWidth - mHorizonPadding;
        int row = 0;
        int totalX = mHorizonPadding;
        int totalY = 0;
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            int width = child.getMeasuredWidth();
            int height = child.getMeasuredHeight();
            if (totalX == mHorizonPadding) {
                totalX += width;
            } else {
                totalX += (mHorizonMargin + width);
            }
            if (totalX > right) {
                row++;
                totalX = mHorizonPadding + width;
            }
            totalY = (row + 1) * (height + mVerticalMargin);
        }
        return totalY + mVerticalMargin;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
    }
}