package com.chenganrt.smartSupervision.business.electronic.wheel;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.chenganrt.smartSupervision.R;
import com.chenganrt.smartSupervision.business.electronic.util.AppTools;
import com.chenganrt.smartSupervision.business.electronic.wheel.adapter.ArrayWheelAdapter;
import com.chenganrt.smartSupervision.business.electronic.wheel.adapter.NumericWheelAdapter;
import com.chenganrt.smartSupervision.business.electronic.wheel.adapter.OnWheelChangedListener;
import com.chenganrt.smartSupervision.business.electronic.wheel.adapter.OnWheelScrollListener;
import com.chenganrt.smartSupervision.business.electronic.wheel.adapter.WheelView;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import androidx.annotation.Nullable;


/**
 * Created by shanxueyi on 2016/9/18.
 */

public class FDatePickerDialog extends FAlertDialog {
    protected FDatePickerDialog(Context context, int theme) {
        super(context, theme);
    }

    protected FDatePickerDialog(Context context) {
        super(context);
    }

    private static boolean isDay = true;
    private static boolean tag = true;

    public static Builder createDatePicker(final Context context, @Nullable final DatePickerDialog.OnDateSetListener mDateSetListener) {
        final FAlertDialog.Builder builder = new FAlertDialog.Builder(context, FAlertDialog.LAYOUT_WHEEL);
        builder.setCancelable(true);
        builder.setCanceledOnTouchOutside(true);

        final WheelView year = (WheelView) builder.getContentView().findViewById(R.id.wheel1);
        final WheelView month = (WheelView) builder.getContentView().findViewById(R.id.wheel2);
        final WheelView day = (WheelView) builder.getContentView().findViewById(R.id.wheel3);


        OnWheelChangedListener listener = new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                updateDays(year, month, day);
            }
        };


        Calendar calendar = Calendar.getInstance();
        // year
        int curYear = calendar.get(Calendar.YEAR);
        year.setViewAdapter(new NumericWheelAdapter(context, 2003, curYear, "%s年"));
        year.setCurrentItem(curYear - 2003);
        year.addChangingListener(listener);

        // month
        int curMonth = calendar.get(Calendar.MONTH);
        month.setViewAdapter(new NumericWheelAdapter(context, 1, 12, "%s月"));
        month.setCurrentItem(curMonth);
        month.addChangingListener(listener);

        //day
        updateDays(year, month, day);
        day.setCurrentItem(calendar.get(Calendar.DAY_OF_MONTH) - 1);


        builder.setPositiveButton(R.string.negative, null);

        builder.setNegativeButton(R.string.positive, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (mDateSetListener != null)
                    mDateSetListener.onDateSet(null, 2003 + year.getCurrentItem(),
                            1 + month.getCurrentItem(), 1 + day.getCurrentItem());
            }
        });
        String[] data = new String[1];
        builder.setWheelData(data, data, data);
        builder.setIsFullscreen(true);
        builder.setGravity(Gravity.BOTTOM);
        return builder;
    }


    public static Builder createDateSpePicker(final Context context, @Nullable final wheelItemDateListener mDateSetListener) {
        final FAlertDialog.Builder builder = new FAlertDialog.Builder(context, FAlertDialog.LAYOUT_WHEELONE);
        builder.setCancelable(true);
        builder.setCanceledOnTouchOutside(true);
        isDay = true;
        tag = true;

        final WheelView year = (WheelView) builder.getContentView().findViewById(R.id.wheel1);
        final WheelView month = (WheelView) builder.getContentView().findViewById(R.id.wheel2);
        final WheelView day = (WheelView) builder.getContentView().findViewById(R.id.wheel3);
        final TextView begin = (TextView) builder.getContentView().findViewById(R.id.bText);
        final TextView middel = (TextView) builder.getContentView().findViewById(R.id.mText);
        final TextView end = (TextView) builder.getContentView().findViewById(R.id.eText);
        final TextView choise = (TextView) builder.getContentView().findViewById(R.id.data_chose);

        day.setVisibility(isDay ? View.VISIBLE : View.GONE);
        if (isDay) choise.setText(context.getResources().getString(R.string.statis_dayOfSelect));
        else choise.setText(context.getResources().getString(R.string.statis_monOfSelect));

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.bText:
                        tag = true;
                        begin.setText(String.valueOf(2003 + year.getCurrentItem()) + "-" + AppTools.addZero(String.valueOf(1 + month.getCurrentItem())) + "-" + AppTools.addZero(String.valueOf(1 + day.getCurrentItem())));
                        break;
                    case R.id.eText:
                        tag = false;
                        end.setText(String.valueOf(2003 + year.getCurrentItem()) + "-" + AppTools.addZero(String.valueOf(1 + month.getCurrentItem())) + "-" + AppTools.addZero(String.valueOf(1 + day.getCurrentItem())));
                        break;
                    case R.id.data_chose:
                        isDay = !isDay;
                        day.setVisibility(isDay ? View.VISIBLE : View.GONE);
                        if (isDay) {
                            choise.setText(context.getResources().getString(R.string.statis_dayOfSelect));
                            begin.setVisibility(View.VISIBLE);
                            end.setVisibility(View.VISIBLE);
                            middel.setText("至");
                            middel.setTextColor(context.getResources().getColor(R.color.black_sd4));
                        } else {
                            choise.setText(context.getResources().getString(R.string.statis_monOfSelect));
                            begin.setVisibility(View.GONE);
                            end.setVisibility(View.GONE);
                            middel.setText(String.valueOf(2003 + year.getCurrentItem()) + "-" + AppTools.addZero(String.valueOf(1 + month.getCurrentItem())));
                            middel.setTextColor(context.getResources().getColor(R.color.green_theam));
                        }
                        break;
                }
            }
        };

        begin.setOnClickListener(onClickListener);
        end.setOnClickListener(onClickListener);
        choise.setOnClickListener(onClickListener);

        OnWheelChangedListener listener = new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                updateDays(year, month, day);
            }
        };


        OnWheelScrollListener onWheelScrollListener = new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {
            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                if (isDay) {
                    if (tag == true) {
                        begin.setText(String.valueOf(2003 + year.getCurrentItem()) + "-" + AppTools.addZero(String.valueOf(1 + month.getCurrentItem())) + "-" + AppTools.addZero(String.valueOf(1 + day.getCurrentItem())));
                    } else {
                        end.setText(String.valueOf(2003 + year.getCurrentItem()) + "-" + AppTools.addZero(String.valueOf(1 + month.getCurrentItem())) + "-" + AppTools.addZero(String.valueOf(1 + day.getCurrentItem())));
                    }
                } else {
                    middel.setText(String.valueOf(2003 + year.getCurrentItem()) + "-" + AppTools.addZero(String.valueOf(1 + month.getCurrentItem())));
                }
            }
        };

        Calendar calendar = Calendar.getInstance();
        // year
        int curYear = calendar.get(Calendar.YEAR);
        year.setViewAdapter(new NumericWheelAdapter(context, 2003, curYear, "%s年"));
        year.setCurrentItem(curYear - 2003);
        year.addChangingListener(listener);
        year.addScrollingListener(onWheelScrollListener);

        // month
        int curMonth = calendar.get(Calendar.MONTH);
        month.setViewAdapter(new NumericWheelAdapter(context, 1, 12, "%s月"));
        month.setCurrentItem(curMonth);
        month.addChangingListener(listener);
        month.addScrollingListener(onWheelScrollListener);

        //day
        updateDays(year, month, day);
        day.setCurrentItem(calendar.get(Calendar.DAY_OF_MONTH) - 1);
        day.addScrollingListener(onWheelScrollListener);

        builder.setPositiveButton(R.string.negative, null);

        builder.setNegativeButton(R.string.positive, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (mDateSetListener != null)
                    mDateSetListener.wheelCurrentSelected(begin.getText().toString().trim(),
                            middel.getText().toString().trim(), end.getText().toString().trim(), isDay);
            }
        });
        String[] data = new String[1];
        builder.setWheelData(data, data, data);
        builder.setIsFullscreen(true);
        builder.setTitle(R.string.statis_timeSelect);
        builder.setGravity(Gravity.BOTTOM);
        return builder;
    }

    public static Builder createDateHourPicker(final Context context, final String[] hourArray, final String[] minuteArray, final onWheelHourItemSelectedListener hourItemSelectedListener) {
        final FAlertDialog.Builder builder = new FAlertDialog.Builder(context, FAlertDialog.LAYOUT_HOUR_WHEEL);
        builder.setCancelable(true);
        builder.setCanceledOnTouchOutside(true);


        final WheelView year = (WheelView) builder.getContentView().findViewById(R.id.wheel1);
        final WheelView month = (WheelView) builder.getContentView().findViewById(R.id.wheel2);
        final WheelView day = (WheelView) builder.getContentView().findViewById(R.id.wheel3);
        final WheelView hour = (WheelView) builder.getContentView().findViewById(R.id.wheel4);
        final WheelView minute = (WheelView) builder.getContentView().findViewById(R.id.wheel5);


        OnWheelChangedListener listener = new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                updateDays(year, month, day);
            }
        };


        Calendar calendar = Calendar.getInstance();
        // year
        int curYear = calendar.get(Calendar.YEAR);
        year.setViewAdapter(new NumericWheelAdapter(context, 2003, curYear, "%s年"));
        year.setCurrentItem(curYear - 2003);
        year.addChangingListener(listener);

        // month
        int curMonth = calendar.get(Calendar.MONTH);
        month.setViewAdapter(new NumericWheelAdapter(context, 1, 12, "%s月"));
        month.setCurrentItem(curMonth);
        month.addChangingListener(listener);

        //day
        updateDays(year, month, day);
        day.setCurrentItem(calendar.get(Calendar.DAY_OF_MONTH) - 1);

        hour.setViewAdapter(new ArrayWheelAdapter<>(context, hourArray));
        minute.setViewAdapter(new ArrayWheelAdapter<>(context, minuteArray));


        /*int curHour = calendar.get(Calendar.HOUR_OF_DAY);
        hour.setViewAdapter(new NumericWheelAdapter(context, 0, 23, "%s时"));
        hour.setCurrentItem(curHour);

        int curMinute = calendar.get(Calendar.MINUTE);
        minute.setViewAdapter(new NumericWheelAdapter(context, 0, 59, "%s分"));
        minute.setCurrentItem(curMinute);*/

        builder.setPositiveButton(R.string.negative, null);

        builder.setNegativeButton(R.string.positive, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (hourItemSelectedListener != null)
                    hourItemSelectedListener.onWheelItemSelected(null, 2003 + year.getCurrentItem(),
                            1 + month.getCurrentItem(), 1 + day.getCurrentItem(), hourArray[hour.getCurrentItem()], minuteArray[minute.getCurrentItem()]);
            }
        });
        String[] data = new String[1];
        builder.setWheelData(data, data, data);
        builder.setIsFullscreen(true);
        builder.setGravity(Gravity.BOTTOM);
        return builder;
    }



    /**
     * Updates day wheel. Sets max days according to selected month and year
     */
    private static void updateDays(WheelView year, WheelView month, WheelView day) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, 2003 + year.getCurrentItem());
        calendar.set(Calendar.MONTH, month.getCurrentItem());

        int maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        day.setViewAdapter(new NumericWheelAdapter(day.getContext(), 1, maxDays, "%s日"));
        int curDay = Math.min(maxDays, day.getCurrentItem() + 1);
        day.setCurrentItem(curDay - 1, true);
    }

    public interface onWheelItemSelectedListener {
        void onWheelItemSelected(Builder Builder, int year1, int month1, int day1, int year2, int month2, int day2);
    }

    public interface onWheelHourItemSelectedListener {
        void onWheelItemSelected(Builder Builder, int year1, int month1, int day1, String hour, String minute);
    }

    /*public interface onWheelItemSelectedListener {
        void onWheelItemSelected(Builder Builder, int postion);
    }*/

    public static Builder createTextTwoPicker(final Context context, final String[] data1, final Map map, @Nullable final wheelItemCurrentListener textSetListener) {

        final FAlertDialog.Builder builder = new FAlertDialog.Builder(context, FAlertDialog.LAYOUT_WHEEL);
        builder.setCancelable(true);
        builder.setCanceledOnTouchOutside(true);

        final WheelView wheel1 = (WheelView) builder.getContentView().findViewById(R.id.wheel1);
        final WheelView wheel2 = (WheelView) builder.getContentView().findViewById(R.id.wheel2);
        final String[] data2 = (String[]) map.get(data1[0]);

        wheel1.setViewAdapter(new ArrayWheelAdapter<>(context, data1));
        wheel2.setViewAdapter(new ArrayWheelAdapter<>(context, data2));

        wheel2.setCurrentItem(0);

        final OnWheelChangedListener listener = new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                updatecities(wheel1, wheel2, data1, map);
            }
        };
        wheel1.addChangingListener(listener);
        builder.setNegativeButton(R.string.positive, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (textSetListener != null) {
                    textSetListener.wheelCurrentSelected(builder, wheel1.getCurrentItem(), wheel2.getCurrentItem());
                }
            }
        });
        builder.setPositiveButton(R.string.negative, null);
        builder.setWheelData(data1, data2, null);
        builder.setIsFullscreen(true);
        builder.setGravity(Gravity.BOTTOM);
        return builder;
    }


    /**
     * Updates cities wheel
     */
    private static void updatecities(WheelView provinceWheel, WheelView citiesWheel, String[] provinceArray, Map map) {

        String proName = provinceArray[provinceWheel.getCurrentItem()];
        String[] citiesArry = (String[]) map.get(proName);
        citiesWheel.setViewAdapter(new ArrayWheelAdapter<>(citiesWheel.getContext(), citiesArry));
        citiesWheel.setCurrentItem(0);
    }

    public interface wheelItemCurrentListener {
        void wheelCurrentSelected(Builder Builder, int position1, int position2);
    }

    public interface wheelItemDateListener {
        void wheelCurrentSelected(String bText, String mText, String eText, boolean isDay);
    }

    public interface onWheel2ItemSelectedListener {
        void onWheelItemSelected(Builder Builder, Date date);
    }
}
