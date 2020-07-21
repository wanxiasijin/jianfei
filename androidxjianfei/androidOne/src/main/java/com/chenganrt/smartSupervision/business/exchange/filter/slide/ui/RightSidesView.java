package com.chenganrt.smartSupervision.business.exchange.filter.slide.ui;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.chenganrt.smartSupervision.R;
import com.chenganrt.smartSupervision.business.exchange.filter.slide.adapter.RightSideslipLayAdapter;
import com.chenganrt.smartSupervision.business.exchange.filter.slide.model.AttrList;
import com.chenganrt.smartSupervision.business.exchange.filter.search.SearchLabelContainerView;
import com.chenganrt.smartSupervision.utils.db.entity.SearchRecord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 属性选择的布局及逻辑
 */
public class RightSidesView extends RelativeLayout {
    private SearchLabelContainerView mWasteTypeLayout;
    private SearchLabelContainerView mInfoLayout;
    private SearchLabelContainerView mTimeLayout;
    private Context mCtx;
    private ListView selectList;
    private Button resetBrand;
    private Button okBrand;
    private RelativeLayout mRelateLay;
    private RightSideslipLayAdapter slidLayFrameAdapter;
    private List<AttrList.Attr.Vals> ValsData;
    private Map<String, SearchRecord> mWasteTypeMap = new HashMap<>();
    private Map<String, SearchRecord> mInfoMap = new HashMap<>();
    private Map<String, SearchRecord> mTimeMap = new HashMap<>();
    private List<Map<String, SearchRecord>> mMultipleSelectList = new ArrayList<>();
    private Map<Integer, List<SearchRecord>> mMaps;
    private RightSideListener mRightSideListener;

    public RightSidesView(Context context) {
        super(context);
        mCtx = context;
        inflateView();
        initListener();
    }

    private void inflateView() {
        View.inflate(getContext(), R.layout.include_right_sideslip_layout, this);
        mWasteTypeLayout = (SearchLabelContainerView) findViewById(R.id.waste_type_label_layout);
        mInfoLayout = (SearchLabelContainerView) findViewById(R.id.info_label_layout);
        mTimeLayout = (SearchLabelContainerView) findViewById(R.id.time_label_layout);
        mRelateLay = (RelativeLayout) findViewById(R.id.select_frame_lay);
        resetBrand = (Button) findViewById(R.id.fram_reset_but);
        okBrand = (Button) findViewById(R.id.fram_ok_but);
        mWasteTypeLayout.setmEnableMultipleSelected(true);
        mInfoLayout.setmEnableMultipleSelected(true);
        mTimeLayout.setmEnableMultipleSelected(false);
    }

    private void initListener() {
        resetBrand.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setData(mMaps);
                mWasteTypeMap.clear();
                mInfoMap.clear();
                mTimeMap.clear();
            }
        });

        okBrand.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mRightSideListener.onMultipleSelectResult(getMultipleSelectList());
            }
        });

        mRelateLay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mWasteTypeLayout.setOnLabelItemClickListener(new SearchLabelContainerView.OnLabelItemListener() {
            @Override
            public void onLabelItemClick(SearchRecord label) {
                if (label.isChecked()) {
                    mWasteTypeMap.put(String.valueOf(label.getId()), label);
                } else {
                    if (mWasteTypeMap.containsKey(String.valueOf(label.getId()))) {
                        mWasteTypeMap.remove(String.valueOf(label.getId()));
                    }
                }
            }
        });
        mInfoLayout.setOnLabelItemClickListener(new SearchLabelContainerView.OnLabelItemListener() {
            @Override
            public void onLabelItemClick(SearchRecord label) {
                if (label.isChecked()) {
                    mInfoMap.put(String.valueOf(label.getId()), label);
                } else {
                    if (mInfoMap.containsKey(String.valueOf(label.getId()))) {
                        mInfoMap.remove(String.valueOf(label.getId()));
                    }
                }
            }
        });
        mTimeLayout.setOnLabelItemClickListener(new SearchLabelContainerView.OnLabelItemListener() {
            @Override
            public void onLabelItemClick(SearchRecord label) {
                if (label.isChecked()) {
                    mTimeMap.clear();
                    mTimeMap.put(String.valueOf(label.getId()), label);
                } else {
                    if (mTimeMap.containsKey(String.valueOf(label.getId()))) {
                        mTimeMap.remove(String.valueOf(label.getId()));
                    }
                }
            }
        });
    }

    public void setData(Map<Integer, List<SearchRecord>> map) {
        if (mMaps == null) {
            mMaps = map;
        }
        mWasteTypeLayout.setLabels(map.get(0));
        mInfoLayout.setLabels(map.get(1));
        mTimeLayout.setLabels(map.get(2));
    }

    public List<Map<String, SearchRecord>> getMultipleSelectList() {
        mMultipleSelectList.clear();
        mMultipleSelectList.add(mWasteTypeMap);
        mMultipleSelectList.add(mInfoMap);
        mMultipleSelectList.add(mTimeMap);
        return mMultipleSelectList;
    }

    public void setListener(RightSideListener rightSideListener) {
        mRightSideListener = rightSideListener;
    }

    public interface RightSideListener {
        void onMultipleSelectResult(List<Map<String, SearchRecord>> list);
    }
}
