package com.chenganrt.smartSupervision.business.electronic.wheel;

import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;

import com.chenganrt.smartSupervision.R;
import com.chenganrt.smartSupervision.business.electronic.wheel.adapter.ArrayWheelAdapter;
import com.chenganrt.smartSupervision.business.electronic.wheel.adapter.ListWheelAdapter;
import com.chenganrt.smartSupervision.business.electronic.wheel.adapter.OnWheelChangedListener;
import com.chenganrt.smartSupervision.business.electronic.wheel.adapter.WheelView;

import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;


/**
 */
public class FDataPickerDialog extends FAlertDialog {
    protected FDataPickerDialog(Context context, int theme) {
        super(context, theme);
    }

    protected FDataPickerDialog(Context context) {
        super(context);
    }

    public static Builder createSingleDataPicker(final Context context, final String[] data, final OnWheelItemSelectedListener listener) {
        final FAlertDialog.Builder builder = new FAlertDialog.Builder(context, FAlertDialog.LAYOUT_WHEEL);
        builder.setCancelable(true);
        builder.setCanceledOnTouchOutside(true);

        final WheelView wheelView = (WheelView) builder.getContentView().findViewById(R.id.wheel1);
        wheelView.setViewAdapter(new ArrayWheelAdapter<>(context, data));
        builder.setNegativeButton(R.string.positive, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (data == null || data.length <= wheelView.getCurrentItem()) {
                    return;
                }
                if (listener != null) {
                    listener.onWheelItemSelected(builder, wheelView.getCurrentItem());
                }
            }
        });
        builder.setPositiveButton(R.string.negative, null);
        builder.setWheelData(data);
        builder.setIsFullscreen(true);
        builder.setGravity(Gravity.BOTTOM);
        return builder;
    }


    public static Builder createArrayPicker(Context context, final Object items[], final OnItemSelectListener listener) {
        final FAlertDialog.Builder builder = new FAlertDialog.Builder(context, FAlertDialog.LAYOUT_WHEEL);
        builder.setCancelable(true);
        builder.setCanceledOnTouchOutside(true);

        final WheelView wheel = (WheelView) builder.getContentView().findViewById(R.id.wheel1);
        wheel.setViewAdapter(new ArrayWheelAdapter<>(context, items));
        builder.setNegativeButton(R.string.positive, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (listener != null) {
                    listener.onItemSelect(wheel.getViewAdapter(), wheel.getCurrentItem());
                }
            }
        });
        builder.setPositiveButton(R.string.negative, null);
        builder.setIsFullscreen(true);
        builder.setGravity(Gravity.BOTTOM);
        builder.setWheelData(new String[1]);
        return builder;
    }

    public static Builder createListPicker(Context context, List lists, final OnItemSelectListener listener) {
        return createListPicker(context, lists, listener);
    }

    public static Builder createListPicker(Context context, List lists, int postion, final OnItemSelectListener listener) {
        final FAlertDialog.Builder builder = new FAlertDialog.Builder(context, FAlertDialog.LAYOUT_WHEEL);
        builder.setCancelable(true);
        builder.setCanceledOnTouchOutside(true);

        final WheelView wheel = (WheelView) builder.getContentView().findViewById(R.id.wheel1);
        wheel.setViewAdapter(new ListWheelAdapter<>(context, lists));
        wheel.setCurrentItem(postion);
        builder.setNegativeButton(R.string.positive, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (listener != null) {
                    listener.onItemSelect(wheel.getViewAdapter(), wheel.getCurrentItem());
                }
            }
        });
        builder.setWheelData(new String[1]);
        builder.setPositiveButton(R.string.negative, null);
        builder.setIsFullscreen(true);
        builder.setGravity(Gravity.BOTTOM);
        return builder;
    }


    public static Builder createTextThreePicker(final Context context, int position1, final int position2, final int position3, final String[] data1, final Map map, final Map map2, @Nullable final OnWheel2ItemSelectedListener textSetListener) {

        final FAlertDialog.Builder builder = new FAlertDialog.Builder(context, FAlertDialog.LAYOUT_WHEEL);
        builder.setCancelable(true);
        builder.setCanceledOnTouchOutside(true);

        final WheelView wheel1 = (WheelView) builder.getContentView().findViewById(R.id.wheel1);
        final WheelView wheel2 = (WheelView) builder.getContentView().findViewById(R.id.wheel2);
        final WheelView wheel3 = (WheelView) builder.getContentView().findViewById(R.id.wheel3);
        final String[] data2 = (String[]) map.get(data1[0]);
        final String[] data3 = (String[]) map2.get(data2[0]);

        wheel1.setViewAdapter(new ArrayWheelAdapter<>(context, data1));
        wheel2.setViewAdapter(new ArrayWheelAdapter<>(context, data2));
        wheel3.setViewAdapter(new ArrayWheelAdapter<>(context, data3));

        wheel1.setCurrentItem(position1);
        updatecities(wheel1, wheel2, wheel3, data1, map, map2, position2, position3);
        //updateArea(wheel2, wheel2, wheel3, data1, map, map2, position2);

        final OnWheelChangedListener listener = new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                updatecities(wheel1, wheel2, wheel3, data1, map, map2, 0, 0);
            }
        };

        final OnWheelChangedListener listener2 = new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                updateArea(wheel1, wheel2, wheel3, data1, map, map2, 0);
            }
        };

        wheel1.addChangingListener(listener);
        wheel2.addChangingListener(listener2);

        builder.setNegativeButton(R.string.positive, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (textSetListener != null) {
                    textSetListener.onWheel2ItemSelected(builder, wheel1.getCurrentItem(), wheel2.getCurrentItem(),wheel3.getCurrentItem());
                }
            }
        });
        builder.setPositiveButton(R.string.negative, null);
        builder.setWheelData(data1, data2, data3);
        builder.setIsFullscreen(true);
        builder.setGravity(Gravity.BOTTOM);
        return builder;
    }

    /**
     * Updates cities wheel
     */
    private static void updatecities(WheelView wheelView1, WheelView wheelView2, WheelView wheelView3, String[] data1, Map map, Map map2, int position2, int position3) {

        String proName = data1[wheelView1.getCurrentItem()];
        String[] data2 = (String[]) map.get(proName);
        String[] data3 = (String[]) map2.get(data2[0]);

        wheelView2.setViewAdapter(new ArrayWheelAdapter<>(wheelView2.getContext(), data2));
        wheelView3.setViewAdapter(new ArrayWheelAdapter<>(wheelView2.getContext(), data3));
        wheelView2.setCurrentItem(position2);
        wheelView3.setCurrentItem(position3);
    }

    private static void updateArea(WheelView wheelView1, WheelView wheelView2, WheelView wheelView3, String[] data1, Map map, Map map2, int position3) {
        String proName = data1[wheelView1.getCurrentItem()];
        String[] dataTwo = (String[]) map.get(proName);
        String[] dataThree = (String[]) map2.get(dataTwo[wheelView2.getCurrentItem()]);
        wheelView3.setViewAdapter(new ArrayWheelAdapter<>(wheelView3.getContext(), dataThree));
        wheelView3.setCurrentItem(position3);
    }

    public interface OnWheelItemSelectedListener {
        void onWheelItemSelected(Builder Builder, int postion);
    }

    public interface OnWheel2ItemSelectedListener {
        void onWheel2ItemSelected(Builder Builder, int position1, int position2, int position3);
    }

}
