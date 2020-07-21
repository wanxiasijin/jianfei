// Generated code from Butter Knife. Do not modify!
package com.chenganrt.smartSupervision.business.login;

import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
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

public class RegisterActivity_ViewBinding implements Unbinder {
  private RegisterActivity target;

  private View view7f09008a;

  private View view7f090362;

  @UiThread
  public RegisterActivity_ViewBinding(RegisterActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public RegisterActivity_ViewBinding(final RegisterActivity target, View source) {
    this.target = target;

    View view;
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.titleSub = Utils.findRequiredViewAsType(source, R.id.titleSub, "field 'titleSub'", TextView.class);
    target.mAccount = Utils.findRequiredViewAsType(source, R.id.et_account, "field 'mAccount'", EditText.class);
    target.mPassword = Utils.findRequiredViewAsType(source, R.id.et_password, "field 'mPassword'", EditText.class);
    target.mPasswordAgain = Utils.findRequiredViewAsType(source, R.id.et_password_again, "field 'mPasswordAgain'", EditText.class);
    target.mCompany = Utils.findRequiredViewAsType(source, R.id.et_company, "field 'mCompany'", EditText.class);
    target.mPhoneNum = Utils.findRequiredViewAsType(source, R.id.et_number, "field 'mPhoneNum'", EditText.class);
    target.mCode = Utils.findRequiredViewAsType(source, R.id.et_code, "field 'mCode'", EditText.class);
    target.mCodeButton = Utils.findRequiredViewAsType(source, R.id.tv_verification_code, "field 'mCodeButton'", CountDownView.class);
    view = Utils.findRequiredView(source, R.id.btn_nest, "field 'mNest' and method 'viewOnClick'");
    target.mNest = Utils.castView(view, R.id.btn_nest, "field 'mNest'", TextView.class);
    view7f09008a = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.viewOnClick(p0);
      }
    });
    target.mAccept = Utils.findRequiredViewAsType(source, R.id.rb_accept, "field 'mAccept'", RadioButton.class);
    view = Utils.findRequiredView(source, R.id.tv_accept_content, "field 'mAcceptment' and method 'viewOnClick'");
    target.mAcceptment = Utils.castView(view, R.id.tv_accept_content, "field 'mAcceptment'", TextView.class);
    view7f090362 = view;
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
    RegisterActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.titleSub = null;
    target.mAccount = null;
    target.mPassword = null;
    target.mPasswordAgain = null;
    target.mCompany = null;
    target.mPhoneNum = null;
    target.mCode = null;
    target.mCodeButton = null;
    target.mNest = null;
    target.mAccept = null;
    target.mAcceptment = null;

    view7f09008a.setOnClickListener(null);
    view7f09008a = null;
    view7f090362.setOnClickListener(null);
    view7f090362 = null;
  }
}
