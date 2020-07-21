package com.chenganrt.smartSupervision.widget.TwinklingRefreshLayout.header;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.commonlib.utils.LogUtil;
import com.chenganrt.smartSupervision.R;

public class LoadFreshHeaderView extends FrameLayout implements IHeaderView {
    private static final String TAG = LoadFreshHeaderView.class.getSimpleName();
    private View mLoadingView;
    private TextView mTxtView;
    private ImageView mImgPullView;
    private ImageView mImgReleaseView;
    private ImageView mImgRefreshSucess;
    private String mRefreshFailure;
    private String mRefreshSuccess;
    private String mRefreshingStr;
    private String mPullDownStr;
    private String mReleaseToRefresh;
    private Context mContext;

    public LoadFreshHeaderView(Context context) {
        this(context, null);
    }

    public LoadFreshHeaderView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public LoadFreshHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mRefreshSuccess = context.getString(R.string.twinkling_refresh_layout_success_refresh);
        mRefreshFailure = context.getString(R.string.twinkling_refresh_layout_failure_refresh);
        mRefreshingStr = context.getString(R.string.twinkling_refresh_layout_refreshing);
        mPullDownStr = context.getString(R.string.twinkling_refresh_layout_pull_to_refresh);
        mReleaseToRefresh = context.getString(R.string.twinkling_refresh_layout_release_to_refresh);
        initView();
    }

    private void initView() {

        View rootView = LayoutInflater.from(mContext).inflate(R.layout.load_to_refresh_header, this, true);
        mLoadingView = rootView.findViewById(R.id.progress_listview_load_more);
        mTxtView = rootView.findViewById(R.id.uc_txt_listview_load_more);
        mImgPullView = rootView.findViewById(R.id.img_pull_arrow);
        mImgReleaseView = rootView.findViewById(R.id.img_release_arrow);
        mImgRefreshSucess = rootView.findViewById(R.id.img_load_success);
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void onPullingDown(float fraction, float maxHeadHeight, float headHeight) {
        LogUtil.d(TAG, "onPullingDown:::fraction:" + fraction + ",maxHeadHeight:" + maxHeadHeight + ",headHeight" + headHeight);
        if (fraction < 0.9f) {
            mTxtView.setText(mPullDownStr);
            mImgPullView.setVisibility(VISIBLE);
            mImgReleaseView.setVisibility(GONE);
        } else {
            mTxtView.setText(mReleaseToRefresh);
            mImgPullView.setVisibility(GONE);
            mImgReleaseView.setVisibility(VISIBLE);
        }

    }

    @Override
    public void onPullReleasing(float fraction, float maxHeadHeight, float headHeight) {
        LogUtil.d(TAG, "onPullReleasing");
        mImgRefreshSucess.setVisibility(GONE);
        if (fraction < 0.9f) {
            mTxtView.setText(mPullDownStr);
            if (mImgPullView.getVisibility() == GONE) {
                mImgPullView.setVisibility(VISIBLE);
                mImgReleaseView.setVisibility(GONE);
                mLoadingView.setVisibility(GONE);
            }
        }
    }

    @Override
    public void startAnim(float maxHeadHeight, float headHeight) {
        LogUtil.d(TAG, "startAnim:::maxHeadHeight:" + maxHeadHeight + "headHeight:" + headHeight);
        mTxtView.setText(mRefreshingStr);
        mImgPullView.setVisibility(GONE);
        mImgReleaseView.setVisibility(GONE);
        mImgRefreshSucess.setVisibility(GONE);
        mLoadingView.setVisibility(VISIBLE);
    }

    @Override
    public void onFinish(OnAnimEndListener animEndListener, boolean isRefreshSuccess) {
        LogUtil.d(TAG, "onFinish");
        if (isRefreshSuccess) {
            mLoadingView.setVisibility(GONE);
            mImgRefreshSucess.setVisibility(VISIBLE);
            mTxtView.setText(mRefreshSuccess);
        }
        animEndListener.onAnimEnd();
    }

    @Override
    public void reset() {
        LogUtil.d(TAG, "reset");
        mImgPullView.setVisibility(VISIBLE);
        mImgReleaseView.setVisibility(GONE);
        mImgRefreshSucess.setVisibility(GONE);
        mLoadingView.setVisibility(GONE);
        mTxtView.setText(mPullDownStr);
    }
}
