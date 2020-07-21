package com.chenganrt.smartSupervision.business.electronic.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.chenganrt.smartSupervision.R;
import com.chenganrt.smartSupervision.business.electronic.parse.entiy.VehicleEntity;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;


/**
 * Created by Administrator on 2019/4/17.
 */

public class VehicleNoAdapter extends RecyclerView.Adapter<VehicleNoAdapter.ViewHolder> {

    private OnItemClickListener mOnItemClickListener;
    private Context context;
    private List<Object> objectList = new ArrayList<>();

    public VehicleNoAdapter(Context context) {
        this.context = context;
    }

    public void refresh(List<VehicleEntity> vehicleEntities) {
        objectList.clear();
        if (vehicleEntities != null && vehicleEntities.size() > 0) {
            objectList.addAll(vehicleEntities);
        }
        notifyDataSetChanged();
    }

    public void addData(List<VehicleEntity> vehicleEntities) {
        if (vehicleEntities != null && vehicleEntities.size() > 0) {
            objectList.addAll(vehicleEntities);
            notifyDataSetChanged();
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.item_vehicle_no, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final VehicleEntity vehicleEntity = (VehicleEntity) objectList.get(position);
        holder.vehicleNo.setText(vehicleEntity.getVehicleNo());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null)
                    mOnItemClickListener.onItemClick(view, vehicleEntity);
            }
        });
    }


    @Override
    public int getItemCount() {
        return objectList.size();
    }


    public interface OnItemClickListener {
        void onItemClick(View view, VehicleEntity vehicleEntity);

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        AdapterView.OnItemClickListener listener;
        TextView vehicleNo;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            vehicleNo = (TextView) itemView.findViewById(R.id.tv_vehicle);
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onItemClick(null, v, getAdapterPosition(), getAdapterPosition());
            }
        }
    }
}
