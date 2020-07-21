package com.chenganrt.smartSupervision.business.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chenganrt.smartSupervision.R;
import com.chenganrt.smartSupervision.business.home.ContentEntity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Administrator on 2019/4/17.
 */

public class ContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private OnItemClickListener mOnItemClickListener;
    private Context context;
    private List<Object> objectList = new ArrayList<>();
    private final  int EMPTY_VIEW=0;
    private final  int NORMAL_VIEW=1;

    public ContentAdapter(Context context) {
        this.context = context;
    }

    public void refresh(List<ContentEntity> vehicleEntities) {
        objectList.clear();
        if (vehicleEntities != null && vehicleEntities.size() > 0) {
            objectList.addAll(vehicleEntities);
        }
        notifyDataSetChanged();
    }
    public void addData(List<ContentEntity> vehicleEntities) {
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
            view = inflater.inflate(R.layout.empty_layout, parent, false);
            return new EmptyHolder(view);
        }else if (viewType==NORMAL_VIEW){
            view = inflater.inflate(R.layout.item_layout, parent, false);
            return new NormalHolder(view);
        }
        return null;

    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof NormalHolder){
            NormalHolder mNormalHolder= (NormalHolder) holder;
            final ContentEntity contentEntity = (ContentEntity) objectList.get(position);
            if(contentEntity==null){
                return;
            }
            if(contentEntity.getIs_top()==1){
                mNormalHolder.tv_flag.setText("置顶");
            }else {
                mNormalHolder.tv_flag.setText("最新");
            }
            mNormalHolder.tv_title.setText(contentEntity.getTitle());
            mNormalHolder.tv_source.setText(contentEntity.getSource());
            mNormalHolder.tv_time.setText(contentEntity.getTime());
            mNormalHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null)
                        mOnItemClickListener.onItemClick(view, contentEntity,position);
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
        void onItemClick(View view, ContentEntity vehicleEntity, int position);

    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public class NormalHolder extends RecyclerView.ViewHolder {
        TextView tv_flag;
        TextView tv_title;
        TextView tv_source;
        TextView tv_time;

        public NormalHolder(View itemView) {
            super(itemView);
            tv_flag = (TextView) itemView.findViewById(R.id.tv_flag);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_source = (TextView) itemView.findViewById(R.id.tv_source);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
        }

    }
    public class EmptyHolder extends RecyclerView.ViewHolder  {
        public EmptyHolder(View itemView) {
            super(itemView);
        }
    }
}
