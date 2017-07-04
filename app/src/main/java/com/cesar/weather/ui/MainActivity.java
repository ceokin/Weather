package com.cesar.weather.ui;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.cesar.weather.R;
import com.cesar.weather.domain.CityWeatherUseCase;
import com.cesar.weather.domain.CoorWeatherUseCase;
import com.cesar.weather.domain.SaveCityUseCase;
import com.cesar.weather.ui.model.UICityWeather;
import com.cesar.weather.ui.photos.PhotoFragment;
import com.cesar.weather.ui.record.RecordFragment;
import com.cesar.weather.ui.weather.WeatherFragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;

import static com.google.android.gms.location.LocationServices.FusedLocationApi;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, WeatherFragment.OnWeatherFragmentListener{

    //Google APIs cliente.
    private GoogleApiClient googleApiClient;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private WeatherFragment weatherFragment;
    private PhotoFragment photoFragment;
    private RecordFragment recordFragment;
    private ContactFragment contactFragment;

    @BindView(R.id.loading_layout) View loadingLayout;
    @BindView(R.id.container_view) View contentLayout;
    @BindView(R.id.error_layout) View errorLayout;

    private BottomNavigationView bottomNavigationView;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.weather_navigation:
                    weatherFragment = new WeatherFragment();
                    switchFragment(weatherFragment);
                    setActionBarTitle(getString(R.string.menu_weather));
                    return true;
                case R.id.pictures_navigation:
                    photoFragment = new PhotoFragment();
                    switchFragment(photoFragment);
                    setActionBarTitle(getString(R.string.menu_pictures));
                    return true;
                case R.id.record_navigation:
                    recordFragment = new RecordFragment();
                    switchFragment(recordFragment);
                    setActionBarTitle(getString(R.string.menu_record));
                    return true;
                case R.id.contact_navigation:
                    contactFragment = new ContactFragment();
                    switchFragment(contactFragment);
                    setActionBarTitle(getString(R.string.menu_zona_app));
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //Lets inflate the very first fragment
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        weatherFragment = new WeatherFragment();
        switchFragment(weatherFragment);

        buildGoogleApiClient();

        setActionBarTitle(getString(R.string.menu_weather));
        showContent();
    }

    private synchronized void buildGoogleApiClient(){
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        Log.v("buildGoogleApiClient", "Api built");
    }

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    protected void onStop(){
        super.onStop();
        if (googleApiClient.isConnected()){
            googleApiClient.disconnect();
        }
    }

    private Location getLocation(){
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(
                    this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        Location location = FusedLocationApi.getLastLocation(googleApiClient);

        return location;
    }

    private void updateDataByCoor(String lon, String lat) {
        showLoading();
        new CoorWeatherUseCase(lon, lat).execute(new WeatherSubscriber());
    }

    private void updateDataByCity(String cityName) {
        showLoading();
        new CityWeatherUseCase(cityName).execute(new WeatherSubscriber());
    }

    private final class WeatherSubscriber extends Subscriber<List<UICityWeather>> {

        //3 callbacks
        @Override public void onCompleted() {
            showContent();
        }

        @Override public void onError(Throwable e) {
            showError();
        }

        @Override
        public void onNext(List<UICityWeather> listCityWeather) {
            SaveCityUseCase saveCityUseCase = new SaveCityUseCase(getApplicationContext());
            saveCityUseCase.save(listCityWeather.get(0).getCityName());
            weatherFragment.updateList(listCityWeather);
        }
    }

    // switching fragments
    private void switchFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_view, fragment).commit();
    }

    private void setActionBarTitle(String cad){
        getSupportActionBar().setTitle(cad);
    }

    //Google interface
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Location location = getLocation();

        if (location != null) {
            double lon = location.getLongitude();
            double lat = location.getLatitude();

            updateDataByCoor(String.valueOf(lat), String.valueOf(lon));
        } else {
            weatherFragment.showErroLocation();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i("onConnectionSuspended", "Connection suspended");
        googleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i("onConnectionFailed", "Connection failed: ConnectionResult.getErrorCode() = " + connectionResult.getErrorCode() + " - " + connectionResult.getErrorMessage());
    }

    //WeatherFragment Interface
    @Override
    public void onSearchButtonWeather(String cityName) {
        updateDataByCity(cityName);
    }

    @Override
    public void onPositionActionBarWeather() {
        if (googleApiClient == null){
            buildGoogleApiClient();
            googleApiClient.connect();
        }else{
            if (!googleApiClient.isConnected()){
                googleApiClient.connect();
            }
            Location location = getLocation();

            if (location != null) {
                double lon = location.getLongitude();
                double lat = location.getLatitude();

                updateDataByCoor(String.valueOf(lat), String.valueOf(lon));
            } else {
                weatherFragment.showErroLocation();
            }
        }
    }

    public void showError() {
        contentLayout.setVisibility(View.INVISIBLE);
        loadingLayout.setVisibility(View.INVISIBLE);
        errorLayout.setVisibility(View.VISIBLE);
    }


    public void showLoading() {
        loadingLayout.setVisibility(View.VISIBLE);
        contentLayout.setVisibility(View.INVISIBLE);
        errorLayout.setVisibility(View.GONE);
    }

    public void showContent() {
        contentLayout.setVisibility(View.VISIBLE);
        loadingLayout.setVisibility(View.INVISIBLE);
        errorLayout.setVisibility(View.GONE);
    }
}
