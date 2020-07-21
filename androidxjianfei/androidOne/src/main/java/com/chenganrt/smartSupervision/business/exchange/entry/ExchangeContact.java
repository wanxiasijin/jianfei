package com.chenganrt.smartSupervision.business.exchange.entry;

import com.android.commonlib.mvp.IBaseContact;
import com.android.commonlib.okhttp.bean.ExchangeStatistics;
import com.android.commonlib.okhttp.bean.ExchangeData;

import java.util.List;

public interface ExchangeContact {
    interface View extends IBaseContact.IBaseView {
        void showExchangeStatistics(ExchangeStatistics data);
        void showExchangeData(List<ExchangeData> list);
        void showError(String error);
        String getExchangeType();

    }

    interface Presenter extends IBaseContact.IBasePresenter {
        void getExchangeStatistics();
        void getTotalSupplyAndDemand();
    }
}
