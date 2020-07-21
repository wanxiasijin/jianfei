// Generated code from Butter Knife. Do not modify!
package com.chenganrt.smartSupervision.business.electronic.activity;

import android.view.View;
import android.widget.GridView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.chenganrt.smartSupervision.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SoilQualityActivity_ViewBinding implements Unbinder {
  private SoilQualityActivity target;

  @UiThread
  public SoilQualityActivity_ViewBinding(SoilQualityActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public SoilQualityActivity_ViewBinding(SoilQualityActivity target, View source) {
    this.target = target;

    target.gridView = Utils.findRequiredViewAsType(source, R.id.gridView, "field 'gridView'", GridView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SoilQualityActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.gridView = null;
  }
}
