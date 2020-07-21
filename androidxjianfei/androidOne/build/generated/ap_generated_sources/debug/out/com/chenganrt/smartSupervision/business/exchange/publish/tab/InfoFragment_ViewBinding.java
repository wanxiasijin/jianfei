// Generated code from Butter Knife. Do not modify!
package com.chenganrt.smartSupervision.business.exchange.publish.tab;

import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.chenganrt.smartSupervision.R;
import com.chenganrt.smartSupervision.business.exchange.publish.calendar.CalendarView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class InfoFragment_ViewBinding implements Unbinder {
  private InfoFragment target;

  private View view7f0902ad;

  private View view7f0902a1;

  private View view7f0902ac;

  private View view7f0902a5;

  @UiThread
  public InfoFragment_ViewBinding(final InfoFragment target, View source) {
    this.target = target;

    View view;
    target.mScrollView = Utils.findRequiredViewAsType(source, R.id.sl_publish, "field 'mScrollView'", ScrollView.class);
    view = Utils.findRequiredView(source, R.id.rl_waste_type, "field 'mRlWasteType' and method 'onViewClicked'");
    target.mRlWasteType = Utils.castView(view, R.id.rl_waste_type, "field 'mRlWasteType'", RelativeLayout.class);
    view7f0902ad = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.mWasteType = Utils.findRequiredViewAsType(source, R.id.waste_type, "field 'mWasteType'", TextView.class);
    target.mTitle = Utils.findRequiredViewAsType(source, R.id.et_title, "field 'mTitle'", EditText.class);
    view = Utils.findRequiredView(source, R.id.rl_area, "field 'mRlArea' and method 'onViewClicked'");
    target.mRlArea = Utils.castView(view, R.id.rl_area, "field 'mRlArea'", RelativeLayout.class);
    view7f0902a1 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.mArea = Utils.findRequiredViewAsType(source, R.id.area, "field 'mArea'", TextView.class);
    target.mAddress = Utils.findRequiredViewAsType(source, R.id.et_address, "field 'mAddress'", EditText.class);
    view = Utils.findRequiredView(source, R.id.rl_type, "field 'mRlType' and method 'onViewClicked'");
    target.mRlType = Utils.castView(view, R.id.rl_type, "field 'mRlType'", RelativeLayout.class);
    view7f0902ac = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.mType = Utils.findRequiredViewAsType(source, R.id.type, "field 'mType'", TextView.class);
    target.mAmount = Utils.findRequiredViewAsType(source, R.id.et_amount, "field 'mAmount'", EditText.class);
    target.mTime = Utils.findRequiredViewAsType(source, R.id.et_time, "field 'mTime'", TextView.class);
    target.mContact = Utils.findRequiredViewAsType(source, R.id.et_contact, "field 'mContact'", EditText.class);
    target.mTel = Utils.findRequiredViewAsType(source, R.id.et_tel, "field 'mTel'", EditText.class);
    target.mDetail = Utils.findRequiredViewAsType(source, R.id.et_detail, "field 'mDetail'", EditText.class);
    target.mRecyclerView = Utils.findRequiredViewAsType(source, R.id.recycler, "field 'mRecyclerView'", RecyclerView.class);
    target.mCalendar = Utils.findRequiredViewAsType(source, R.id.calendar_view, "field 'mCalendar'", CalendarView.class);
    target.mCardview = Utils.findRequiredViewAsType(source, R.id.cardview, "field 'mCardview'", CardView.class);
    view = Utils.findRequiredView(source, R.id.rl_end_time, "method 'onViewClicked'");
    view7f0902a5 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    InfoFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mScrollView = null;
    target.mRlWasteType = null;
    target.mWasteType = null;
    target.mTitle = null;
    target.mRlArea = null;
    target.mArea = null;
    target.mAddress = null;
    target.mRlType = null;
    target.mType = null;
    target.mAmount = null;
    target.mTime = null;
    target.mContact = null;
    target.mTel = null;
    target.mDetail = null;
    target.mRecyclerView = null;
    target.mCalendar = null;
    target.mCardview = null;

    view7f0902ad.setOnClickListener(null);
    view7f0902ad = null;
    view7f0902a1.setOnClickListener(null);
    view7f0902a1 = null;
    view7f0902ac.setOnClickListener(null);
    view7f0902ac = null;
    view7f0902a5.setOnClickListener(null);
    view7f0902a5 = null;
  }
}
