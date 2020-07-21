// Generated code from Butter Knife. Do not modify!
package com.chenganrt.smartSupervision.business.exchange.detail;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.chenganrt.smartSupervision.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class InfoDetailActivity_ViewBinding implements Unbinder {
  private InfoDetailActivity target;

  private View view7f090377;

  private View view7f090382;

  @UiThread
  public InfoDetailActivity_ViewBinding(InfoDetailActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public InfoDetailActivity_ViewBinding(final InfoDetailActivity target, View source) {
    this.target = target;

    View view;
    target.mRootLayout = Utils.findRequiredViewAsType(source, R.id.rl_main, "field 'mRootLayout'", RelativeLayout.class);
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.titleSub = Utils.findRequiredViewAsType(source, R.id.titleSub, "field 'titleSub'", TextView.class);
    target.mTitle = Utils.findRequiredViewAsType(source, R.id.title, "field 'mTitle'", TextView.class);
    target.mAddress = Utils.findRequiredViewAsType(source, R.id.address, "field 'mAddress'", TextView.class);
    target.mAmount = Utils.findRequiredViewAsType(source, R.id.amount, "field 'mAmount'", TextView.class);
    target.mArea = Utils.findRequiredViewAsType(source, R.id.area, "field 'mArea'", TextView.class);
    target.mViewsValue = Utils.findRequiredViewAsType(source, R.id.views_value, "field 'mViewsValue'", TextView.class);
    target.mCollectValue = Utils.findRequiredViewAsType(source, R.id.collect_value, "field 'mCollectValue'", TextView.class);
    target.mcontact = Utils.findRequiredViewAsType(source, R.id.contact, "field 'mcontact'", TextView.class);
    target.mEndTime = Utils.findRequiredViewAsType(source, R.id.end_time, "field 'mEndTime'", TextView.class);
    target.mCreateTime = Utils.findRequiredViewAsType(source, R.id.create_time, "field 'mCreateTime'", TextView.class);
    target.mDetail = Utils.findRequiredViewAsType(source, R.id.detail, "field 'mDetail'", TextView.class);
    view = Utils.findRequiredView(source, R.id.tv_collect, "field 'mCollect' and method 'onViewClicked'");
    target.mCollect = Utils.castView(view, R.id.tv_collect, "field 'mCollect'", TextView.class);
    view7f090377 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_edit, "field 'mEdit' and method 'onViewClicked'");
    target.mEdit = Utils.castView(view, R.id.tv_edit, "field 'mEdit'", TextView.class);
    view7f090382 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.mRecyclerView = Utils.findRequiredViewAsType(source, R.id.recycler, "field 'mRecyclerView'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    InfoDetailActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mRootLayout = null;
    target.toolbar = null;
    target.titleSub = null;
    target.mTitle = null;
    target.mAddress = null;
    target.mAmount = null;
    target.mArea = null;
    target.mViewsValue = null;
    target.mCollectValue = null;
    target.mcontact = null;
    target.mEndTime = null;
    target.mCreateTime = null;
    target.mDetail = null;
    target.mCollect = null;
    target.mEdit = null;
    target.mRecyclerView = null;

    view7f090377.setOnClickListener(null);
    view7f090377 = null;
    view7f090382.setOnClickListener(null);
    view7f090382 = null;
  }
}
