package com.horses.launchpad.lima16;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class DisplayListener extends SimpleImageLoadingListener {

    static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<>());

    @Override
    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
        if (loadedImage != null) {
            ImageView imageView = (ImageView) view;
            boolean firstDisplay = !displayedImages.contains(imageUri);
            if (firstDisplay) {
                FadeInBitmapDisplayer.animate(imageView, 500);
                displayedImages.add(imageUri);
            }
        }
    }

    public static final DisplayImageOptions optionsImage = new DisplayImageOptions.Builder()
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .showImageOnFail(R.drawable.empty)
            .showImageForEmptyUri(R.drawable.empty)
            .showImageOnLoading(R.drawable.empty)
            .build();

    public static final DisplayImageOptions optionsImageProfile = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.drawable.empty_person)
            .showImageOnFail(R.drawable.empty_person)
            .showImageForEmptyUri(R.drawable.empty_person)
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .build();

}
