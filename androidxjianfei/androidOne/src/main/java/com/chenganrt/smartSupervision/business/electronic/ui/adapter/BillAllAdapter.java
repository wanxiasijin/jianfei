package com.chenganrt.smartSupervision.business.electronic.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chenganrt.smartSupervision.R;
import com.chenganrt.smartSupervision.business.electronic.BillEntity;
import com.chenganrt.smartSupervision.business.electronic.util.BillStatusUtil;
import com.chenganrt.smartSupervision.business.electronic.RemovieEvent;
import com.chenganrt.smartSupervision.business.electronic.util.AppTools;
import com.chenganrt.smartSupervision.business.electronic.util.CountDown;
import com.chenganrt.smartSupervision.business.electronic.util.ICountDownTime;
import com.chenganrt.smartSupervision.business.login.UserSP;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import androidx.recyclerview.widget.RecyclerView;


/**
 * Created by dell on 2018/7/12.
 */

public class BillAllAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int VIEW_TYPE_ITEM = 1;
    public static final int VIEW_TYPE_EMPTY = 0;

   // private ICountDownTime iCountDownTime;
    private List<Object> objectList = new ArrayList<>();
    private Context context;
    private IClicItem iClickItem;

    public BillAllAdapter(Context context) {
        this.context = context;
    }

    public BillAllAdapter(Context context, ICountDownTime iCountDownTime) {
        this.context = context;
     //   this.iCountDownTime = iCountDownTime;
    }


    public interface IClicItem {
        void selectedItem(BillEntity billEntity, int position, int type);
    }

    public void setItemClick(IClicItem iClickItem) {
        this.iClickItem = iClickItem;
    }

    public void refresh(List<BillEntity> billEntities) {
        if (billEntities == null) billEntities = new ArrayList<>();
        objectList.clear();
        objectList.addAll(billEntities);
        notifyDataSetChanged();
    }

    public void addData(List<BillEntity> billEntities) {
        if (billEntities == null) return;
        objectList.addAll(billEntities);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_EMPTY) {
            View emptyView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_empty, parent, false);
            return new RecyclerView.ViewHolder(emptyView) {
            };
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
      //  iCountDownTime.addObserver(viewHolder);
       // iCountDownTime.startCountdown();
        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        if (objectList.size() == 0) return VIEW_TYPE_EMPTY;
        return VIEW_TYPE_ITEM;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder hd = (ViewHolder) holder;
            hd.lastBindPositon = position;
            final BillEntity billEntity = (BillEntity) objectList.get(position);
            if (billEntity != null) {
                if (!TextUtils.isEmpty(billEntity.getSignStatus()) && "0".equals(billEntity.getSignStatus())) {
                    billEntity.setOver("0");
                    ((ViewHolder) holder).billEntity = billEntity;
                    bindCountView(hd.tvDownTime, hd.billEntity, hd.lastBindPositon);
//                    if (!iCountDownTime.containHolder(hd)) {
//                        iCountDownTime.addObserver(hd);
//                    }

                    if (!TextUtils.isEmpty(billEntity.getIsCountDown()) && "1".equals(billEntity.getIsCountDown())) {
                        hd.iconDown.setVisibility(View.VISIBLE);
                        hd.tvDownTime.setVisibility(View.VISIBLE);

                    } else {
                        hd.iconDown.setVisibility(View.GONE);
                        hd.tvDownTime.setVisibility(View.GONE);
                    }
                } else {
                    hd.iconDown.setVisibility(View.GONE);
                    hd.tvDownTime.setVisibility(View.GONE);
                }

                if (!TextUtils.isEmpty(billEntity.getSignStatus())) {
                    if ("1".equals(billEntity.getSignStatus())) {
                        hd.tvPushTime.setText(String.format(context.getResources().getString(R.string.home_comfirm), billEntity.getSignTime()));
                    } else if ("2".equals(billEntity.getSignStatus())) {
                        hd.tvPushTime.setText(String.format(context.getResources().getString(R.string.home_cancel), billEntity.getCancelTime()));
                    } else if ("4".equals(billEntity.getSignStatus())) {
                        hd.tvPushTime.setText(String.format(context.getResources().getString(R.string.home_report), billEntity.getSignTime()));
                    } else if ("5".equals(billEntity.getSignStatus())) {
                        hd.tvPushTime.setText(String.format(context.getResources().getString(R.string.home_sure), billEntity.getSignTime()));
                    } else {
                        hd.tvPushTime.setText(String.format(context.getResources().getString(R.string.home_push), billEntity.getReceiveTime()));
                    }
                }

                hd.mTvWaybillNumber.setText("联单编号:" + billEntity.getOrderId());
                hd.mTvStartAddress.setText(billEntity.getProjectAddress());
                hd.mTvEndAddress.setText(billEntity.getReceivingName());
                hd.mTvNumberPlate.setText(billEntity.getVehicleNo());

                setSoilExcept(hd.mTvStatus, billEntity);

                setDrawableLeft(hd.mTvNumberPlate, billEntity.getApplyStatus());
//                hd.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        if (iClickItem != null) iClickItem.selectedItem(billEntity, position);
//                    }
//                });
                hd.check_details.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (iClickItem != null) iClickItem.selectedItem(billEntity, position, 0);
                    }
                });

                hd.check_track.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (iClickItem != null) iClickItem.selectedItem(billEntity, position, 1);
                    }
                });
            }
        }
    }

    private void setSoilExcept(TextView textView, BillEntity billEntity) {
        if ("1".equals(UserSP.getInstance().getUserType(context))) {
            textView.setText(billEntity.getStatusName());
            textView.setBackgroundDrawable(BillStatusUtil.getDriverTypeDrawable(context, billEntity.getOrderStatus()));
        } else {
            textView.setText(BillStatusUtil.getTypeName(billEntity.getSignStatus(), 1 == billEntity.getIsSupply()));
            textView.setBackgroundDrawable(BillStatusUtil.getTypeDrawable(context, billEntity.getSignStatus()));

            /*if ("1".equals(billEntity.getSignStatus())) {
                if (!TextUtils.isEmpty(String.valueOf(billEntity.getSoilStatus())) && "0".equals(String.valueOf(billEntity.getSoilStatus()))) {
                    textView.setText(context.getString(R.string.soil_except_bill));
                    textView.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bg_coners_red));
                } else {
                    textView.setText(BillStatusUtil.getTypeName(billEntity.getSignStatus()));
                    textView.setBackgroundDrawable(BillStatusUtil.getTypeDrawable(context, billEntity.getSignStatus()));
                }
            } else {
                textView.setText(BillStatusUtil.getTypeName(billEntity.getSignStatus()));
                textView.setBackgroundDrawable(BillStatusUtil.getTypeDrawable(context, billEntity.getSignStatus()));
            }*/

        }
    }

    private void setDrawableLeft(TextView textView, String status) {
        if (!TextUtils.isEmpty(status)) {
            String type = String.valueOf(UserSP.getInstance().getUserType(context));
            if ("1".equals(type)) {
                if ("1".equals(status)) {
                    textView.setCompoundDrawables(getAppliedDrawable(), null, null, null);
                } else {
                    textView.setCompoundDrawables(getUnAppleDrawable(), null, null, null);
                }
            } else {
                if ("0".equals(status)) {
                    textView.setCompoundDrawables(getUnAppleDrawable(), null, null, null);
                } else if ("1".equals(status)) {
                    textView.setCompoundDrawables(getAppliedDrawable(), null, null, null);
                }
            }

        }
    }

    private Drawable getAppliedDrawable() {
        Drawable drawable = context.getResources().getDrawable(R.drawable.checked);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        return drawable;
    }

    private Drawable getUnAppleDrawable() {
        Drawable drawable = context.getResources().getDrawable(R.drawable.uncheck);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        return drawable;
    }

    private static void bindCountView(TextView textView, BillEntity billEntity, final int position) {
        if (billEntity == null) return;
        if ("1".equals(billEntity.getIsCountDown())) {
            long date = getMillTime(billEntity.getSignExpire());
            if ("0".equals(billEntity.getOver()) && date > 0) {
                textView.setText(AppTools.formatDate(date));
            } else if ("0".equals(billEntity.getOver()) && date == 0) {
                textView.setText("00:00:00");
                EventBus.getDefault().post(new RemovieEvent(position));
                //tvStatus.setBackgroundDrawable(textView.getResources().getDrawable(R.drawable.bg_coners_gray));
            }
        }
    }


    private static long getMillTime(String time) {
        SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowtime = d.format(new Date());
        try {
            return (d.parse(time).getTime() - d.parse(nowtime).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public void notifyItem(final int position) {//try上,以防万一
        if (objectList != null && objectList.size() > 0) {
            //((BillEntity) objectList.get(position)).setIsCountDown("0");
          //  LogUtil.i("notifyItem count:::");
            ((BillEntity) objectList.get(position)).setOver("1");
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    try {
                        notifyItemChanged(position, position);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public void removeItem(final int position) {
        if (objectList != null && objectList.size() > 0 && position < objectList.size()) {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    try {
                     //   LogUtil.i("removeItem count:::");
                        ((BillEntity) objectList.get(position)).setOver("1");
                        ((BillEntity) objectList.get(position)).setIsCountDown("0");
                        //notifyItemChanged(position, position);
                        objectList.remove(position);
                        notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (objectList.size() == 0) return 1;
        return objectList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements Observer {

        int lastBindPositon = -1;
        BillEntity billEntity;
        TextView check_track;
        TextView check_details;//运单编号
        TextView mTvWaybillNumber;//运单编号
        TextView mTvStatus;//状态
        TextView mTvStartAddress;//开始地址
        TextView mTvEndAddress;//结束地址
        TextView mTvNumberPlate;//车牌号
        TextView tvDownTime;//倒计时
        TextView tvPushTime;//推送时间
        ImageView iconDown;
        LinearLayout layoutDownTime;

        public ViewHolder(View itemView) {
            super(itemView);
            check_track = (TextView) itemView.findViewById(R.id.check_track);
            check_details = (TextView) itemView.findViewById(R.id.check_details);
            mTvWaybillNumber = (TextView) itemView.findViewById(R.id.tv_waybill_number);
            mTvStatus = (TextView) itemView.findViewById(R.id.tv_status);
            mTvStartAddress = (TextView) itemView.findViewById(R.id.tv_start_address);
            mTvEndAddress = (TextView) itemView.findViewById(R.id.tv_end_address);
            mTvNumberPlate = (TextView) itemView.findViewById(R.id.tv_number_plate);
            tvDownTime = (TextView) itemView.findViewById(R.id.downTime);
            tvPushTime = (TextView) itemView.findViewById(R.id.tv_pTime);
            iconDown = (ImageView) itemView.findViewById(R.id.icon_down);
            layoutDownTime = (LinearLayout) itemView.findViewById(R.id.layout_downTime);

        }

        @Override
        public void update(Observable observable, Object object) {
            if (object != null && object instanceof CountDown.PostionFL) {
                CountDown.PostionFL postionFL = (CountDown.PostionFL) object;
                if (lastBindPositon >= postionFL.frist && lastBindPositon <= postionFL.last) {
                   // bindCountView(tvDownTime, billEntity, lastBindPositon);
                }
            }
        }

    }
}
