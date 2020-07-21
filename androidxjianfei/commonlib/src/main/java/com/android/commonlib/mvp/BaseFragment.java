package com.android.commonlib.mvp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.commonlib.view.processdialog.HProgressDialog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment<T extends BasePresenter> extends Fragment {
    private T presenter;
    private HProgressDialog mDialog;
    public T getPresenter() {
        return presenter;
    }

    /**
     * Fragment的View加载完毕的标记
     */
    private boolean isViewCreated;

    /**
     * Fragment对用户是否可见的标记
     */
    private boolean isUIVisible;

    /**
     * Fragment是否第一次可见
     */
    private boolean isFirstVisible = true;

    /**
     * 是否要懒加载
     */
    public boolean isViewpagerFragment = true;

    public View view = null;

    public Unbinder unbinder = null;

    /**
     * 创建Fragment
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (view == null) {
            view = View.inflate(getContext(), initLayout(), null);
            unbinder = ButterKnife.bind(this, view);
        }
    }

    /**
     * 创建Fragment视图
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (!isViewpagerFragment) {
            init();
        }
        return view;
    }

    /**
     * Fragment视图已经创建完成
     *
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewCreated = true;
        lazyLoad();
    }

    /**
     * 设置Fragment当前是否被用户可见
     *
     * @param isVisibleToUser Fragment当前是否被用户可见
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //isVisibleToUser这个boolean值表示:该Fragment的UI 用户是否可见
        if (isVisibleToUser) {
            isUIVisible = true;
            lazyLoad();
        } else {
            isUIVisible = false;
        }
    }

    /**
     * 懒加载
     */
    private void lazyLoad() {
        //这里进行双重标记判断,是因为setUserVisibleHint会多次回调,并且会在onCreateView执行前回调,必须确保onCreateView加载完毕且页面可见,才加载数据
        if (isViewCreated && isUIVisible && isFirstVisible) {
            loadData();
            //数据加载完毕,恢复标记,防止重复加载
            isViewCreated = false;
            isUIVisible = false;
            isFirstVisible = false;
        }
    }

    protected void loadData() {
        init();
    }

    public void init() {
        presenter = createPresenter();
        initConfig();
        initData();
    }

    /**
     * 基础配置
     */
    public void initConfig() {

    }

    /**
     * 加载数据
     */
    public void initData() {
    }

    /**
     * 加载presenter
     *
     * @return presenter
     */
    public abstract T createPresenter();

    /**
     * 加载布局
     *
     * @return 布局id
     */
    public abstract int initLayout();

    public void showLoading() {
        if (mDialog == null) {
            mDialog = HProgressDialog.createProgress(getActivity()).show();
        }
        mDialog.show();
    }

    /**
     * 关闭等待框
     */
    public void dismissLoading() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.hide();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null && unbinder != Unbinder.EMPTY) {
            unbinder.unbind();
            unbinder = null;
        }
    }

}
