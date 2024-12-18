package com.example.bingebook;

import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ImageLoader {

    public void loadImageFromUrl(String url, ImageView imageView) {
        Glide.with(imageView.getContext())
                .load(url)

                .into(imageView);
    }
}
