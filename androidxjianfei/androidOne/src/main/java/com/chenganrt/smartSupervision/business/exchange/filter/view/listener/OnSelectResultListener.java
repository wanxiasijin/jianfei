package com.chenganrt.smartSupervision.business.exchange.filter.view.listener;


import com.chenganrt.smartSupervision.business.exchange.filter.view.FilterResultBean;

import java.util.List;

public interface OnSelectResultListener {

    void onSelectResult(FilterResultBean resultBean);

    void onSelectResultList(List<FilterResultBean> resultBeans);
}
