package com.cesar.weather.ui.photos;

/**
 * Created by Cesar on 01/07/2017.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cesar.weather.R;

import java.util.List;


public class PhotoListAdapter extends RecyclerView.Adapter<PhotoItemViewHolder> {
    private List<Bitmap> photoItemList;
    private Context context;

    public PhotoListAdapter(Context context, List<Bitmap> photoItemList) {
        this.context = context;
        this.photoItemList = photoItemList;
    }

    @Override
    public PhotoItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.photo_item_layout, viewGroup, false);
        return new PhotoItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PhotoItemViewHolder viewHolder, int position) {
        viewHolder.bind(photoItemList.get(position));
    }

    @Override
    public int getItemCount() {
        return photoItemList.size();
    }
}
