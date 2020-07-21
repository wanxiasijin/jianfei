// Generated code from Butter Knife. Do not modify!
package com.chenganrt.smartSupervision.business.login;

import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.chenganrt.smartSupervision.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AgreementActivity_ViewBinding implements Unbinder {
  private AgreementActivity target;

  @UiThread
  public AgreementActivity_ViewBinding(AgreementActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public AgreementActivity_ViewBinding(AgreementActivity target, View source) {
    this.target = target;

    target.mWebView = Utils.findRequiredViewAsType(source, R.id.webview, "field 'mWebView'", WebView.class);
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.titleSub = Utils.findRequiredViewAsType(source, R.id.titleSub, "field 'titleSub'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AgreementActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mWebView = null;
    target.toolbar = null;
    target.titleSub = null;
  }
}
