package com.chenganrt.smartSupervision.business.personal;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.android.commonlib.view.buttomdialog.ShareBottomPopupDialog;
import com.chenganrt.smartSupervision.R;
import com.chenganrt.smartSupervision.business.exchange.collect.CollectedActivity;
import com.chenganrt.smartSupervision.business.exchange.published.PublishedActivity;
import com.chenganrt.smartSupervision.business.login.LoginActivity;
import com.chenganrt.smartSupervision.business.login.UserSP;
import com.chenganrt.smartSupervision.common.okhttputil.CallBackUtil;
import com.chenganrt.smartSupervision.common.okhttputil.HttpConstant;
import com.chenganrt.smartSupervision.common.okhttputil.OkhttpUtil;
import com.chenganrt.smartSupervision.model.response.UserDateBeanParser;
import com.chenganrt.smartSupervision.model.response.UserDdtaBean;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import timber.log.Timber;

import static com.chenganrt.smartSupervision.business.login.LoginActivity.LOGIN_REQUSET_CODE;

public class PersonalFragment extends Fragment implements View.OnClickListener {
    private LinearLayout ll1, ll2, ll_share;
    private TextView tv1, tv3, tv4, login;
    private Button bt_cancel;
    private Button bt_tel;
    private LinearLayout ll_main;
    private ImageView iv_setting;

    private LinearLayout ll_userfeed, ll_aboutme, ll_contact_me;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.personalcenter_layout);
////        StatusBarUtil.setColor(getActivity(), Color.GREEN);
//        initView();
//    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.personalcenter_layout, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        ll1 = (LinearLayout) view.findViewById(R.id.ll1);
        ll2 = (LinearLayout) view.findViewById(R.id.ll2);


        iv_setting = (ImageView) view.findViewById(R.id.iv_setting);
        iv_setting.setOnClickListener(this);
        ll_main = (LinearLayout) view.findViewById(R.id.ll_main);
        ll_share = (LinearLayout) view.findViewById(R.id.ll_share);
        ll_share.setOnClickListener(this);

        ll_userfeed = (LinearLayout) view.findViewById(R.id.ll_userfeed);
        ll_userfeed.setOnClickListener(this);

        ll_aboutme = (LinearLayout) view.findViewById(R.id.ll_aboutme);
        ll_aboutme.setOnClickListener(this);

        ll_contact_me = (LinearLayout) view.findViewById(R.id.ll_contact_me);
        ll_contact_me.setOnClickListener(this);


        tv1 = view.findViewById(R.id.tv1);
        tv1.setOnClickListener(this);
        tv3 = view.findViewById(R.id.tv3);
        tv3.setOnClickListener(this);
        tv4 = view.findViewById(R.id.tv4);
        tv4.setOnClickListener(this);
        ll1.setOnClickListener(this);
        ll2.setOnClickListener(this);
        login = view.findViewById(R.id.login);
        login.setOnClickListener(this);
    }


    private void initData() {
        String url = HttpConstant.baseUrl + "/user/info";
        Map<String, String> map = new HashMap<>();
        map.put("id", UserSP.getInstance().getUserId(getActivity()));
        OkhttpUtil.okHttpGet(getActivity(), url, map, new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {
            }

            @Override
            public void onResponse(String response) {
                try {
                    UserDdtaBean userDdtaBean = UserDateBeanParser.parse(response);
                if (userDdtaBean.getApp_account() != null) {
                        tv1.setText("用户名:" + userDdtaBean.getApp_account());
                    }
                    if (userDdtaBean.getEnter_name() != null) {
                        tv3.setText("所属名称:" + userDdtaBean.getEnter_name());
                    }
                    if (userDdtaBean.getArea_name() != null) {
                        tv4.setText("电子围栏:" + userDdtaBean.getArea_name());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
    @Override
    public void onResume() {
        super.onResume();
        Timber.d("onResume");
        if (!TextUtils.isEmpty(UserSP.getInstance().getUserId(getActivity()))) {
            tv1.setVisibility(View.VISIBLE);
            tv3.setVisibility(View.VISIBLE);
            tv4.setVisibility(View.VISIBLE);
            login.setVisibility(View.GONE);
            initData();
        } else {
            tv1.setVisibility(View.GONE);
            tv3.setVisibility(View.GONE);
            tv4.setVisibility(View.GONE);
            login.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
            case R.id.ll1:
                if (!TextUtils.isEmpty(UserSP.getInstance().getUserId(getActivity()))) {
                    Timber.d("UserId:" + UserSP.getInstance().getUserId(getActivity()));
                    startActivity(new Intent(getActivity(), PublishedActivity.class));
                } else {
                    Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(loginIntent, LOGIN_REQUSET_CODE);
                }
                break;
            case R.id.ll2:
                if (!TextUtils.isEmpty(UserSP.getInstance().getUserId(getActivity()))) {
                    Timber.d("UserId:" + UserSP.getInstance().getUserId(getActivity()));
                    startActivity(new Intent(getActivity(), CollectedActivity.class));
                } else {
                    Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(loginIntent, LOGIN_REQUSET_CODE);
                }
                break;
            case R.id.ll_share:
                Intent intent3 = new Intent(getActivity(), RecommendedShareActivity.class);
                startActivity(intent3);
                break;
            case R.id.ll_userfeed:
                if (!TextUtils.isEmpty(UserSP.getInstance().getUserId(getActivity()))) {
                    Timber.d("UserId:" + UserSP.getInstance().getUserId(getActivity()));
                    startActivity(new Intent(getActivity(), UserFeedbackActivity.class));
                } else {
                    Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(loginIntent, LOGIN_REQUSET_CODE);
                }
                break;
            case R.id.ll_userdata:
                Intent intent5 = new Intent(getActivity(), UserDataActivity.class);
                startActivity(intent5);
                break;
            case R.id.ll_aboutme:
                Intent intent6 = new Intent(getActivity(), AboutMeActivity.class);
                startActivity(intent6);
                break;
            case R.id.iv_setting:
                Intent intent7 = new Intent(getActivity(), MoreSettingActivity.class);
                startActivity(intent7);
                break;
            case R.id.ll_contact_me:
                showPop();
                break;


        }
    }

    private void showPop() {
        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.buttom_dialog, null);
        ShareBottomPopupDialog shareBottomPopupDialog = new ShareBottomPopupDialog(getActivity(), dialogView);
        shareBottomPopupDialog.showPopup(ll_main);
        bt_cancel = (Button) dialogView.findViewById(R.id.bt_cancel);
        bt_tel = (Button) dialogView.findViewById(R.id.bt_tel);
        bt_tel.setText("呼叫 15012667208");
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.bt_cancel:
                        break;
                    case R.id.bt_tel:
                        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.
                                permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(getActivity(), new
                                    String[]{Manifest.permission.CALL_PHONE}, 1);
                        } else {
                            call();
                        }
                        break;

                }
                shareBottomPopupDialog.dismiss();

            }
        };
        bt_tel.setOnClickListener(clickListener);
        bt_cancel.setOnClickListener(clickListener);
    }

    private void call() {
        try {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:13135342156"));
            startActivity(intent);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    call();
                } else {
                    Toast.makeText(getActivity(), "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == Activity.RESULT_OK && requestCode == LOGIN_REQUSET_CODE) {
//            startActivity(new Intent(getActivity(), MainActivity.class));
//        }
//    }
}
