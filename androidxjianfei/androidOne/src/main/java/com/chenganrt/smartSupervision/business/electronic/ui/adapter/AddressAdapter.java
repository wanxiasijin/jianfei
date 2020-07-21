package com.chenganrt.smartSupervision.business.electronic.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chenganrt.smartSupervision.R;
import com.chenganrt.smartSupervision.business.electronic.parse.entiy.AccomEntity;
import com.chenganrt.smartSupervision.business.electronic.parse.entiy.FieldDetailEntity;
import com.chenganrt.smartSupervision.business.electronic.parse.entiy.FieldEntity;
import com.chenganrt.smartSupervision.business.electronic.parse.entiy.OrderDetailEntity;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;


public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {
    private OnItemClickListener mOnItemClickListener;
    private List<Object> mData = new ArrayList<>();
    private Context context;

    public AddressAdapter(Context context) {
        this.context = context;
    }

    @Override
    public AddressAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_address, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AddressAdapter.ViewHolder holder, final int position) {
        Object object = mData.get(position);
        if (object instanceof FieldEntity) {
            FieldEntity fieldEntity = (FieldEntity) object;
            if (fieldEntity != null && !TextUtils.isEmpty(fieldEntity.getFieldTypeName()))
                holder.name.setText(fieldEntity.getFieldTypeName());
        } else if (object instanceof FieldDetailEntity) {
            FieldDetailEntity fieldDetailEntity = (FieldDetailEntity) object;
            if (fieldDetailEntity != null && !TextUtils.isEmpty(fieldDetailEntity.getReceivingName()))
                holder.name.setText(fieldDetailEntity.getReceivingName());
        } else if (object instanceof OrderDetailEntity.ReceiveEntity) {
            OrderDetailEntity.ReceiveEntity receiveEntity = (OrderDetailEntity.ReceiveEntity) object;
            if (receiveEntity != null && !TextUtils.isEmpty(receiveEntity.getReceivingName()))
                holder.name.setText(receiveEntity.getReceivingName());
        } else if (object instanceof AccomEntity) {
            AccomEntity accomEntity = (AccomEntity) object;
            if (accomEntity != null && !TextUtils.isEmpty(accomEntity.getFieldName()))
                holder.name.setText(accomEntity.getFieldName());
        }
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(v, mData.get(position));
                }
            });
        }
    }

    public void refresh(List<Object> objectList) {
        if (objectList == null) objectList = new ArrayList<>();
        mData.clear();
        mData.addAll(objectList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.tv_address_detail);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, Object object);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
