package com.android.commonlib.glide;

import android.content.Context;
import android.widget.ImageView;

import com.android.commonlib.R;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

public class Glide {

    private volatile static Glide mGlide;

    public static Glide getInstance() {
        if (mGlide == null) {
            synchronized (Glide.class) {
                if (mGlide == null) {
                    mGlide = new Glide();
                }
            }
        }
        return mGlide;
    }

    public void displayImage(Context context, Object path, ImageView imageView) {
        //报红是没编译的问题
        GlideApp.with(context)
                .load(path)
                .placeholder(R.drawable.tfjh_moren_pic)
                .error(R.drawable.tfjh_moren_pic)
                .transition(DrawableTransitionOptions.withCrossFade())//加载动画淡入淡出
                .into(imageView);
    }

    public void displayGif(Context context, int drawable, ImageView imageView) {
        //报红是没编译的问题
        GlideApp.with(context)
                .load(drawable)
                .transition(DrawableTransitionOptions.withCrossFade())//加载动画淡入淡出
                .into(imageView);
    }
}
