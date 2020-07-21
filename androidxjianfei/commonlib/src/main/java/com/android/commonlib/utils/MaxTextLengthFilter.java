package com.android.commonlib.utils;

import android.content.Context;
import android.text.InputFilter;
import android.text.Spanned;
import android.widget.Toast;

/**
 * Created by wanwan on 18-3-12.
 */

public class MaxTextLengthFilter implements InputFilter {

    private int mMaxTextLength;
    private Toast mToast;

    public MaxTextLengthFilter(Context context, int resource, int maxTextLength) {
        mMaxTextLength = maxTextLength;
        mToast = Toast.makeText(context, resource, Toast.LENGTH_SHORT);
    }

    public CharSequence filter(CharSequence source, int start, int end,
                               Spanned dest, int dstart, int dend) {
        int keep = mMaxTextLength - (dest.length() - (dend - dstart));
        if (keep < (end - start)) {
            mToast.show();
        }
        if (keep <= 0) {
            return "";
        } else if (keep >= end - start) {
            return null;
        } else {
            return source.subSequence(start, start + keep);
        }
    }
}