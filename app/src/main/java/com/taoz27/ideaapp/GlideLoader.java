package com.taoz27.ideaapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.youth.banner.loader.ImageLoader;

/**
 * Created by taoz27 on 2017/11/20.
 */

public class GlideLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        imageView.setImageBitmap((Bitmap)path);
    }

    @Override
    public ImageView createImageView(Context context) {
        return new ImageView(context);
    }
}
