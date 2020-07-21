package com.chenganrt.smartSupervision.widget.TwinklingRefreshLayout.footer;

import android.view.View;

/**
 * Created by lcodecore on 2016/10/1.
 */

public interface IBottomView {
    View getView();

    void onPullingUp(float fraction, float maxBottomHeight, float bottomHeight);

    void startAnim(float maxBottomHeight, float bottomHeight);

    void onPullReleasing(float fraction, float maxBottomHeight, float bottomHeight);

    void onFinish();

    void reset();

    void setNoMore(boolean value);
}
