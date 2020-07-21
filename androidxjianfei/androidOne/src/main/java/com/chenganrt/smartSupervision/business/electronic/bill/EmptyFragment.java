package com.chenganrt.smartSupervision.business.electronic.bill;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chenganrt.smartSupervision.R;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2019/6/12.
 */

public class EmptyFragment extends BaseFragment {
    @BindView(R.id.layout_empty_fragment)
    LinearLayout layotEmpty;
    @BindView(R.id.tv_empty)
    TextView tvEmpty;

    private IEmpty iEmpty;

    public interface IEmpty {
        void noData();
    }

    public void setiEmpty(IEmpty iEmpty) {
        this.iEmpty = iEmpty;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        layotEmpty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (iEmpty != null) iEmpty.noData();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_empty, container, false);
    }
}
