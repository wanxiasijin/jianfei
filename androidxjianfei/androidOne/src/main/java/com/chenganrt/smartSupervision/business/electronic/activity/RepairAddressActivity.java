package com.chenganrt.smartSupervision.business.electronic.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.android.commonlib.utils.WindowUtil;
import com.chenganrt.smartSupervision.R;
import com.chenganrt.smartSupervision.business.electronic.BillAction;
import com.chenganrt.smartSupervision.business.electronic.RecycleItemDecor;
import com.chenganrt.smartSupervision.business.electronic.okhttp.UICallback;
import com.chenganrt.smartSupervision.business.electronic.parse.entiy.AccomEntity;
import com.chenganrt.smartSupervision.business.electronic.parse.entiy.OrderDetailEntity;
import com.chenganrt.smartSupervision.business.electronic.ui.EditTextDel;
import com.chenganrt.smartSupervision.business.electronic.ui.Group;
import com.chenganrt.smartSupervision.business.electronic.ui.adapter.AddressAdapter;
import com.chenganrt.smartSupervision.business.login.UserSP;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Administrator on 2019/12/26.
 */

public class RepairAddressActivity extends BaseActivity implements AddressAdapter.OnItemClickListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.ed_address)
    EditTextDel edAddress;

    private AddressAdapter addressAdapter;
    private List<AccomEntity> accomEntities;
    private List<AccomEntity> tempAccList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_singleads);
        getSupportActionBar().hide();
        WindowUtil.setStatusTextColor(true, getWindow(), getResources().getColor(R.color.black));
        ButterKnife.bind(this);
        initData();
        edWatch();
        getAccInfo();
    }

    @OnClick(R.id.tvCancel)
    public void onClickView(View view) {
        switch (view.getId()) {
            case R.id.tvCancel:
                getActivity().finish();
                break;
        }
    }

    private void initData() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new RecycleItemDecor(0));
        addressAdapter = new AddressAdapter(getContext());
        recyclerView.setAdapter(addressAdapter);
        addressAdapter.setOnItemClickListener(this);

    }

    private void getAccInfo() {
        BillAction billAction = new BillAction();
        billAction.getAccAction(UserSP.getInstance().getUserName(getContext()), "", new UICallback(getActivity(), true) {
            @Override
            public void handleSuccess(Message msg) {
                Group<AccomEntity> aEntities = (Group<AccomEntity>) msg.obj;
                if (aEntities == null) return;
                accomEntities = aEntities;
                addressAdapter.refresh((List) aEntities);
            }
        });
    }

    private void edWatch() {
        edAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                getAlladds(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private List<OrderDetailEntity.ReceiveEntity> getAlladds(String str) {
        tempAccList.clear();
        if (accomEntities != null) {
            for (int i = 0; i < accomEntities.size(); i++) {
                AccomEntity accomEntity = accomEntities.get(i);
                if (accomEntity.getFieldName().contains(str)) {
                    tempAccList.add(accomEntity);
                }
            }
            addressAdapter.refresh((List) tempAccList);
        }
        return null;
    }

    @Override
    public void onItemClick(View view, Object object) {
        if (object == null) return;
        if (object instanceof AccomEntity) {
            AccomEntity accomEntity = (AccomEntity) object;
            Intent intent = new Intent();
            intent.putExtra("fieldId", accomEntity.getFieldId());
            intent.putExtra("address", accomEntity.getFieldName());
            getActivity().setResult(getActivity().RESULT_OK, intent);
            getActivity().finish();
        }
    }
}
