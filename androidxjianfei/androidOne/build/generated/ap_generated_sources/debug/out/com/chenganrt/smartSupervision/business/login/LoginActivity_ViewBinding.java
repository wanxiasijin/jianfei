// Generated code from Butter Knife. Do not modify!
package com.chenganrt.smartSupervision.business.login;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.chenganrt.smartSupervision.R;
import com.chenganrt.smartSupervision.business.login.view.CountDownView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class LoginActivity_ViewBinding implements Unbinder {
  private LoginActivity target;

  private View view7f090173;

  private View view7f090132;

  private View view7f090088;

  private View view7f09008d;

  private View view7f090209;

  private View view7f090362;

  @UiThread
  public LoginActivity_ViewBinding(LoginActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public LoginActivity_ViewBinding(final LoginActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.iv_back, "field 'mBack' and method 'viewOnClick'");
    target.mBack = Utils.castView(view, R.id.iv_back, "field 'mBack'", ImageView.class);
    view7f090173 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.viewOnClick(p0);
      }
    });
    target.mAccount = Utils.findRequiredViewAsType(source, R.id.et_account, "field 'mAccount'", EditText.class);
    target.mPassword = Utils.findRequiredViewAsType(source, R.id.et_password, "field 'mPassword'", EditText.class);
    view = Utils.findRequiredView(source, R.id.forget_password, "field 'mForgetPassword' and method 'viewOnClick'");
    target.mForgetPassword = Utils.castView(view, R.id.forget_password, "field 'mForgetPassword'", CountDownView.class);
    view7f090132 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.viewOnClick(p0);
      }
    });
    target.mAccept = Utils.findRequiredViewAsType(source, R.id.rb_accept, "field 'mAccept'", RadioButton.class);
    view = Utils.findRequiredView(source, R.id.btn_login, "field 'mLogin' and method 'viewOnClick'");
    target.mLogin = Utils.castView(view, R.id.btn_login, "field 'mLogin'", TextView.class);
    view7f090088 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.viewOnClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_register, "field 'mRegister' and method 'viewOnClick'");
    target.mRegister = Utils.castView(view, R.id.btn_register, "field 'mRegister'", TextView.class);
    view7f09008d = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.viewOnClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.message_login, "field 'mMessageLogin' and method 'viewOnClick'");
    target.mMessageLogin = Utils.castView(view, R.id.message_login, "field 'mMessageLogin'", TextView.class);
    view7f090209 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.viewOnClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_accept_content, "method 'viewOnClick'");
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
    LoginActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mBack = null;
    target.mAccount = null;
    target.mPassword = null;
    target.mForgetPassword = null;
    target.mAccept = null;
    target.mLogin = null;
    target.mRegister = null;
    target.mMessageLogin = null;

    view7f090173.setOnClickListener(null);
    view7f090173 = null;
    view7f090132.setOnClickListener(null);
    view7f090132 = null;
    view7f090088.setOnClickListener(null);
    view7f090088 = null;
    view7f09008d.setOnClickListener(null);
    view7f09008d = null;
    view7f090209.setOnClickListener(null);
    view7f090209 = null;
    view7f090362.setOnClickListener(null);
    view7f090362 = null;
  }
}
