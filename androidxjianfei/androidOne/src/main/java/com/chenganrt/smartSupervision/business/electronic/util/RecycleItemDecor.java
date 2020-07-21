package com.chenganrt.smartSupervision.business.electronic.util;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by dell on 2018/7/13.
 */

public class RecycleItemDecor extends RecyclerView.ItemDecoration {

    int mSpace;

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.left = mSpace;
        outRect.right = mSpace;
        outRect.bottom = mSpace;
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = mSpace;
        }

    }

    public RecycleItemDecor(int space) {
        this.mSpace = space;
    }

}
