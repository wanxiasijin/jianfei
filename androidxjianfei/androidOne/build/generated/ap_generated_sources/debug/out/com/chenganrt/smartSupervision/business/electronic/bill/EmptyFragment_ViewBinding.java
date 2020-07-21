// Generated code from Butter Knife. Do not modify!
package com.chenganrt.smartSupervision.business.electronic.bill;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.chenganrt.smartSupervision.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class EmptyFragment_ViewBinding implements Unbinder {
  private EmptyFragment target;

  @UiThread
  public EmptyFragment_ViewBinding(EmptyFragment target, View source) {
    this.target = target;

    target.layotEmpty = Utils.findRequiredViewAsType(source, R.id.layout_empty_fragment, "field 'layotEmpty'", LinearLayout.class);
    target.tvEmpty = Utils.findRequiredViewAsType(source, R.id.tv_empty, "field 'tvEmpty'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    EmptyFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.layotEmpty = null;
    target.tvEmpty = null;
  }
}
