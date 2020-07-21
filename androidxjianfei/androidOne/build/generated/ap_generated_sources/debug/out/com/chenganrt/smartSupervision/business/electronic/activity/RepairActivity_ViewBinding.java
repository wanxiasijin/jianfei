// Generated code from Butter Knife. Do not modify!
package com.chenganrt.smartSupervision.business.electronic.activity;

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

public class RepairActivity_ViewBinding implements Unbinder {
  private RepairActivity target;

  private View view7f090384;

  private View view7f090191;

  private View view7f090198;

  private View view7f09019d;

  private View view7f0901a4;

  private View view7f090360;

  private View view7f090095;

  @UiThread
  public RepairActivity_ViewBinding(RepairActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public RepairActivity_ViewBinding(final RepairActivity target, View source) {
    this.target = target;

    View view;
    target.siteName = Utils.findRequiredViewAsType(source, R.id.tv_project_name, "field 'siteName'", TextView.class);
    target.siteAddress = Utils.findRequiredViewAsType(source, R.id.tv_project_address, "field 'siteAddress'", TextView.class);
    target.siteFency = Utils.findRequiredViewAsType(source, R.id.tv_fency_name, "field 'siteFency'", TextView.class);
    target.siteUnit = Utils.findRequiredViewAsType(source, R.id.tv_build_unit, "field 'siteUnit'", TextView.class);
    target.siteSupervise = Utils.findRequiredViewAsType(source, R.id.tv_supervision_unit, "field 'siteSupervise'", TextView.class);
    target.siteBuild = Utils.findRequiredViewAsType(source, R.id.tv_construction_unit, "field 'siteBuild'", TextView.class);
    target.mTvWaste_types = Utils.findRequiredViewAsType(source, R.id.tv_waste_types, "field 'mTvWaste_types'", TextView.class);
    view = Utils.findRequiredView(source, R.id.tv_end_address, "field 'tvEndAddress' and method 'onClickView'");
    target.tvEndAddress = Utils.castView(view, R.id.tv_end_address, "field 'tvEndAddress'", TextView.class);
    view7f090384 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickView(p0);
      }
    });
    target.edMount = Utils.findRequiredViewAsType(source, R.id.et_loading_amount, "field 'edMount'", EditText.class);
    target.tvLisence = Utils.findRequiredViewAsType(source, R.id.tv_lisence, "field 'tvLisence'", TextView.class);
    target.timeStart = Utils.findRequiredViewAsType(source, R.id.time_in, "field 'timeStart'", TextView.class);
    target.timeEnd = Utils.findRequiredViewAsType(source, R.id.time_out, "field 'timeEnd'", TextView.class);
    target.mTvStart_address = Utils.findRequiredViewAsType(source, R.id.tv_start_address, "field 'mTvStart_address'", TextView.class);
    target.edMark = Utils.findRequiredViewAsType(source, R.id.ed_mark, "field 'edMark'", EditText.class);
    view = Utils.findRequiredView(source, R.id.layout_category, "method 'onClickView'");
    view7f090191 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickView(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.layout_in, "method 'onClickView'");
    view7f090198 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickView(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.layout_out, "method 'onClickView'");
    view7f09019d = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickView(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.layout_vehicle, "method 'onClickView'");
    view7f0901a4 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickView(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_abnormal_report, "method 'onClickView'");
    view7f090360 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickView(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_submit, "method 'onClickView'");
    view7f090095 = view;
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
    RepairActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.siteName = null;
    target.siteAddress = null;
    target.siteFency = null;
    target.siteUnit = null;
    target.siteSupervise = null;
    target.siteBuild = null;
    target.mTvWaste_types = null;
    target.tvEndAddress = null;
    target.edMount = null;
    target.tvLisence = null;
    target.timeStart = null;
    target.timeEnd = null;
    target.mTvStart_address = null;
    target.edMark = null;

    view7f090384.setOnClickListener(null);
    view7f090384 = null;
    view7f090191.setOnClickListener(null);
    view7f090191 = null;
    view7f090198.setOnClickListener(null);
    view7f090198 = null;
    view7f09019d.setOnClickListener(null);
    view7f09019d = null;
    view7f0901a4.setOnClickListener(null);
    view7f0901a4 = null;
    view7f090360.setOnClickListener(null);
    view7f090360 = null;
    view7f090095.setOnClickListener(null);
    view7f090095 = null;
  }
}
