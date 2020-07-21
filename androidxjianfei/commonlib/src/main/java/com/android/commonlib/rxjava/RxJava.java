package com.android.commonlib.rxjava;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RxJava {
    private volatile static RxJava mRxJava;
    private static CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public interface ObserveOnMainThread<T> {
        void accept(T t);
    }

    public interface ThrowableOnMainThread {
        void accept(String t);
    }

    public static RxJava getInstance() {
        if (mRxJava == null) {
            synchronized (RxJava.class) {
                if (mRxJava == null) {
                    mRxJava = new RxJava();
                }
            }
        }
        return mRxJava;
    }

    public <T> void create(ObservableOnSubscribe<T> a, final ObserveOnMainThread<T> b, final ThrowableOnMainThread c) {
        Observable.create(a)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<T>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(T t) {
                        b.accept(t);
                    }

                    @Override
                    public void onError(Throwable e) {
                        c.accept(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void clear() {
        mCompositeDisposable.clear();
    }
}
