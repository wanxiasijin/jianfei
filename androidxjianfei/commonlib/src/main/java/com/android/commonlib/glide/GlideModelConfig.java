package com.android.commonlib.glide;

import android.content.Context;

import com.android.commonlib.R;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;

@GlideModule
public class GlideModelConfig extends AppGlideModule{

    private long diskSize = 1024 * 1024 * 100;
    private long memorySize = (Runtime.getRuntime().maxMemory()) / 8; // 取1/8最大内存作为最大缓存

    public boolean isManifestParsingEnabled() {
        return false;
    }


    public void applyOptions(Context context, GlideBuilder builder) {
        builder.setMemoryCache(new LruResourceCache(memorySize))//通过操作运行时内存中的资源实现的
                .setBitmapPool(new LruBitmapPool(memorySize))//用来允许重复利用许多大小不同的需要回收的Bitmap对象
                .setDiskCache(new InternalCacheDiskCacheFactory(context, diskSize))  //硬盘缓存
                .setDefaultRequestOptions(new RequestOptions().error(R.drawable.ic_launcher).format(DecodeFormat.PREFER_ARGB_8888));
    }
}
