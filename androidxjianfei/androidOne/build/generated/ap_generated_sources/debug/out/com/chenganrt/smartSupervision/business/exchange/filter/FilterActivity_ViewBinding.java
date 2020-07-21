// Generated code from Butter Knife. Do not modify!
package com.chenganrt.smartSupervision.business.exchange.filter;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.drawerlayout.widget.DrawerLayout;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.chenganrt.smartSupervision.R;
import com.chenganrt.smartSupervision.business.exchange.filter.view.FilterTabView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FilterActivity_ViewBinding implements Unbinder {
  private FilterActivity target;

  private View view7f09007f;

  private View view7f09011a;

  private View view7f0903c5;

  @UiThread
  public FilterActivity_ViewBinding(FilterActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public FilterActivity_ViewBinding(final FilterActivity target, View source) {
    this.target = target;

    View view;
    target.mToolbar = Utils.findRequiredViewAsType(source, R.id.search_toolbar, "field 'mToolbar'", RelativeLayout.class);
    target.mDrawer = Utils.findRequiredViewAsType(source, R.id.drawer_layout, "field 'mDrawer'", DrawerLayout.class);
    view = Utils.findRequiredView(source, R.id.btn_back, "field 'mBack' and method 'onViewClicked'");
    target.mBack = Utils.castView(view, R.id.btn_back, "field 'mBack'", ImageView.class);
    view7f09007f = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.et_toolbar_search, "field 'mSearchContent' and method 'onViewClicked'");
    target.mSearchContent = Utils.castView(view, R.id.et_toolbar_search, "field 'mSearchContent'", EditText.class);
    view7f09011a = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_toolbar_search, "field 'mSearch' and method 'onViewClicked'");
    target.mSearch = Utils.castView(view, R.id.tv_toolbar_search, "field 'mSearch'", TextView.class);
    view7f0903c5 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.ftb_filter = Utils.findRequiredViewAsType(source, R.id.ftb_filter, "field 'ftb_filter'", FilterTabView.class);
    target.mSlideFilterView = Utils.findRequiredViewAsType(source, R.id.nav_view, "field 'mSlideFilterView'", RelativeLayout.class);
    target.mSmartRefreshLayout = Utils.findRequiredViewAsType(source, R.id.smart_fresh, "field 'mSmartRefreshLayout'", SmartRefreshLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    FilterActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mToolbar = null;
    target.mDrawer = null;
    target.mBack = null;
    target.mSearchContent = null;
    target.mSearch = null;
    target.ftb_filter = null;
    target.mSlideFilterView = null;
    target.mSmartRefreshLayout = null;

    view7f09007f.setOnClickListener(null);
    view7f09007f = null;
    view7f09011a.setOnClickListener(null);
    view7f09011a = null;
    view7f0903c5.setOnClickListener(null);
    view7f0903c5 = null;
  }
}
