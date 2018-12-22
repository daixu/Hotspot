package com.jxs.hotspot.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.jxs.hotspot.utils.UIUtils;
import com.jxs.hotspot.widget.GlideApp;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class NetworkImageHolderView implements Holder<String> {
    private ImageView imageView;

    @Override
    public View createView(Context context) {
        imageView = new ImageView(context);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, int position, String data) {
        if (null != data) {
            MultiTransformation multiTransformation = new MultiTransformation(
                    new CenterCrop(),
                    new RoundedCornersTransformation(UIUtils.dip2px(context, 5), 0, RoundedCornersTransformation.CornerType.ALL));

            GlideApp.with(context)
                    .load(data)
                    .apply(RequestOptions.bitmapTransform(multiTransformation))
                    .into(imageView);
        }
    }
}