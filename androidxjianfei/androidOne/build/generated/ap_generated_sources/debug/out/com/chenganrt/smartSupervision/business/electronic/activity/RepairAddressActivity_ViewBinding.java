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
import java.lang.IllegalStateException;
import java.lang.Override;

public class RepairAddressActivity_ViewBinding implements Unbinder {
  private RepairAddressActivity target;

  private View view7f090357;

  @UiThread
  public RepairAddressActivity_ViewBinding(RepairAddressActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public RepairAddressActivity_ViewBinding(final RepairAddressActivity target, View source) {
    this.target = target;

    View view;
    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.recyclerView, "field 'recyclerView'", RecyclerView.class);
    target.edAddress = Utils.findRequiredViewAsType(source, R.id.ed_address, "field 'edAddress'", EditTextDel.class);
    view = Utils.findRequiredView(source, R.id.tvCancel, "method 'onClickView'");
    view7f090357 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickView(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    RepairAddressActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.recyclerView = null;
    target.edAddress = null;

    view7f090357.setOnClickListener(null);
    view7f090357 = null;
  }
}
