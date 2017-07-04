package com.cesar.weather.ui.record;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cesar.weather.R;

import java.util.List;

/**
 * Created by Cesar on 01/07/2017.
 */

public class RecordListAdapter extends RecyclerView.Adapter<RecordItemViewHolder> {


    @Override
    public RecordItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_record, parent, false);
        RecordItemViewHolder recordItemViewHolder = new RecordItemViewHolder(view);
        return recordItemViewHolder;
    }

    @Override
    public void onBindViewHolder(RecordItemViewHolder holder, int position) {
        holder.bind(recordItemList.get(position));
    }

    @Override
    public int getItemCount() {
        return recordItemList.size();
    }

    private List<String> recordItemList;

    public RecordListAdapter(List<String> recordItemList) {
        this.recordItemList = recordItemList;
    }

}
