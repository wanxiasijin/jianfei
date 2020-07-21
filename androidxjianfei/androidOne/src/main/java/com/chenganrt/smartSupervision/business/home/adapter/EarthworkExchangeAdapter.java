package com.chenganrt.smartSupervision.business.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chenganrt.smartSupervision.R;
import com.chenganrt.smartSupervision.business.home.MatchMessageEntity;
import com.chenganrt.smartSupervision.utils.FormatCurrentData;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


/**
 * Created by Administrator on 2019/4/17.
 */

public class EarthworkExchangeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private OnItemClickListener mOnItemClickListener;
    private Context context;
    private List<Object> objectList = new ArrayList<>();
    private final  int EMPTY_VIEW=0;
    private final  int NORMAL_VIEW=1;

    public EarthworkExchangeAdapter(Context context) {
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
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        if(viewType==EMPTY_VIEW){
            view = inflater.inflate(R.layout.empty_match_layout, parent, false);
            return new EarthworkExchangeAdapter.EmptyHolder(view);
        }else if (viewType==NORMAL_VIEW){
            view = inflater.inflate(R.layout.item_earthwork_exchange_message, parent, false);
            return new EarthworkExchangeAdapter.NormalHolder(view);
        }
        return null;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof EarthworkExchangeAdapter.NormalHolder) {
            EarthworkExchangeAdapter.NormalHolder mNormalHolder = (EarthworkExchangeAdapter.NormalHolder) holder;
            final MatchMessageEntity matchMessageEntity = (MatchMessageEntity) objectList.get(position);
            if (matchMessageEntity == null) {
                return;
            }
            mNormalHolder.title.setText(matchMessageEntity.getTitle());
            mNormalHolder.amount.setText(matchMessageEntity.getAmount()+"万方");
            mNormalHolder.type.setText(matchMessageEntity.getWaste_type_detail());
            mNormalHolder.tv_notice_id.setText("编号:" + matchMessageEntity.getNotice_id());
            mNormalHolder.time1.setText(FormatCurrentData.getTimeRange(matchMessageEntity.getCreate_time()));
            mNormalHolder.time2.setText(matchMessageEntity.getCreate_time());
            mNormalHolder.tv_check_details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null)
                        mOnItemClickListener.onItemClick(view, matchMessageEntity, position, matchMessageEntity.getNotice_id());
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
    public interface OnItemClickListener {
        void onItemClick(View view, MatchMessageEntity vehicleEntity, int position,String id);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    class NormalHolder extends RecyclerView.ViewHolder   {

        TextView title;
        TextView amount;
        TextView time1;
        TextView tv_notice_id;
        TextView type;
        TextView time2;
        TextView tv_check_details;

        public NormalHolder(View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.adress);
            amount=itemView.findViewById(R.id.amount);
            time1=itemView.findViewById(R.id.time1);
            time2=itemView.findViewById(R.id.time2);
            tv_notice_id=itemView.findViewById(R.id.tv_notice_id);
            type=itemView.findViewById(R.id.type);
            tv_check_details=itemView.findViewById(R.id.tv_check_details);

        }
    }
    public class EmptyHolder extends RecyclerView.ViewHolder  {
        public EmptyHolder(View itemView) {
            super(itemView);
        }
    }
}
