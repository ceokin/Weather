package com.cesar.weather.ui.photos;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.cesar.weather.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cesar on 01/07/2017.
 */

public class PhotoItemViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.photo_imageView) ImageView photoImageView;

    public PhotoItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public void bind(final Bitmap photoItem) {
         photoImageView.setImageBitmap(photoItem);
    }
}
