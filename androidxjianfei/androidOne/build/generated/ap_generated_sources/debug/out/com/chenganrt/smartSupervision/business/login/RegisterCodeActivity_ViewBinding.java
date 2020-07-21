// Generated code from Butter Knife. Do not modify!
package com.chenganrt.smartSupervision.business.login;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.chenganrt.smartSupervision.R;
import com.chenganrt.smartSupervision.business.login.view.CountDownView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class RegisterCodeActivity_ViewBinding implements Unbinder {
  private RegisterCodeActivity target;

  private View view7f0903c9;

  private View view7f09008a;

  @UiThread
  public RegisterCodeActivity_ViewBinding(RegisterCodeActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public RegisterCodeActivity_ViewBinding(final RegisterCodeActivity target, View source) {
    this.target = target;

    View view;
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.titleSub = Utils.findRequiredViewAsType(source, R.id.titleSub, "field 'titleSub'", TextView.class);
    target.mPhoneNumber = Utils.findRequiredViewAsType(source, R.id.et_account, "field 'mPhoneNumber'", EditText.class);
    view = Utils.findRequiredView(source, R.id.tv_verification_code, "field 'mCodeButton' and method 'viewOnClick'");
    target.mCodeButton = Utils.castView(view, R.id.tv_verification_code, "field 'mCodeButton'", CountDownView.class);
    view7f0903c9 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.viewOnClick(p0);
      }
    });
    target.mEtCode = Utils.findRequiredViewAsType(source, R.id.et_code, "field 'mEtCode'", EditText.class);
    view = Utils.findRequiredView(source, R.id.btn_nest, "field 'mNest' and method 'viewOnClick'");
    target.mNest = Utils.castView(view, R.id.btn_nest, "field 'mNest'", TextView.class);
    view7f09008a = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.viewOnClick(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    RegisterCodeActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.titleSub = null;
    target.mPhoneNumber = null;
    target.mCodeButton = null;
    target.mEtCode = null;
    target.mNest = null;

    view7f0903c9.setOnClickListener(null);
    view7f0903c9 = null;
    view7f09008a.setOnClickListener(null);
    view7f09008a = null;
  }
}
