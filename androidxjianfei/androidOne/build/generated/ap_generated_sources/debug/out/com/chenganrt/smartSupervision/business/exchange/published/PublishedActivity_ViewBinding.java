// Generated code from Butter Knife. Do not modify!
package com.chenganrt.smartSupervision.business.exchange.published;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.android.commonlib.view.flycotablayout.CommonTabLayout;
import com.chenganrt.smartSupervision.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class PublishedActivity_ViewBinding implements Unbinder {
  private PublishedActivity target;

  @UiThread
  public PublishedActivity_ViewBinding(PublishedActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public PublishedActivity_ViewBinding(PublishedActivity target, View source) {
    this.target = target;

    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.titleSub = Utils.findRequiredViewAsType(source, R.id.titleSub, "field 'titleSub'", TextView.class);
    target.mCommonTabLayout = Utils.findRequiredViewAsType(source, R.id.common_tab_layout, "field 'mCommonTabLayout'", CommonTabLayout.class);
    target.mSmartRefreshLayout = Utils.findRequiredViewAsType(source, R.id.smart_fresh, "field 'mSmartRefreshLayout'", SmartRefreshLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    PublishedActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.titleSub = null;
    target.mCommonTabLayout = null;
    target.mSmartRefreshLayout = null;
  }
}
