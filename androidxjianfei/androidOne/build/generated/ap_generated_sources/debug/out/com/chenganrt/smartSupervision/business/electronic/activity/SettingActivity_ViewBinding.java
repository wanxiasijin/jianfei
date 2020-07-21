// Generated code from Butter Knife. Do not modify!
package com.chenganrt.smartSupervision.business.electronic.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.chenganrt.smartSupervision.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SettingActivity_ViewBinding implements Unbinder {
  private SettingActivity target;

  private View view7f090121;

  private View view7f090199;

  private View view7f09019e;

  private View view7f09018f;

  private View view7f0903b5;

  private View view7f0901a5;

  @UiThread
  public SettingActivity_ViewBinding(SettingActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public SettingActivity_ViewBinding(final SettingActivity target, View source) {
    this.target = target;

    View view;
    target.tvCompany = Utils.findRequiredViewAsType(source, R.id.tv_company_name, "field 'tvCompany'", TextView.class);
    target.tvName = Utils.findRequiredViewAsType(source, R.id.tv_name, "field 'tvName'", TextView.class);
    target.tvVersion = Utils.findRequiredViewAsType(source, R.id.tv_version, "field 'tvVersion'", TextView.class);
    target.tabNum = Utils.findRequiredViewAsType(source, R.id.tab_message, "field 'tabNum'", TextView.class);
    target.fenceName = Utils.findRequiredViewAsType(source, R.id.tv_fence_name, "field 'fenceName'", TextView.class);
    view = Utils.findRequiredView(source, R.id.fence_status, "field 'fenceStatus' and method 'onViewClick'");
    target.fenceStatus = Utils.castView(view, R.id.fence_status, "field 'fenceStatus'", TextView.class);
    view7f090121 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClick(p0);
      }
    });
    target.imageRed = Utils.findRequiredViewAsType(source, R.id.image_red, "field 'imageRed'", ImageView.class);
    view = Utils.findRequiredView(source, R.id.layout_message, "method 'onViewClick'");
    view7f090199 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.layout_paw, "method 'onViewClick'");
    view7f09019e = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.layout_about, "method 'onViewClick'");
    view7f09018f = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_sign_out, "method 'onViewClick'");
    view7f0903b5 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.layout_version, "method 'onViewClick'");
    view7f0901a5 = view;
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
    SettingActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvCompany = null;
    target.tvName = null;
    target.tvVersion = null;
    target.tabNum = null;
    target.fenceName = null;
    target.fenceStatus = null;
    target.imageRed = null;

    view7f090121.setOnClickListener(null);
    view7f090121 = null;
    view7f090199.setOnClickListener(null);
    view7f090199 = null;
    view7f09019e.setOnClickListener(null);
    view7f09019e = null;
    view7f09018f.setOnClickListener(null);
    view7f09018f = null;
    view7f0903b5.setOnClickListener(null);
    view7f0903b5 = null;
    view7f0901a5.setOnClickListener(null);
    view7f0901a5 = null;
  }
}
