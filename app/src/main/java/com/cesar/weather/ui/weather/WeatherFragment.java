package com.cesar.weather.ui.weather;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cesar.weather.R;
import com.cesar.weather.ui.model.UICityWeather;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Cesar on 01/07/2017.
 */

public class WeatherFragment  extends Fragment {

    @BindView(R.id.search_city_edit_text) EditText searchEditText;
    @BindView(R.id.search_button) Button searchButton;
    @BindView(R.id.city_text_view) TextView cityTextView;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    private WeatherListAdapter weatherListAdapter;

    public interface OnWeatherFragmentListener{
        void onSearchButtonWeather(String townName);
        void onPositionActionBarWeather();
    }

    OnWeatherFragmentListener onWeatherListFragmentListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.weather_fragment, null);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        return view;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if (context instanceof OnWeatherFragmentListener){
            onWeatherListFragmentListener = (OnWeatherFragmentListener) context;
        }else{
            throw new ClassCastException(context.toString() + " must implements WeatherFragment.OnWeatherFragmentListener");
        }
    }

    @Override
    public void onCreateOptionsMenu(
            Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case R.id.action_position:
                searchEditText.getText().clear();
                onWeatherListFragmentListener.onPositionActionBarWeather();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @OnClick(R.id.search_button)
    public void submit(View view) {
        onWeatherListFragmentListener.onSearchButtonWeather(searchEditText.getText().toString());
        closeSoftKeyboard();
    }

    public void updateList(List<UICityWeather> weatherItemList){
        cityTextView.setText(weatherItemList.get(0).getCityName());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        weatherListAdapter = new WeatherListAdapter(getActivity(), weatherItemList);
        recyclerView.setAdapter(weatherListAdapter);
    }

    public void showErroLocation(){
        Toast.makeText(getActivity(), R.string.no_location_detected, Toast.LENGTH_LONG).show();
    }

    public  void closeSoftKeyboard() {
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }
}
