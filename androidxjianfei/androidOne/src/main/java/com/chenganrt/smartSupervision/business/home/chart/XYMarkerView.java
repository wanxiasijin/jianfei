
package com.chenganrt.smartSupervision.business.home.chart;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chenganrt.smartSupervision.R;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

import java.text.DecimalFormat;

/**
 * Custom implementation of the MarkerView.
 *
 * @author Philipp Jahoda
 */
public class XYMarkerView extends MarkerView {
    private TextView tvContent;
    private TextView tv1;
    private TextView tv2;
    private View view;
    private IAxisValueFormatter xAxisValueFormatter;
    private DecimalFormat format;

    public XYMarkerView(Context context, IAxisValueFormatter xAxisValueFormatter) {
        super(context, R.layout.custom_marker_view);
        this.xAxisValueFormatter = xAxisValueFormatter;
        tvContent = (TextView) findViewById(R.id.tvContent);
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        view =   findViewById(R.id.view);
        //format = new DecimalFormat("###.0");
    }


    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        tvContent.setText(xAxisValueFormatter.getFormattedValue(e.getX(), null));
        String ss = (String) e.getData();

        if ("正常结束联单".equals(e.getData())) {
            view.setBackgroundResource(R.drawable.fade_red1111);
        } else {
            view.setBackgroundResource(R.drawable.fade_red222);
        }
        tv1.setText(ss);
        tv2.setText((int) e.getY()+ "条");
        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }
}
