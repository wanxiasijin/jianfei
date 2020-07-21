// Generated code from Butter Knife. Do not modify!
package com.chenganrt.smartSupervision.business.electronic.activity;

import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.chenganrt.smartSupervision.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AnalyseActivity_ViewBinding implements Unbinder {
  private AnalyseActivity target;

  private View view7f090284;

  private View view7f090285;

  private View view7f090287;

  private View view7f090286;

  @UiThread
  public AnalyseActivity_ViewBinding(AnalyseActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public AnalyseActivity_ViewBinding(final AnalyseActivity target, View source) {
    this.target = target;

    View view;
    target.radioGroup = Utils.findRequiredViewAsType(source, R.id.radiogroup, "field 'radioGroup'", RadioGroup.class);
    target.smartRefreshLayout = Utils.findRequiredViewAsType(source, R.id.smarfresh, "field 'smartRefreshLayout'", SmartRefreshLayout.class);
    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.recyclerView, "field 'recyclerView'", RecyclerView.class);
    view = Utils.findRequiredView(source, R.id.radio_more, "field 'radioMore' and method 'onViewClick'");
    target.radioMore = Utils.castView(view, R.id.radio_more, "field 'radioMore'", RadioButton.class);
    view7f090284 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.radio_today, "method 'onViewClick'");
    view7f090285 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.radio_yesterday, "method 'onViewClick'");
    view7f090287 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.radio_week, "method 'onViewClick'");
    view7f090286 = view;
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
    AnalyseActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.radioGroup = null;
    target.smartRefreshLayout = null;
    target.recyclerView = null;
    target.radioMore = null;

    view7f090284.setOnClickListener(null);
    view7f090284 = null;
    view7f090285.setOnClickListener(null);
    view7f090285 = null;
    view7f090287.setOnClickListener(null);
    view7f090287 = null;
    view7f090286.setOnClickListener(null);
    view7f090286 = null;
  }
}
