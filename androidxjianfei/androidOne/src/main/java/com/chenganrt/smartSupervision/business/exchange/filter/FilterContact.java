package com.chenganrt.smartSupervision.business.exchange.filter;

import com.android.commonlib.mvp.IBaseContact;
import com.chenganrt.smartSupervision.utils.db.entity.SearchRecord;

import java.util.List;
import java.util.Map;

public interface FilterContact {
    interface View extends IBaseContact.IBaseView {
        void showHotData(List<SearchRecord> list);
        void showRecord(List<SearchRecord> list);
        void showError(String error);

    }

    interface Presenter extends IBaseContact.IBasePresenter {
        void checkNetwork();
        void saveRecord(boolean isSave, String key);
        void getHotRearch();
        void getRecord();
        Map<Integer, List<SearchRecord>> getLabelData();
    }
}
