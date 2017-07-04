package com.cesar.weather.ui.photos;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesar.weather.R;
import com.cesar.weather.domain.SaveCityUseCase;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlacePhotoMetadata;
import com.google.android.gms.location.places.PlacePhotoMetadataBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataResult;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

/**
 * Created by Cesar on 01/07/2017.
 */

public class PhotoFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;

    @BindView(R.id.city_text_view)      TextView placeNameTextView;
    @BindView(R.id.choose_place_button) Button choosePlaceButton;
    @BindView(R.id.photo_recycler_view) RecyclerView recyclerView;
    @BindView(R.id.loading_layout)      LinearLayout loadingLayout;
    @BindView(R.id.no_picture_layout)   LinearLayout noPictureLayout;

    private PhotoListAdapter photoListAdapter;
    private GoogleApiClient  googleApiClient;
    private Place            place;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.photo_fragment, null);
        ButterKnife.bind(this, view);
        showContent();
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                place = PlaceAutocomplete.getPlace(getActivity(), data);
                placeNameTextView.setText(place.getName().toString());

                //Save city
                SaveCityUseCase saveCityUseCase = new SaveCityUseCase(getContext());
                saveCityUseCase.save(place.getName().toString());

                if (googleApiClient == null) {
                    buildGoogleApiClient();
                    googleApiClient.connect();
                } else {
                    getPhotos();
                }
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(getActivity(), data);
                // TODO: Handle the error.
                Log.i(TAG, status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {

            }
        }
    }

    @OnClick(R.id.choose_place_button)
    public void submit(View view) {
        int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
        try {
            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .build(getActivity());
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }
    }

    private synchronized void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient
                .Builder(getActivity())
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        Log.v("buildGoogleApiClient", "Api built");
    }

    public void updateList(ArrayList<Bitmap> photosList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        photoListAdapter = new PhotoListAdapter(getActivity(), photosList);
        recyclerView.setAdapter(photoListAdapter);
    }

    private void getPhotos() {
        if (!googleApiClient.isConnected()) {
            googleApiClient.connect();
        }
        new GetPhotosTask().execute(place.getId());
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        getPhotos();
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

    // The definition of our task class
    private class GetPhotosTask extends AsyncTask<String, Void , ArrayList<Bitmap>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoading();
        }

        @Override
        protected ArrayList<Bitmap> doInBackground(String... params) {
            String idPlace = params[0];
            ArrayList<Bitmap> photosList = new ArrayList<>();

            // Get a PlacePhotoMetadataResult containing metadata for the first 10 photos.
            PlacePhotoMetadataResult result = Places.GeoDataApi
                    .getPlacePhotos(googleApiClient, idPlace).await();
            // Get a PhotoMetadataBuffer instance containing a list of photos (PhotoMetadata).
            if (result != null && result.getStatus().isSuccess()) {
                PlacePhotoMetadataBuffer photoMetadataBuffer = result.getPhotoMetadata();

                for (PlacePhotoMetadata photo : photoMetadataBuffer) {
                    // Get a full-size bitmap for the photo and add to the List
                    photosList.add(photo.getPhoto(googleApiClient).await().getBitmap());
                }
            }
            return photosList;
        }

        @Override
        protected void onPostExecute(ArrayList<Bitmap> result) {
            super.onPostExecute(result);
            if (result != null && !result.isEmpty()){
                showContent();
                updateList(result);
            }else{
                showNoPicture();
            }
        }
    }

    public void showNoPicture() {
        recyclerView.setVisibility(View.INVISIBLE);
        loadingLayout.setVisibility(View.INVISIBLE);
        noPictureLayout.setVisibility(View.VISIBLE);
    }


    public void showLoading() {
        loadingLayout.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
        noPictureLayout.setVisibility(View.GONE);
    }

    public void showContent() {
        recyclerView.setVisibility(View.VISIBLE);
        loadingLayout.setVisibility(View.INVISIBLE);
        noPictureLayout.setVisibility(View.GONE);
    }
}
