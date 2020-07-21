package com.chenganrt.smartSupervision.business.exchange.filter.view;

import android.content.Context;
import android.widget.PopupWindow;

import com.chenganrt.smartSupervision.business.exchange.filter.view.listener.OnFilterToViewListener;

import java.util.List;


public interface IPopupLoader {

    PopupWindow getPopupEntity(Context context, List data, int filterType, int position, OnFilterToViewListener onFilterToViewListener, FilterTabView view);
}
