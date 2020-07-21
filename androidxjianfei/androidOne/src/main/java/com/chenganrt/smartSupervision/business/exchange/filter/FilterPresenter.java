package com.chenganrt.smartSupervision.business.exchange.filter;

import com.android.commonlib.mvp.BasePresenter;
import com.android.commonlib.rxjava.RxJava;
import com.chenganrt.smartSupervision.utils.db.DBManager;
import com.chenganrt.smartSupervision.utils.db.entity.SearchRecord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import timber.log.Timber;

public class FilterPresenter extends BasePresenter<FilterActivity> implements FilterContact.Presenter {
    /**
     * 构造方法
     *
     * @param view View层对象
     */
    public FilterPresenter(FilterActivity view) {
        super(view);
    }

    @Override
    public void checkNetwork() {

    }

    @Override
    public void saveRecord(boolean isSave, String key) {
        RxJava.getInstance().create(new SaveRecordObservable(isSave, key), new RxJava.ObserveOnMainThread<Boolean>() {
            @Override
            public void accept(Boolean key) {
                getRecord();
            }
        }, new RxJava.ThrowableOnMainThread() {
            @Override
            public void accept(String t) {
                if (t != null && !t.isEmpty() && getView().isActive()) {
                    getView().showError(t);
                }
            }
        });
    }

    @Override
    public void getHotRearch() {
        RxJava.getInstance().create(new HotRearchObservable(), new RxJava.ObserveOnMainThread<List<SearchRecord>>() {
            @Override
            public void accept(List<SearchRecord> labels) {
                if (getView().isActive) {
                    getView().showHotData(labels);
                }
            }
        }, new RxJava.ThrowableOnMainThread() {
            @Override
            public void accept(String t) {
                if (t != null && !t.isEmpty() && getView().isActive()) {
                    getView().showError(t);
                }
            }
        });
    }

    @Override
    public void getRecord() {
        RxJava.getInstance().create(new RecordObservable(), new RxJava.ObserveOnMainThread<List<SearchRecord>>() {
            @Override
            public void accept(List<SearchRecord> labels) {
                if (getView().isActive) {
                    getView().showRecord(labels);
                }
            }
        }, new RxJava.ThrowableOnMainThread() {
            @Override
            public void accept(String t) {
                if (t != null && !t.isEmpty() && getView().isActive()) {
                    getView().showError(t);
                }
            }
        });
    }

    @Override
    public Map<Integer, List<SearchRecord>> getLabelData() {
        Map<Integer, List<SearchRecord>> map = new HashMap<>();
        List<SearchRecord> list0 = new ArrayList<>();
        String[] names0 = {"工程渣土", "工程泥浆", "施工废弃物", "拆除废弃物", "装修废弃物"};
        for (int i = 0; i < names0.length; i++) {
            list0.add(new SearchRecord((long) i, names0[i]));
        }
        map.put(0, list0);
        List<SearchRecord> list1 = new ArrayList<>();
        String[] names1 = {"再生骨料混泥土", "再生骨料混砂浆", "砌体块材料再生产品", "板材料再生产品", "再生骨料"};
        for (int i = 0; i < names1.length; i++) {
            list1.add(new SearchRecord((long) i, names1[i]));
        }
        map.put(1, list1);
        List<SearchRecord> list2 = new ArrayList<>();
        String[] names2 = {"1天内", "7天内", "14天内"};
        for (int i = 0; i < names2.length; i++) {
            list2.add(new SearchRecord((long) i, names2[i]));
        }
        map.put(2, list2);
        return map;
    }

    @Override
    public void clear() {
        RxJava.getInstance().clear();
    }

    private static class SaveRecordObservable implements ObservableOnSubscribe<Boolean> {

        private String mKey;
        private boolean isSave;

        public SaveRecordObservable(boolean isSave, String key) {
            this.isSave = isSave;
            mKey = key;
        }

        @Override
        public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
           if (isSave) {
               SearchRecord searchRecord = new SearchRecord();
               searchRecord.setKeyword(mKey);
               DBManager.getInstance().insertSearchRecord(searchRecord);
           } else {
               DBManager.getInstance().clearSearchRecords();
           }
            emitter.onNext(true);
            emitter.onComplete();
        }
    }

    private static class HotRearchObservable implements ObservableOnSubscribe<List<SearchRecord>> {

        @Override
        public void subscribe(ObservableEmitter<List<SearchRecord>> emitter) throws Exception {
            List<SearchRecord> list = new ArrayList<>();
            String[] names = {"建筑废弃物供应信息", "建筑废弃物需求信息", "综合利用物供应信息", "综合利用物需求信息", "消纳场排队信息", "建筑废弃物供应信息", "建筑废弃物需求信息", "综合利用物供应信息", "综合利用物需求信息", "消纳场排队信息"};
            for (int i = 0; i < 10; i++) {
                list.add(new SearchRecord((long) i, names[i]));
            }
            emitter.onNext(list);
            emitter.onComplete();
        }
    }

    private static class RecordObservable implements ObservableOnSubscribe<List<SearchRecord>> {

        @Override
        public void subscribe(ObservableEmitter<List<SearchRecord>> emitter) throws Exception {
            try {
                List<SearchRecord> list =  DBManager.getInstance().getSearchRecords();
                Timber.d("SearchRecords" + list.size());
                emitter.onNext(list);
            } catch (Exception e) {
                emitter.onError(e);
            }
            emitter.onComplete();
        }
    }
}
