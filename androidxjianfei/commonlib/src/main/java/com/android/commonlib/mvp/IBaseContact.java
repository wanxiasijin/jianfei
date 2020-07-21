package com.android.commonlib.mvp;

public interface IBaseContact {
    interface IBaseView {
        boolean isActive();
    }

    interface IBasePresenter {
        void clear();
    }
}
