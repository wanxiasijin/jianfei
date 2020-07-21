package com.android.commonlib.mvp;

import java.lang.ref.WeakReference;

public class BasePresenter<T> {
    private WeakReference<T> view;

    /**
     * 构造方法
     */
    public BasePresenter(T view) {
        this.view = new WeakReference<>(view);
    }

    /**
     * 获取View层对象
     */
    public T getView() {
        return view.get();
    }

    /**
     * 解除关联
     */
    public void clear() {
        if (view != null) {
            view.clear();
            view = null;
        }
    }
}
