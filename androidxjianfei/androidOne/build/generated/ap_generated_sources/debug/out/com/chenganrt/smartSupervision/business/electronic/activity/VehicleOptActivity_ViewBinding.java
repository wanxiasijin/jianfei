// Generated code from Butter Knife. Do not modify!
package com.chenganrt.smartSupervision.business.electronic.activity;

import android.view.View;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.chenganrt.smartSupervision.R;
import com.chenganrt.smartSupervision.business.electronic.ui.EditTextDel;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class VehicleOptActivity_ViewBinding implements Unbinder {
  private VehicleOptActivity target;

  @UiThread
  public VehicleOptActivity_ViewBinding(VehicleOptActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public VehicleOptActivity_ViewBinding(VehicleOptActivity target, View source) {
    this.target = target;

    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.recyclerView, "field 'recyclerView'", RecyclerView.class);
    target.smartRefreshLayout = Utils.findRequiredViewAsType(source, R.id.smarfresh, "field 'smartRefreshLayout'", SmartRefreshLayout.class);
    target.edVehicle = Utils.findRequiredViewAsType(source, R.id.ed_vehicle, "field 'edVehicle'", EditTextDel.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    VehicleOptActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.recyclerView = null;
    target.smartRefreshLayout = null;
    target.edVehicle = null;
  }
}
