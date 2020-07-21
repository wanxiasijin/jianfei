// Generated code from Butter Knife. Do not modify!
package com.chenganrt.smartSupervision.business.electronic.bill;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.chenganrt.smartSupervision.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class EditBillFragment_ViewBinding implements Unbinder {
  private EditBillFragment target;

  private View view7f0903a3;

  private View view7f090384;

  private View view7f090095;

  private View view7f090360;

  private View view7f090191;

  @UiThread
  public EditBillFragment_ViewBinding(final EditBillFragment target, View source) {
    this.target = target;

    View view;
    target.mTvWaybill_number = Utils.findRequiredViewAsType(source, R.id.tv_waybill_number, "field 'mTvWaybill_number'", TextView.class);
    target.mTvOut_time = Utils.findRequiredViewAsType(source, R.id.tv_out_time, "field 'mTvOut_time'", TextView.class);
    target.mTvStatus = Utils.findRequiredViewAsType(source, R.id.tv_status, "field 'mTvStatus'", TextView.class);
    target.mTvWaste_types = Utils.findRequiredViewAsType(source, R.id.tv_waste_types, "field 'mTvWaste_types'", TextView.class);
    target.mTvLoading_amount = Utils.findRequiredViewAsType(source, R.id.tv_loading_amount, "field 'mTvLoading_amount'", TextView.class);
    view = Utils.findRequiredView(source, R.id.tv_number_plate1, "field 'mTvNumber_plate' and method 'onViewClick'");
    target.mTvNumber_plate = Utils.castView(view, R.id.tv_number_plate1, "field 'mTvNumber_plate'", TextView.class);
    view7f0903a3 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClick(p0);
      }
    });
    target.mTvStart_address = Utils.findRequiredViewAsType(source, R.id.tv_start_address, "field 'mTvStart_address'", TextView.class);
    target.mTvProject_name = Utils.findRequiredViewAsType(source, R.id.tv_project_name, "field 'mTvProject_name'", TextView.class);
    target.mTvProject_address = Utils.findRequiredViewAsType(source, R.id.tv_project_address, "field 'mTvProject_address'", TextView.class);
    target.tvFecnyName = Utils.findRequiredViewAsType(source, R.id.tv_fency_name, "field 'tvFecnyName'", TextView.class);
    target.mTvBuild_unit = Utils.findRequiredViewAsType(source, R.id.tv_build_unit, "field 'mTvBuild_unit'", TextView.class);
    target.mTvSupervision_unit = Utils.findRequiredViewAsType(source, R.id.tv_supervision_unit, "field 'mTvSupervision_unit'", TextView.class);
    target.mTvConstruction_unit = Utils.findRequiredViewAsType(source, R.id.tv_construction_unit, "field 'mTvConstruction_unit'", TextView.class);
    view = Utils.findRequiredView(source, R.id.tv_end_address, "field 'tvEndAddress' and method 'onViewClick'");
    target.tvEndAddress = Utils.castView(view, R.id.tv_end_address, "field 'tvEndAddress'", TextView.class);
    view7f090384 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClick(p0);
      }
    });
    target.mEt_loading_amount = Utils.findRequiredViewAsType(source, R.id.et_loading_amount, "field 'mEt_loading_amount'", EditText.class);
    target.tvPushTime = Utils.findRequiredViewAsType(source, R.id.tv_push_time, "field 'tvPushTime'", TextView.class);
    target.tvReciverTime = Utils.findRequiredViewAsType(source, R.id.tv_reciver_time, "field 'tvReciverTime'", TextView.class);
    target.tvEndTime = Utils.findRequiredViewAsType(source, R.id.tv_end_time, "field 'tvEndTime'", TextView.class);
    view = Utils.findRequiredView(source, R.id.btn_submit, "method 'onViewClick'");
    view7f090095 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_abnormal_report, "method 'onViewClick'");
    view7f090360 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.layout_category, "method 'onViewClick'");
    view7f090191 = view;
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
    EditBillFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mTvWaybill_number = null;
    target.mTvOut_time = null;
    target.mTvStatus = null;
    target.mTvWaste_types = null;
    target.mTvLoading_amount = null;
    target.mTvNumber_plate = null;
    target.mTvStart_address = null;
    target.mTvProject_name = null;
    target.mTvProject_address = null;
    target.tvFecnyName = null;
    target.mTvBuild_unit = null;
    target.mTvSupervision_unit = null;
    target.mTvConstruction_unit = null;
    target.tvEndAddress = null;
    target.mEt_loading_amount = null;
    target.tvPushTime = null;
    target.tvReciverTime = null;
    target.tvEndTime = null;

    view7f0903a3.setOnClickListener(null);
    view7f0903a3 = null;
    view7f090384.setOnClickListener(null);
    view7f090384 = null;
    view7f090095.setOnClickListener(null);
    view7f090095 = null;
    view7f090360.setOnClickListener(null);
    view7f090360 = null;
    view7f090191.setOnClickListener(null);
    view7f090191 = null;
  }
}
