package com.cesar.weather.ui.record;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cesar.weather.R;
import com.cesar.weather.domain.SaveCityUseCase;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cesar on 04/07/2017.
 */

public class RecordFragment extends Fragment{

    @BindView(R.id.photo_recycler_view) RecyclerView recyclerView;
    private RecordListAdapter recordListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.record_fragment, null);
        ButterKnife.bind(this, view);
        updateList();
        return view;
    }

    public void updateList(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recordListAdapter = new RecordListAdapter(getRecords());
        recyclerView.setAdapter(recordListAdapter);
    }

    public List<String> getRecords(){
        SaveCityUseCase saveCityUseCase = new SaveCityUseCase(getActivity());
        return saveCityUseCase.getCities();
    }
}
