package id.gits.kiblat.location;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import id.gits.kiblat.rxeventbus.MyRxBus;
import id.gits.kiblat.rxeventbus.RxEventBusProvider;

/**
 * Created by yogi on 02/12/16.
 */
public class MyLocation {

    private GoogleApiClient mGoogleApiClient;
    private ApiCallbacks mApiCallbacks;
    private Location mLocation;
    MyRxBus rxBus;

    private static final LocationRequest REQUEST = LocationRequest.create()
            .setInterval(5000)// update location every 5 second
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

    public MyLocation(Context context){
        rxBus = RxEventBusProvider.provide();
        mApiCallbacks = new ApiCallbacks();
        mGoogleApiClient= new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(mApiCallbacks)
                .addOnConnectionFailedListener(mApiCallbacks)
                .build();

    }

    public void connect(){
        if (!mGoogleApiClient.isConnected()){
            mGoogleApiClient.connect();
        }
    }

    public void disconnect(){
        if (mGoogleApiClient.isConnected() || mGoogleApiClient.isConnecting()) {
            mGoogleApiClient.disconnect();
        }
    }


    private class ApiCallbacks implements GoogleApiClient.ConnectionCallbacks,
            GoogleApiClient.OnConnectionFailedListener,
            LocationListener {
        @Override
        public void onConnected(@Nullable Bundle bundle) {



            mLocation  =LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            Log.e( "onConnected: ", "Connect"+ mLocation.getLongitude());
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,REQUEST,  this);




        }

        @Override
        public void onConnectionSuspended(int i) {

        }

        @Override
        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        }


        @Override
        public void onLocationChanged(Location location) {

            rxBus.post(new LocationMdl(mLocation));
            mLocation   = location;

        }

    }


    public static class LocationMdl {
        private Location location;
        public LocationMdl(Location location) {
            this.location = location;
        }

        public Location getLocation() {
            return location;
        }
    }
}