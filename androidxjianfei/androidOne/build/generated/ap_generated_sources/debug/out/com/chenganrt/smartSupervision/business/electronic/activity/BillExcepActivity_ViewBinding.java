// Generated code from Butter Knife. Do not modify!
package com.chenganrt.smartSupervision.business.electronic.activity;

import android.view.View;
import android.widget.EditText;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.chenganrt.smartSupervision.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class BillExcepActivity_ViewBinding implements Unbinder {
  private BillExcepActivity target;

  private View view7f0903ba;

  @UiThread
  public BillExcepActivity_ViewBinding(BillExcepActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public BillExcepActivity_ViewBinding(final BillExcepActivity target, View source) {
    this.target = target;

    View view;
    target.edDescript = Utils.findRequiredViewAsType(source, R.id.ed_description, "field 'edDescript'", EditText.class);
    view = Utils.findRequiredView(source, R.id.tv_submit, "method 'onViewClick'");
    view7f0903ba = view;
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
    BillExcepActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.edDescript = null;

    view7f0903ba.setOnClickListener(null);
    view7f0903ba = null;
  }
}
