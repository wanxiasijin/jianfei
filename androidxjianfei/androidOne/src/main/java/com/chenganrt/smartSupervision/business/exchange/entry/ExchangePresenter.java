package com.chenganrt.smartSupervision.business.exchange.entry;

import com.android.commonlib.mvp.BasePresenter;
import com.android.commonlib.okhttp.NetWorkDataManager;
import com.chenganrt.smartSupervision.business.exchange.entity.ExchangeListResponse;
import com.android.commonlib.okhttp.bean.ExchangeStatistics;
import com.android.commonlib.okhttp.bean.Response;
import com.android.commonlib.rxjava.RxJava;
import com.android.commonlib.utils.GsonUtil;

import java.util.LinkedHashMap;
import java.util.Map;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import timber.log.Timber;

public class ExchangePresenter extends BasePresenter<ExchangeActivity> implements ExchangeContact.Presenter {

    /**
     * 构造方法
     *
     * @param view View层对象
     */
    public ExchangePresenter(ExchangeActivity view) {
        super(view);
    }

    @Override
    public void getExchangeStatistics() {
        RxJava.getInstance().create(new StatisticsObservable(), new RxJava.ObserveOnMainThread<Response>() {
            @Override
            public void accept(Response response) {
                if (getView().isActive()) {
                    if (response.getStatus() == 200) {
                        ExchangeStatistics exchangeStatistics = GsonUtil.JsonToObject(GsonUtil.toJson(response.getData()), ExchangeStatistics.class);
                        if (exchangeStatistics != null) {
                            Timber.d("exchangeStatistics:" + exchangeStatistics.toString());
                            getView().showExchangeStatistics(exchangeStatistics);
                        }
                    }
                }
            }
        }, new RxJava.ThrowableOnMainThread() {
            @Override
            public void accept(String t) {

            }
        });
    }

    @Override
    public void getTotalSupplyAndDemand() {
        RxJava.getInstance().create(new ExchangeDataObservable(getView()), new RxJava.ObserveOnMainThread<Response>() {
            @Override
            public void accept(Response response) {
                    if (getView().isActive()) {
                        if (response.getStatus() == 200) {
                            Timber.d("response  data:" + response.getData());
                            ExchangeListResponse exchangeListResponse = GsonUtil.JsonToObject(GsonUtil.toJson(response.getData()), ExchangeListResponse.class);
                            if (exchangeListResponse != null) {
                                Timber.d("exchangeListResponse:" + exchangeListResponse.getRecords().toString());
                                getView().showExchangeData(exchangeListResponse.getRecords());
                            }
                        } else {
                            getView().showExchangeData(null);
                            getView().showError(response.getMessage());
                        }
                    }
            }
        }, new RxJava.ThrowableOnMainThread() {
            @Override
            public void accept(String t) {
                if (t != null && !t.isEmpty() && getView().isActive()) {
                    getView().showExchangeData(null);
                    getView().showError(t);
                }
            }
        });
    }

    @Override
    public void clear() {
        RxJava.getInstance().clear();
    }

    private static class StatisticsObservable implements ObservableOnSubscribe<Response> {

        @Override
        public void subscribe(ObservableEmitter<Response> emitter) throws Exception {
            try {
                Response response = NetWorkDataManager.getExchangeStatisticsTotal();
                emitter.onNext(response);
            } catch (Exception e) {
                Timber.d(e);
                emitter.onError(e);
            }
            emitter.onComplete();
        }
    }

    private static class ExchangeDataObservable implements ObservableOnSubscribe<Response> {
        private ExchangeContact.View mView;

        public ExchangeDataObservable(ExchangeContact.View view) {
            mView = view;
        }
        @Override
        public void subscribe(ObservableEmitter<Response> emitter) throws Exception {
            try {
                Map<String, String> supplyMap = new LinkedHashMap<>();
                supplyMap.put("exchange_type", mView.getExchangeType());
                supplyMap.put("current", "1");
                Response response = NetWorkDataManager.getExchangeDataList(supplyMap);
                emitter.onNext(response);
            } catch (Exception e) {
                Timber.d(e);
                emitter.onError(e);
            }
            emitter.onComplete();
        }
    }
}
