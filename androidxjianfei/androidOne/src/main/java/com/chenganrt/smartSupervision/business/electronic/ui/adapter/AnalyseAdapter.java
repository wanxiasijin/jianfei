package com.chenganrt.smartSupervision.business.electronic.ui.adapter;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.chenganrt.smartSupervision.R;
import com.chenganrt.smartSupervision.business.electronic.chart.Mpiechart;
import com.chenganrt.smartSupervision.business.electronic.chart.PiechartSelfView;
import com.chenganrt.smartSupervision.business.electronic.parse.entiy.AnalyseDetailEntity;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;


/**
 * Created by Administrator on 2019/4/17.
 */

public class AnalyseAdapter extends RecyclerView.Adapter<AnalyseAdapter.ViewHolder> implements OnChartValueSelectedListener, AdapterView.OnItemClickListener {

    private final int ITEM_TITLE = 0, ITEM_CONTENT = 1, ITEM_PIECHART = 2, ITEM_EMPTY = 3;

    private Context context;
    private boolean isExcept;
    private boolean isAuto;
    private List<Object> objectList = new ArrayList<>();
    private List<PieEntry> pie = new ArrayList<>();
    private int anmatime;

    public AnalyseAdapter(Context context, boolean isExcept, boolean isAuto) {
        this.context = context;
        this.isExcept = isExcept;
        this.isAuto = isAuto;
    }

    private IClickitem iClickitem;

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        if (iClickitem != null) iClickitem.pieviewPartSelect(e, h);
    }

    @Override
    public void onNothingSelected() {

    }

    public interface IClickitem {
        void dropTip(TextView view);

        void pieviewPartSelect(Entry e, Highlight h);
    }

    public void setiClickitem(IClickitem iClickitem) {
        this.iClickitem = iClickitem;
    }


    public void refresh(List<AnalyseDetailEntity.AnalyseContentEntity> detailEntities, List<PieEntry> pie, int anmatime) {
        this.pie = pie;
        this.anmatime = anmatime;
        objectList.clear();
        objectList.add(ITEM_PIECHART);
        objectList.add(ITEM_TITLE);
        if (detailEntities != null && detailEntities.size() > 0) {
            objectList.addAll(detailEntities);
        } else {
            objectList.add(ITEM_EMPTY);
        }
        notifyDataSetChanged();
    }

    public void addData(List<AnalyseDetailEntity.AnalyseContentEntity> detailEntities) {
        if (detailEntities != null && detailEntities.size() > 0) {
            objectList.addAll(detailEntities);
            notifyDataSetChanged();
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        int viewId = 0;
        switch (viewType) {
            case ITEM_PIECHART:
                viewId = R.layout.item_analyse_piechart;
                break;
            case ITEM_TITLE:
                viewId = R.layout.item_analyse_title;
                break;
            case ITEM_CONTENT:
                if (isExcept) viewId = R.layout.item_analyse_content;
                else viewId = R.layout.item_analyse_content_except;
                break;
            case ITEM_EMPTY:
                viewId = R.layout.layout_part_empty;
                break;
        }
        View v = inflater.inflate(viewId, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public int getItemViewType(int position) {
        Object obj = objectList.get(position);
        if (obj instanceof Integer) {
            return ((Integer) obj).intValue();//对应类型
        } else if (obj instanceof AnalyseDetailEntity.AnalyseContentEntity) {
            return ITEM_CONTENT;
        }
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final int viewType = holder.getItemViewType();
        switch (viewType) {
            case ITEM_TITLE:
                holder.titleExcept.setVisibility(isExcept ? View.VISIBLE : View.GONE);
                holder.titleUnsign.setText(context.getResources().getString(isAuto ? R.string.home_auto : R.string.home_unsigned));
                holder.titleRate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (iClickitem != null)
                            iClickitem.dropTip(holder.titleRate);
                    }
                });
                break;
            case ITEM_CONTENT:
                bindDetailHolder(holder, (AnalyseDetailEntity.AnalyseContentEntity) objectList.get(position));
                break;
            case ITEM_PIECHART:
                bindPieHolder(holder);
                break;
        }
    }

    private void bindDetailHolder(ViewHolder holder, AnalyseDetailEntity.AnalyseContentEntity detailEntity) {
        holder.tvDate.setText(detailEntity.getDate());
        holder.tvSign.setText(String.valueOf(detailEntity.getToSign()));
        holder.tvSigned.setText(String.valueOf(detailEntity.getConfirmed()));
        holder.tvCancel.setText(String.valueOf(detailEntity.getCancelled()));
        holder.tvExcept.setText(String.valueOf(detailEntity.getSoilExcepted()));
        holder.tvUnsign.setText(String.valueOf(isAuto ? detailEntity.getAutoConfirmed() : detailEntity.getUnsigned()));
        holder.tvRate.setText(detailEntity.getSignedRate() + "%");
        holder.tvExcept.setVisibility(isExcept ? View.VISIBLE : View.GONE);

    }

    private void bindPieHolder(final ViewHolder holder) {
        if (pie == null) {
            holder.piechart.setVisibility(View.GONE);
            holder.pieChartEmpty.setVisibility(View.VISIBLE);
            return;
        } else if (pie != null && pie.size() == 0) {
            return;
        } else {
            holder.piechart.setVisibility(View.VISIBLE);
            holder.pieChartEmpty.setVisibility(View.GONE);
        }
        Mpiechart mpiechart = new Mpiechart();
        mpiechart.setAnimaTime(anmatime);
        mpiechart.showPieChart(context, holder.piechart, pie);
        holder.piechart.setOnChartValueSelectedListener(this);
    }

    private void setUnderLine(TextView textView, String str) {
        SpannableString spanText = new SpannableString(str);
        spanText.setSpan(new UnderlineSpan(), 0, spanText.length(),
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        textView.setText(spanText);
    }

    @Override
    public int getItemCount() {
        return objectList.size();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        AdapterView.OnItemClickListener listener;
        TextView tvDate, tvSign, tvSigned, tvCancel, tvExcept, tvUnsign, tvRate, pieChartEmpty;
        TextView titleRate, titleCancel, titleExcept, titleUnsign;
        PiechartSelfView piechart;
        //PieChartFixCover piechart;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tvDate = (TextView) itemView.findViewById(R.id.tv_date);
            tvSign = (TextView) itemView.findViewById(R.id.tv_sign);//待签认
            tvSigned = (TextView) itemView.findViewById(R.id.tv_signed);//已签认
            tvCancel = (TextView) itemView.findViewById(R.id.tv_cancel);//已取消
            tvExcept = (TextView) itemView.findViewById(R.id.tv_except);//土质异常
            titleExcept = (TextView) itemView.findViewById(R.id.title_except);//土质异常标题
            tvRate = (TextView) itemView.findViewById(R.id.tv_rate);//签认率
            tvUnsign = (TextView) itemView.findViewById(R.id.tv_unsige);//未签认
            titleUnsign = (TextView) itemView.findViewById(R.id.title_unsign);//未签认title
            titleRate = (TextView) itemView.findViewById(R.id.title_rate);
            titleCancel = (TextView) itemView.findViewById(R.id.title_cancel);
            piechart = (PiechartSelfView) itemView.findViewById(R.id.pie_chart);
            pieChartEmpty = (TextView) itemView.findViewById(R.id.chart_empty);
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onItemClick(null, v, getAdapterPosition(), getAdapterPosition());
            }
        }
    }
}
