package com.luisgonzalez.demo.FRAGMENTS;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.luisgonzalez.demo.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link mHomeFragmentView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class mHomeFragmentView extends Fragment implements LocationListener, OnMapReadyCallback, GoogleMap.OnMyLocationChangeListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private MapView mapView;
    private GoogleMap mMap;
    private LocationManager locationManager;
    private String locationProvider;
    private View view;


    public mHomeFragmentView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment mHomeFragmentView.
     */
    // TODO: Rename and change types and number of parameters
    public static mHomeFragmentView newInstance(String param1, String param2) {
        mHomeFragmentView fragment = new mHomeFragmentView();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            view = inflater.inflate(R.layout.home_fragment_view, container, false);
        } catch (InflateException e) {

        }

        mapView = (MapView) view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        setupMapIfneed();
        initializeLocationManager();
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mMap = googleMap;
        initializeMap();
    }

    private void initializeMap() {
        MapsInitializer.initialize(getActivity());
        this.mMap.getUiSettings().setMapToolbarEnabled(false);
        this.mMap.setMyLocationEnabled(true);
        this.mMap.setOnMyLocationChangeListener(this);
        this.mMap.getUiSettings().setMyLocationButtonEnabled(false);
        this.mMap.getUiSettings().setTiltGesturesEnabled(false);
    }

    public void setupMapIfneed() {
        if (mMap == null) {
            mapView.getMapAsync(this);
        }
    }


    /* location changed method */

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            if (mMap != null) {
                setlocationInTheMAp(location);
            }
        }
    }

    public void centerMylocation(Location location) {

        setlocationInTheMAp(location);

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    /* Cliclo de vida */

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
        setupMapIfneed();
        if (ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(
                locationProvider, 30000, 5, this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        locationManager.removeUpdates(this);
    }

    private void initializeLocationManager() {

        this.locationManager = (LocationManager) this.getActivity().getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.POWER_HIGH);

        this.locationProvider = locationManager.getBestProvider(criteria, false);


        if (ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(
                locationProvider, 30000, 5, this);


        Location location = locationManager.getLastKnownLocation(locationProvider);
        if(location != null) {

            onLocationChanged(location);

        }
        else {
            Log.d("current","localidad nula");
            }

    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
        mMap=null;
        locationManager.removeUpdates(this);
    }

    @Override
    public void onMyLocationChange(Location location) {
            centerMylocation(location);
    }

    public  void setlocationInTheMAp(Location location){
          /*JSONObject object = new JSONObject(Myapp.getInstance().getJsonStringUser());
            String stl =object.getJSONArray("auth").getJSONObject(0).getString("NAME_PROFILE");*/
        CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude()));
        mMap.clear();
        mMap.moveCamera(center);
        mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).title("Aqui estas revisando la app "));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(17);
        mMap.animateCamera(zoom);
    }
}

