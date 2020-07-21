package com.chenganrt.smartSupervision.business.electronic.chart;

import android.content.Context;

import com.chenganrt.smartSupervision.R;
import com.chenganrt.smartSupervision.business.electronic.parse.entiy.AnalyseEntity;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2019/5/30.
 */

public class Mpiechart {

    private int animaTime;

    public void setAnimaTime(int animaTime) {
        this.animaTime = animaTime;
    }

    public void showPieChart(Context context, PiechartSelfView pieChart, final List<PieEntry> pieList) {
        PieDataSet dataSet = new PieDataSet(pieList, "");

        ArrayList<Integer> colors = new ArrayList<>();
        for (PieEntry pieEntry : pieList) {
            AnalyseEntity analyseEntity = (AnalyseEntity) pieEntry.getData();

            if ("0".equals(analyseEntity.getType()))
                colors.add(context.getResources().getColor(R.color.orange_sd));//待签认


            if ("1".equals(analyseEntity.getType()))
                colors.add(context.getResources().getColor(R.color.green_theam));//已签认


            if ("2".equals(analyseEntity.getType()))
                colors.add(context.getResources().getColor(R.color.gray_sd));//已取消

            if ("3".equals(analyseEntity.getType()))
                colors.add(context.getResources().getColor(R.color.brown_sd));//未签认

            if ("4".equals(analyseEntity.getType()))
                colors.add(context.getResources().getColor(R.color.text_backcolor_f7));//土质异常

            if ("5".equals(analyseEntity.getType()))
                colors.add(context.getResources().getColor(R.color.brown_sd));//自动签认

            if ("-1".equals(analyseEntity.getType()))
                colors.add(context.getResources().getColor(R.color.green_theam));
        }

        dataSet.setColors(colors);
        PieData pieData = new PieData(dataSet);
        Description description = new Description();
        description.setEnabled(false);
        pieChart.setDescription(description);
        pieChart.setHoleRadius(80f);//设置PieChart内部圆的半径
        /*pieChart.setTransparentCircleRadius(80f);//设置半透明圆环的半径, 0为透明
        pieChart.setTransparentCircleColor(Color.WHITE);//设置PieChart内部透明圆与内部圆间距(31f-28f)填充颜色
        pieChart.setTransparentCircleAlpha(255);*/
        //pieChart.setRotationAngle(-15);//设置初始旋转角度

        dataSet.setValueLinePart1OffsetPercentage(108f);//数据连接线距图形片内部边界的距离，为百分数
        dataSet.setValueLinePart1Length(0.2f);
        dataSet.setValueLinePart2Length(0.4f);
        dataSet.setValueLineColor(context.getResources().getColor(R.color.gray_sd1));//设置连接线的颜色
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);//连接线在饼状图外面
        dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);//连接线在饼状图外面
        dataSet.setValueLineColor(0xff000000);  //设置指示线条颜色,必须设置成这样，才能和饼图区域颜色一致

        dataSet.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                //DecimalFormat df = new DecimalFormat("0.00");//格式化小数
                if ("-1".equalsIgnoreCase(((AnalyseEntity) entry.getData()).getType())) {
                    return "0";
                }
                return (((AnalyseEntity) entry.getData()).getValue()) + "  (" + ((AnalyseEntity) entry.getData()).getPercentage() + "%)";
            }
        });

        dataSet.setSliceSpace(2f);//设置饼块之间的间隔
        dataSet.setHighlightEnabled(true);
        Legend legend = pieChart.getLegend();
        legend.setEnabled(false);//是否显示图例
        legend.setPosition(Legend.LegendPosition.ABOVE_CHART_RIGHT);
        legend.setTextColor(context.getResources().getColor(R.color.black_sd4));
        legend.setXEntrySpace(10f); //设置图例实体之间延X轴的间距
        legend.setTextSize(12f);
        pieChart.setExtraOffsets(46, 10, 46, 10);// 和四周相隔一段距离,显示数据
        pieChart.setRotationEnabled(false);//设置pieChart图表是否可以手动旋转
        pieChart.setHighlightPerTapEnabled(true);//设置piecahrt图表点击Item高亮是否可用
        pieChart.animateY(animaTime, Easing.EasingOption.EaseInOutQuad);// 设置pieChart图表展示动画效果
        pieChart.setDrawEntryLabels(true);//设置pieChart是否只显示饼图上百分比不显示文字
        pieChart.setDrawCenterText(false);//是否绘制PieChart内部中心文本
        pieData.setDrawValues(true);// 绘制内容value，设置字体颜色大小
        pieChart.setEntryLabelTextSize(10.2f);//label文字大小
        //pieChart.setEntryLabelColor(context.getResources().getColor(R.color.gray_sd));

        //pieData.setValueFormatter(new PercentFormatter());

        pieData.setValueTextSize(8.8f);//值文字大小
        //pieData.setValueTextSize(9f);//值文字大小
        pieData.setValueTextColor(context.getResources().getColor(R.color.black_sd4));
        //pieChart.setDrawSliceText(false);//去年圆环内的文本
        pieChart.setUsePercentValues(false);//是否用百分比显示

        pieChart.setData(pieData);
        pieChart.postInvalidate();// 更新 piechart 视图
    }

}
