package com.chenganrt.smartSupervision.business.electronic.util;

import java.util.Observer;

import androidx.recyclerview.widget.RecyclerView;

public interface ICountDownTime {
    void addObserver(Observer observer);
    void removieObserver(Observer observer);
    void deleteObservers();
    void startCountdown();
    void stopCountdown();
    boolean containHolder(Observer observer);
    void notifyAdapter();
    void bindRecyclerView(RecyclerView recyclerView);
}
