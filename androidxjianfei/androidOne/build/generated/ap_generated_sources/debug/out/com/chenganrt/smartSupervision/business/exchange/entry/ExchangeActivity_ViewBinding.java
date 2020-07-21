// Generated code from Butter Knife. Do not modify!
package com.chenganrt.smartSupervision.business.exchange.entry;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.android.commonlib.view.flycotablayout.CommonTabLayout;
import com.chenganrt.smartSupervision.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ExchangeActivity_ViewBinding implements Unbinder {
  private ExchangeActivity target;

  private View view7f0901c8;

  private View view7f0901c9;

  private View view7f0901bc;

  private View view7f090391;

  @UiThread
  public ExchangeActivity_ViewBinding(ExchangeActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ExchangeActivity_ViewBinding(final ExchangeActivity target, View source) {
    this.target = target;

    View view;
    target.mIvTop = Utils.findRequiredViewAsType(source, R.id.iv_top, "field 'mIvTop'", ImageView.class);
    view = Utils.findRequiredView(source, R.id.ll_publish, "field 'mPublish' and method 'onViewClicked'");
    target.mPublish = Utils.castView(view, R.id.ll_publish, "field 'mPublish'", LinearLayout.class);
    view7f0901c8 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.ll_published, "field 'mPublished' and method 'onViewClicked'");
    target.mPublished = Utils.castView(view, R.id.ll_published, "field 'mPublished'", LinearLayout.class);
    view7f0901c9 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.ll_collect, "field 'mCollect' and method 'onViewClicked'");
    target.mCollect = Utils.castView(view, R.id.ll_collect, "field 'mCollect'", LinearLayout.class);
    view7f0901bc = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.mWasteSupply = Utils.findRequiredViewAsType(source, R.id.tv_waste_supply_value, "field 'mWasteSupply'", TextView.class);
    target.mWasteDemand = Utils.findRequiredViewAsType(source, R.id.tv_waste_demand_value, "field 'mWasteDemand'", TextView.class);
    target.mAllSupply = Utils.findRequiredViewAsType(source, R.id.tv_all_supply_value, "field 'mAllSupply'", TextView.class);
    target.mALLDemand = Utils.findRequiredViewAsType(source, R.id.tv_all_demand_value, "field 'mALLDemand'", TextView.class);
    target.mCommonTabLayout = Utils.findRequiredViewAsType(source, R.id.common_tab_layout, "field 'mCommonTabLayout'", CommonTabLayout.class);
    target.mSmartRefreshLayout = Utils.findRequiredViewAsType(source, R.id.smart_fresh, "field 'mSmartRefreshLayout'", SmartRefreshLayout.class);
    view = Utils.findRequiredView(source, R.id.tv_info, "field 'mInfo' and method 'onViewClicked'");
    target.mInfo = Utils.castView(view, R.id.tv_info, "field 'mInfo'", TextView.class);
    view7f090391 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    ExchangeActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mIvTop = null;
    target.mPublish = null;
    target.mPublished = null;
    target.mCollect = null;
    target.mWasteSupply = null;
    target.mWasteDemand = null;
    target.mAllSupply = null;
    target.mALLDemand = null;
    target.mCommonTabLayout = null;
    target.mSmartRefreshLayout = null;
    target.mInfo = null;

    view7f0901c8.setOnClickListener(null);
    view7f0901c8 = null;
    view7f0901c9.setOnClickListener(null);
    view7f0901c9 = null;
    view7f0901bc.setOnClickListener(null);
    view7f0901bc = null;
    view7f090391.setOnClickListener(null);
    view7f090391 = null;
  }
}
