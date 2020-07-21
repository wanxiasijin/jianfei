package com.chenganrt.smartSupervision.business.home.chart;

import android.util.Log;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by philipp on 02/06/16.
 */
public class DayAxisValueFormatter implements IAxisValueFormatter{

    public List<String> mStringList=new ArrayList<>();

    public DayAxisValueFormatter(List<String> mStringList){
       this.mStringList=mStringList;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        Log.d("mm",value+"");
        String formatString = "";
        if (mStringList.isEmpty()){
            return "";
        }
        switch ((int) value) {
            case 0:
                formatString = mStringList.get(0);
                break;
            case 1:
                formatString = mStringList.get(1);
                break;
            case 2:
                formatString =mStringList.get(2);
                break;
            case 3:
                formatString =mStringList.get(3);
                break;
            default:
                break;
        }
        return formatString;
    }
}
