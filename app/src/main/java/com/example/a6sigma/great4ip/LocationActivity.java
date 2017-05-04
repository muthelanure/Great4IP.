//package com.example.a6sigma.great4ip;
//
//import android.Manifest;
//import android.content.pm.PackageManager;
//import android.location.Location;
//import android.os.Build;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.design.widget.FloatingActionButton;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.content.ContextCompat;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//import android.view.View;
//
//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.location.LocationListener;
//import com.google.android.gms.location.LocationRequest;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.model.CameraPosition;
//import com.google.android.gms.maps.model.LatLng;
//
///**
// * Created by bill power on 5/1/2017.
// */
//
//public class LocationActivity extends AppCompatActivity implements OnMapReadyCallback,
//        GoogleApiClient.ConnectionCallbacks,
//        GoogleApiClient.OnConnectionFailedListener,
//        LocationListener {
//
//    private SupportMapFragment mapFragment;
//    private GoogleMap map;
//    private GPSTracker gps;
//    public static double myLat;
//    public static double myLng;
//
//    private String TAG = LocationActivity.class.getSimpleName();
//
//    private FloatingActionButton fabLocation;
//    private GoogleApiClient mGoogleApiClient;
//    private AppCompatActivity context = LocationActivity.this;
//    private LatLng latLngMyLoc;
//    private LocationRequest mLocationRequest;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.massage_location);
//
//        checkingPermssion();
//
//        fabLocation = (FloatingActionButton) findViewById(R.id.fab_location);
//
//        gps = new GPSTracker(this);
//        myLat = gps.getLatitude();
//        myLng = gps.getLongitude();
//
//        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
//
//        fabLocation.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.e(TAG, "MyLng: " + myLng);
//            }
//        });
//
//        latLngMyLoc = new LatLng(myLat, myLng);
//    }
//
//    private void checkingPermssion() {
//        int PERMISSION_ALL = 35;
//        String[] PERMISSIONS = {
//                Manifest.permission.ACCESS_FINE_LOCATION,
//                Manifest.permission.ACCESS_COARSE_LOCATION,
//                Manifest.permission.CAMERA,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE
//        };
//
//        if (!AppInfo.hasPermissions(context, PERMISSIONS)) {
//            ActivityCompat.requestPermissions(context, PERMISSIONS, PERMISSION_ALL);
//        }
//
//
//    }
//
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        map = googleMap;
//
//        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//
//        CameraPosition cameraPosition = new CameraPosition.Builder()
//                .target(new LatLng(myLat, myLng))
//                .zoom(15)
//                .build();
//        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
//
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        map.setMyLocationEnabled(true);
//
//    }
//
//    /* Location - Setting reader myLocation */
//    protected synchronized void buildGoogleApiClient() {
//        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .addConnectionCallbacks(this)
//                .addOnConnectionFailedListener(this)
//                .addApi(LocationServices.API)
//                .build();
//        mGoogleApiClient.connect();
//    }
//
//    public void buttonMyLocationClick(View v) {
//        Log.e("MyLocation ", myLat + "," + myLng);
//        mapFragment.getMapAsync(new OnMapReadyCallback() {
//
//            @Override
//            public void onMapReady(GoogleMap googleMap) {
//                map = googleMap;
//
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    if (ContextCompat.checkSelfPermission(context,
//                            Manifest.permission.ACCESS_FINE_LOCATION)
//                            == PackageManager.PERMISSION_GRANTED) {
//                        buildGoogleApiClient();
//                        map.moveCamera(CameraUpdateFactory.newLatLng(latLngMyLoc));
//                        map.animateCamera(CameraUpdateFactory.zoomTo(15));
//
//                    }
//                } else {
//                    buildGoogleApiClient();
//                    map.moveCamera(CameraUpdateFactory.newLatLng(latLngMyLoc));
//                    map.animateCamera(CameraUpdateFactory.zoomTo(15));
//                }
//            }
//        });
//    }
//
//    @Override
//    public void onConnected(@Nullable Bundle bundle) {
//        mLocationRequest = new LocationRequest();
//        mLocationRequest.setInterval(1000);
//        mLocationRequest.setFastestInterval(1000);
//        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
//        if (ContextCompat.checkSelfPermission(this,
//                Manifest.permission.ACCESS_FINE_LOCATION)
//                == PackageManager.PERMISSION_GRANTED) {
//            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
//        }
//    }
//
//    @Override
//    public void onConnectionSuspended(int i) {
//
//    }
//
//    @Override
//    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//
//    }
//
//    @Override
//    public void onLocationChanged(Location location) {
//
//    }
//}
