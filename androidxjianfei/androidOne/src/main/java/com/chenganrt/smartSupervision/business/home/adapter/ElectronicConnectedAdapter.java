package com.chenganrt.smartSupervision.business.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.chenganrt.smartSupervision.R;
import com.chenganrt.smartSupervision.business.home.MatchMessageEntity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


/**
 * Created by Administrator on 2019/4/17.
 */

public class ElectronicConnectedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private OnItemClickListener mOnItemClickListener;
    private Context context;
    private List<Object> objectList = new ArrayList<>();
    private final  int EMPTY_VIEW=0;
    private final  int NORMAL_VIEW=1;

    public ElectronicConnectedAdapter(Context context) {
        this.context = context;
    }

    public void refresh(List<MatchMessageEntity> vehicleEntities) {
        objectList.clear();
        if (vehicleEntities != null && vehicleEntities.size() > 0) {
            objectList.addAll(vehicleEntities);
        }
        notifyDataSetChanged();
    }

    public void addData(List<MatchMessageEntity> vehicleEntities) {
        if (vehicleEntities != null && vehicleEntities.size() > 0) {
            objectList.addAll(vehicleEntities);
            notifyDataSetChanged();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        if(viewType==EMPTY_VIEW){
            view = inflater.inflate(R.layout.empty_match_layout, parent, false);
            return new ElectronicConnectedAdapter.EmptyHolder(view);
        }else if (viewType==NORMAL_VIEW){
            view = inflater.inflate(R.layout.item_electronic_duplicate_message, parent, false);
            return new ElectronicConnectedAdapter.NormalHolder(view);
        }
        return null;
    }




    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof ElectronicConnectedAdapter.NormalHolder){
            ElectronicConnectedAdapter.NormalHolder mNormalHolder= (ElectronicConnectedAdapter.NormalHolder) holder;
            final MatchMessageEntity matchMessageEntity1 = (MatchMessageEntity) objectList.get(position);
            if(matchMessageEntity1==null){
                return;
            }
            mNormalHolder.title.setText(matchMessageEntity1.getTitle());
            mNormalHolder.amount.setText(matchMessageEntity1.getAmount());
            mNormalHolder.waste_type_detail.setText(matchMessageEntity1.getWaste_type_detail());
            mNormalHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null)
                        mOnItemClickListener.onItemClick(view, matchMessageEntity1,position);
                }
            });
        }

    }


    @Override
    public int getItemCount() {
        //同时这里也需要添加判断，如果mData.size()为0的话，只引入一个布局，就是emptyView
        // 那么，这个recyclerView的itemCount为1
        if(objectList.size()==0){
            return 1;
        }
        //如果不为0，按正常的流程跑
        return objectList.size();
    }
    public interface OnItemClickListener {
        void onItemClick(View view, MatchMessageEntity vehicleEntity, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }
    @Override
    public int getItemViewType(int position) {
        //在这里进行判断，如果我们的集合的长度为0时，我们就使用emptyView的布局
        if(objectList.size()==0||objectList.isEmpty()){
            return EMPTY_VIEW;
        } else if(objectList.size()>0) {
            //如果有数据，则使用ITEM的布局
            return NORMAL_VIEW;
        }
        return super.getItemViewType(position);

    }

    class NormalHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        AdapterView.OnItemClickListener listener;
        TextView title;
        TextView amount;
        TextView waste_type_detail;

        public NormalHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title=itemView.findViewById(R.id.tv1);
            amount=itemView.findViewById(R.id.tv2);
            waste_type_detail=itemView.findViewById(R.id.tv3);
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onItemClick(null, v, getAdapterPosition(), getAdapterPosition());
            }
        }
    }
    public class EmptyHolder extends RecyclerView.ViewHolder  {
        public EmptyHolder(View itemView) {
            super(itemView);
        }
    }
}
