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
import java.lang.IllegalStateException;
import java.lang.Override;

public class ResetPasswordActivity_ViewBinding implements Unbinder {
  private ResetPasswordActivity target;

  private View view7f09007e;

  @UiThread
  public ResetPasswordActivity_ViewBinding(ResetPasswordActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ResetPasswordActivity_ViewBinding(final ResetPasswordActivity target, View source) {
    this.target = target;

    View view;
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.titleSub = Utils.findRequiredViewAsType(source, R.id.titleSub, "field 'titleSub'", TextView.class);
    target.mPassword = Utils.findRequiredViewAsType(source, R.id.et_password, "field 'mPassword'", EditText.class);
    target.mPasswordAgain = Utils.findRequiredViewAsType(source, R.id.et_password_again, "field 'mPasswordAgain'", EditText.class);
    view = Utils.findRequiredView(source, R.id.btn_accept, "field 'mNest' and method 'viewOnClick'");
    target.mNest = Utils.castView(view, R.id.btn_accept, "field 'mNest'", TextView.class);
    view7f09007e = view;
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
    ResetPasswordActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.titleSub = null;
    target.mPassword = null;
    target.mPasswordAgain = null;
    target.mNest = null;

    view7f09007e.setOnClickListener(null);
    view7f09007e = null;
  }
}
