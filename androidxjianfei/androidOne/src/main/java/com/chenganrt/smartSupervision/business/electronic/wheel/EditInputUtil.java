package com.chenganrt.smartSupervision.business.electronic.wheel;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.widget.EditText;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditInputUtil {
    /**
     * this will add a new inputfilter
     *
     * @param text
     */
    public static void makeEdtOnlyChinese(EditText text) {
        InputFilter[] oldFilters = text.getFilters();
        if (oldFilters == null) {
            oldFilters = new InputFilter[0];
        }
        int size = oldFilters.length;
        InputFilter[] newFilters = Arrays.copyOf(oldFilters, size + 1);
        newFilters[size] = onlyChineseIF;
        text.setFilters(newFilters);
    }

    private static InputFilter onlyChineseIF = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            String temp = source.subSequence(start, end).toString();
            if (!TextUtils.isEmpty(temp)
                    && !temp.matches("[\\u4E00-\\u9FA5]+") && !temp.matches("[0-9]*") && !temp.matches("[a-zA-Z]")) {
                return "";
            }
            return null;
        }
    };

    public static void setEditTextInhibitInputSpeChat(EditText editText) {

        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                String speChat = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
                Pattern pattern = Pattern.compile(speChat);
                Matcher matcher = pattern.matcher(source.toString());
                if (matcher.find()) return "";
                if (source.equals(" ")) return "";
                else return null;
            }
        };
        editText.setFilters(new InputFilter[]{filter});
    }

}
