package com.chenganrt.smartSupervision.widget.TwinklingRefreshLayout.footer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.commonlib.utils.LogUtil;
import com.chenganrt.smartSupervision.R;


public class LoadMoreFootView extends FrameLayout implements IBottomView {
    private static final String TAG = LoadMoreFootView.class.getSimpleName();
    private ProgressBar mLoadingView;
    private TextView mTxtView;
    private ImageView mReleaseArrow;
    private boolean mNoMore = false;

    public LoadMoreFootView(Context context) {
        this(context, null);
    }

    public LoadMoreFootView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public LoadMoreFootView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.load_to_refresh_header, this, true);
        mLoadingView = rootView.findViewById(R.id.progress_listview_load_more);
        mTxtView = rootView.findViewById(R.id.uc_txt_listview_load_more);
        mReleaseArrow = rootView.findViewById(R.id.img_release_arrow);
    }

    public void setNoMore(boolean noMore) {
        LogUtil.d(TAG, "set no more: mNoMore = " + noMore);
        mNoMore = noMore;
        if (noMore) {
            mReleaseArrow.setVisibility(View.GONE);
            mLoadingView.setVisibility(View.GONE);
        } else {
            mReleaseArrow.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void onPullingUp(float fraction, float maxBottomHeight, float bottomHeight) {
        LogUtil.d(TAG, "onPullingUp");
        if (mNoMore) {
            mTxtView.setText(R.string.twinkling_refresh_layout_load_no_more);
        } else {
            mTxtView.setText(R.string.twinkling_refresh_layout_loading);
        }
    }

    @Override
    public void onPullReleasing(float fraction, float maxBottomHeight, float bottomHeight) {
        LogUtil.d(TAG, "onPullReleasing");
    }

    @Override
    public void startAnim(float maxBottomHeight, float bottomHeight) {
        LogUtil.d(TAG, "startAnim");
        if (!mNoMore) {
            mLoadingView.setVisibility(View.VISIBLE);
            mReleaseArrow.setVisibility(View.GONE);
        }
    }

    @Override
    public void onFinish() {
        LogUtil.d(TAG, "onFinish");
        mLoadingView.setVisibility(View.GONE);
        if (!mNoMore) {
            mReleaseArrow.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void reset() {
        LogUtil.d(TAG, "reset");

    }
}
