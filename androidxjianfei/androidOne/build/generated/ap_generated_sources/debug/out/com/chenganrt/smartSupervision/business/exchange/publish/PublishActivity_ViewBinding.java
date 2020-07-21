// Generated code from Butter Knife. Do not modify!
package com.chenganrt.smartSupervision.business.exchange.publish;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.chenganrt.smartSupervision.R;
import com.flyco.tablayout.CommonTabLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class PublishActivity_ViewBinding implements Unbinder {
  private PublishActivity target;

  private View view7f090371;

  private View view7f090095;

  @UiThread
  public PublishActivity_ViewBinding(PublishActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public PublishActivity_ViewBinding(final PublishActivity target, View source) {
    this.target = target;

    View view;
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.titleSub = Utils.findRequiredViewAsType(source, R.id.titleSub, "field 'titleSub'", TextView.class);
    target.mCommonTabLayout = Utils.findRequiredViewAsType(source, R.id.common_tab_layout, "field 'mCommonTabLayout'", CommonTabLayout.class);
    view = Utils.findRequiredView(source, R.id.tv_cancel, "method 'onViewClicked'");
    view7f090371 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_submit, "method 'onViewClicked'");
    view7f090095 = view;
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
    PublishActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.titleSub = null;
    target.mCommonTabLayout = null;

    view7f090371.setOnClickListener(null);
    view7f090371 = null;
    view7f090095.setOnClickListener(null);
    view7f090095 = null;
  }
}
