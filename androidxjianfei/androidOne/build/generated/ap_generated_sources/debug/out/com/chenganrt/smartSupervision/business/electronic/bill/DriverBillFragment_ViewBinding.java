// Generated code from Butter Knife. Do not modify!
package com.chenganrt.smartSupervision.business.electronic.bill;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.chenganrt.smartSupervision.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class DriverBillFragment_ViewBinding implements Unbinder {
  private DriverBillFragment target;

  private View view7f090095;

  private View view7f0903a4;

  private View view7f090389;

  @UiThread
  public DriverBillFragment_ViewBinding(final DriverBillFragment target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.btn_submit, "field 'mBtnSubmit' and method 'onViewClick'");
    target.mBtnSubmit = Utils.castView(view, R.id.btn_submit, "field 'mBtnSubmit'", TextView.class);
    view7f090095 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClick(p0);
      }
    });
    target.mTvWaybill_number = Utils.findRequiredViewAsType(source, R.id.tv_waybill_number, "field 'mTvWaybill_number'", TextView.class);
    target.mTvOut_time = Utils.findRequiredViewAsType(source, R.id.tv_out_time, "field 'mTvOut_time'", TextView.class);
    target.mTvStatus = Utils.findRequiredViewAsType(source, R.id.tv_status, "field 'mTvStatus'", TextView.class);
    target.mTvWaste_types = Utils.findRequiredViewAsType(source, R.id.tv_waste_types, "field 'mTvWaste_types'", TextView.class);
    target.mTvLoading_amount = Utils.findRequiredViewAsType(source, R.id.tv_loading_amount, "field 'mTvLoading_amount'", TextView.class);
    view = Utils.findRequiredView(source, R.id.tv_number_plate2, "field 'mTvNumber_plate' and method 'onViewClick'");
    target.mTvNumber_plate = Utils.castView(view, R.id.tv_number_plate2, "field 'mTvNumber_plate'", TextView.class);
    view7f0903a4 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClick(p0);
      }
    });
    target.mTvStart_address = Utils.findRequiredViewAsType(source, R.id.tv_start_address, "field 'mTvStart_address'", TextView.class);
    target.mTvEnd_address = Utils.findRequiredViewAsType(source, R.id.tv_end_address, "field 'mTvEnd_address'", TextView.class);
    target.mTvProject_name = Utils.findRequiredViewAsType(source, R.id.tv_project_name, "field 'mTvProject_name'", TextView.class);
    target.mTvProject_address = Utils.findRequiredViewAsType(source, R.id.tv_project_address, "field 'mTvProject_address'", TextView.class);
    target.tvFecnyName = Utils.findRequiredViewAsType(source, R.id.tv_fency_name, "field 'tvFecnyName'", TextView.class);
    target.mTvBuild_unit = Utils.findRequiredViewAsType(source, R.id.tv_build_unit, "field 'mTvBuild_unit'", TextView.class);
    target.mTvSupervision_unit = Utils.findRequiredViewAsType(source, R.id.tv_supervision_unit, "field 'mTvSupervision_unit'", TextView.class);
    target.mTvConstruction_unit = Utils.findRequiredViewAsType(source, R.id.tv_construction_unit, "field 'mTvConstruction_unit'", TextView.class);
    target.mLiBtn = Utils.findRequiredViewAsType(source, R.id.ll_btn, "field 'mLiBtn'", LinearLayout.class);
    target.tvProject = Utils.findRequiredViewAsType(source, R.id.tv_project, "field 'tvProject'", TextView.class);
    target.layout_abandon = Utils.findRequiredViewAsType(source, R.id.layout_abandon, "field 'layout_abandon'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.tv_exception, "method 'onViewClick'");
    view7f090389 = view;
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
    DriverBillFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mBtnSubmit = null;
    target.mTvWaybill_number = null;
    target.mTvOut_time = null;
    target.mTvStatus = null;
    target.mTvWaste_types = null;
    target.mTvLoading_amount = null;
    target.mTvNumber_plate = null;
    target.mTvStart_address = null;
    target.mTvEnd_address = null;
    target.mTvProject_name = null;
    target.mTvProject_address = null;
    target.tvFecnyName = null;
    target.mTvBuild_unit = null;
    target.mTvSupervision_unit = null;
    target.mTvConstruction_unit = null;
    target.mLiBtn = null;
    target.tvProject = null;
    target.layout_abandon = null;

    view7f090095.setOnClickListener(null);
    view7f090095 = null;
    view7f0903a4.setOnClickListener(null);
    view7f0903a4 = null;
    view7f090389.setOnClickListener(null);
    view7f090389 = null;
  }
}
