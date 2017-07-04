package com.cesar.weather.ui.record;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cesar.weather.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cesar on 01/07/2017.
 */

public class RecordItemViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.place_name_editText) TextView cityNameTextview;

    public RecordItemViewHolder(View itemView){
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(String cityName) {
        cityNameTextview.setText(cityName);
    }
}
