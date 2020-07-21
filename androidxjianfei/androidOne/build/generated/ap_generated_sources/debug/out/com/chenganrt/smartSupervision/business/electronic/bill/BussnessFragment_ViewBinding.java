// Generated code from Butter Knife. Do not modify!
package com.chenganrt.smartSupervision.business.electronic.bill;

import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.viewpager.widget.ViewPager;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.android.commonlib.view.flycotablayout.SlidingTabLayout;
import com.chenganrt.smartSupervision.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class BussnessFragment_ViewBinding implements Unbinder {
  private BussnessFragment target;

  private View view7f090357;

  @UiThread
  public BussnessFragment_ViewBinding(final BussnessFragment target, View source) {
    this.target = target;

    View view;
    target.slidingTabLayout = Utils.findRequiredViewAsType(source, R.id.slidingtablayout, "field 'slidingTabLayout'", SlidingTabLayout.class);
    target.viewPager = Utils.findRequiredViewAsType(source, R.id.viewPager, "field 'viewPager'", ViewPager.class);
    target.layoutSerach = Utils.findRequiredViewAsType(source, R.id.layout_search, "field 'layoutSerach'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.tvCancel, "field 'tvCancel' and method 'onViewClick'");
    target.tvCancel = Utils.castView(view, R.id.tvCancel, "field 'tvCancel'", TextView.class);
    view7f090357 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClick(p0);
      }
    });
    target.editText = Utils.findRequiredViewAsType(source, R.id.ed_content, "field 'editText'", EditText.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    BussnessFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.slidingTabLayout = null;
    target.viewPager = null;
    target.layoutSerach = null;
    target.tvCancel = null;
    target.editText = null;

    view7f090357.setOnClickListener(null);
    view7f090357 = null;
  }
}
