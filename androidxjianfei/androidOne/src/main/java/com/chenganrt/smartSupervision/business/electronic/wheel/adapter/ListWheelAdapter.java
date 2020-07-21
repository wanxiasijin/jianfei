/*
 *  Copyright 2011 Yuri Kanivets
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.chenganrt.smartSupervision.business.electronic.wheel.adapter;

import android.content.Context;

import java.util.List;

/**
 * The simple Array wheel adapter
 *
 * @param <T> the element type
 */
public class ListWheelAdapter<T> extends AbstractWheelTextAdapter {

    // items
    private List<T> lists;

    /**
     * Constructor
     *
     * @param context the current context
     * @param lists   the items
     */
    public ListWheelAdapter(Context context, List<T> lists) {
        super(context);
        this.lists = lists;
    }

    @Override
    public CharSequence getItemText(int index) {
        if (index >= 0 && index < getItemsCount()) {
            T item = lists.get(index);
            if (item instanceof CharSequence) {
                return (CharSequence) item;
            }
            return item.toString();
        }
        return null;
    }

    @Override
    public int getItemsCount() {
        if (lists != null) {
            return lists.size();
        }
        return 0;
    }

    public T getItem(int position) {
        if (position < getItemsCount()) {
            return lists.get(position);
        }
        return null;
    }
}
