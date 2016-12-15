package id.gits.kiblat.view;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import id.gits.kiblat.R;
import id.gits.kiblat.databinding.KiblatActivityBinding;
import id.gits.mvvmcore.activity.GitsActivity;


public class KiblatActivity extends GitsActivity<KiblatVM, KiblatActivityBinding> implements KiblatVM.KiblatView, OnMapReadyCallback {


    GoogleMap mGoogleMap;
    Location myLocation;


    @Override
    protected int getToolbarId() {
        return R.id.toolbar;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onResume() {
        super.onResume();


    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mViewModel.Destroy();

    }

    @Override
    public int getResLayout() {
        return R.layout.kiblat_activity;
    }

    @Override
    public KiblatVM getViewModel() {
        return new KiblatVM(this, this);
    }

    @Override
    public void bindViewModel(KiblatActivityBinding binding, KiblatVM viewModel) {
        binding.setVm(viewModel);

    }


    @Override
    public void showAzimut(Location myLocation, Location kabaaLoc) {
        this.myLocation = myLocation;
        mGoogleMap.clear();
        float azimuth = myLocation.bearingTo(kabaaLoc);
        azimuth = azimuth < 0 ? azimuth + 360 : azimuth;
        mBinding.compassView.setAzimuth(azimuth);
        showPolyline(myLocation);
    }

    private void showPolyline(Location myLocation) {
        List<LatLng> lokasi = new ArrayList<>();

        LatLng kabaa = new LatLng(21.422487, 39.826206);
        lokasi.add(kabaa);
        lokasi.add(new LatLng(myLocation.getLatitude(), myLocation.getLongitude()));
        mGoogleMap.addMarker(new MarkerOptions().position(kabaa));
        mGoogleMap.addPolyline(new PolylineOptions()
                .addAll(lokasi)
                .width(2).color(Color.RED));
        mGoogleMap.setMyLocationEnabled(true);
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
        mGoogleMap.getUiSettings().setCompassEnabled(true);
//
//        LatLngBounds.Builder b = new LatLngBounds.Builder();
//        for (LatLng m : lokasi) {
//            b.include(m);
//        }
//        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(b.build(), 150));
    }


    @Override
    public void initMap() {
        mBinding.layoutMap.setVisibility(View.VISIBLE);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(KiblatActivity.this);
    }

    @Override
    public void showError(String message) {
        Snackbar snackbar = Snackbar.make(mBinding.layoutMap,message,Snackbar.LENGTH_INDEFINITE);
        snackbar.show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
//
//                List<LatLng> lokasi = new ArrayList<>();
//
//        LatLng kabaa = new LatLng(21.422487, 39.826206);
//        lokasi.add(kabaa);
//        lokasi.add(new LatLng(myLocation.getLatitude(), myLocation.getLongitude()));
//        mGoogleMap.addMarker(new MarkerOptions().position(kabaa));
//        mGoogleMap.addPolyline(new PolylineOptions()
//                .addAll(lokasi)
//                .width(2).color(Color.RED));
//        mGoogleMap.setMyLocationEnabled(true);
//        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
//        mGoogleMap.getUiSettings().setCompassEnabled(true);
//
//        LatLngBounds.Builder b = new LatLngBounds.Builder();
//        for (LatLng m : lokasi) {
//            b.include(m);
//        }
//        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(b.build(), 100));

    }
}
