package com.chenganrt.smartSupervision.business.electronic.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chenganrt.smartSupervision.R;

import java.util.ArrayList;
import java.util.List;



public class ImageAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater mInflater;
    private List<Object> objectList = new ArrayList<>();

    public ImageAdapter(Context context) {
        this.context = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void refresh(List<Uri> stringList) {
        if (stringList == null) return;
        objectList.clear();
        objectList.add(Uri.EMPTY);
        objectList.addAll(stringList);
        notifyDataSetChanged();
    }

    public void addImage(Uri uri) {
        if (uri == null) return;
        objectList.add(uri);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return objectList.size();
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return objectList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = mInflater.inflate(R.layout.item_image, viewGroup, false);
            holder = new ViewHolder(view);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        if (holder != null) {
            holder.bindData(position, (Uri) getItem(position));
        }

        return view;
    }

    class ViewHolder {
        ImageButton imageButton;

        public ViewHolder(View view) {
            imageButton = (ImageButton) view.findViewById(R.id.image);
            view.setTag(this);
        }

        public void bindData(int position, Uri uri) {
            if (position == 0) {
                imageButton.setImageResource(R.drawable.camera);
            } else {
                imageButton.setScaleType(ImageView.ScaleType.CENTER_CROP);
                Glide.with(context).load(uri).into(imageButton);
            }
        }
    }
}
