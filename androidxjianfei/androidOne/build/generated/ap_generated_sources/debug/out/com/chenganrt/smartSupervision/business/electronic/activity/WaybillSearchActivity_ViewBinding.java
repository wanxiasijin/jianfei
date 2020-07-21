// Generated code from Butter Knife. Do not modify!
package com.chenganrt.smartSupervision.business.electronic.activity;

import android.view.View;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.chenganrt.smartSupervision.R;
import com.chenganrt.smartSupervision.business.electronic.ui.EditTextDel;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class WaybillSearchActivity_ViewBinding implements Unbinder {
  private WaybillSearchActivity target;

  private View view7f0900a0;

  @UiThread
  public WaybillSearchActivity_ViewBinding(WaybillSearchActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public WaybillSearchActivity_ViewBinding(final WaybillSearchActivity target, View source) {
    this.target = target;

    View view;
    target.mEdtAccount = Utils.findRequiredViewAsType(source, R.id.et_account, "field 'mEdtAccount'", EditTextDel.class);
    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.recyclerView, "field 'recyclerView'", RecyclerView.class);
    target.viewDrop = Utils.findRequiredView(source, R.id.view, "field 'viewDrop'");
    target.smartRefreshLayout = Utils.findRequiredViewAsType(source, R.id.smarfresh, "field 'smartRefreshLayout'", SmartRefreshLayout.class);
    view = Utils.findRequiredView(source, R.id.cancel, "method 'onViewClick'");
    view7f0900a0 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClick(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    WaybillSearchActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mEdtAccount = null;
    target.recyclerView = null;
    target.viewDrop = null;
    target.smartRefreshLayout = null;

    view7f0900a0.setOnClickListener(null);
    view7f0900a0 = null;
  }
}
