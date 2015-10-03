package notika.assignment_4;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.location.Location;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.LocationListener;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements GoogleApiClient.ConnectionCallbacks,
        LocationListener {

    MediaPlayer mediaPlayer;
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private GoogleApiClient mGoogleApiClient;
    Marker hereMarker;
    Marker checkPointMarker;
    int cameraZoom = 19;
    boolean _debug = false;
    int[] checkPoints;
    int currentPosition = 0;

    ArrayList<Treasure> treasures = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        fillData();
        Resources r = getResources();
        checkPoints = r.getIntArray(R.array.checkpoints);

        setUpMapIfNeeded();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGoogleApiClient.connect();
    }

    @Override
    public void onPause() {
        super.onPause();
        mGoogleApiClient.disconnect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        mGoogleApiClient.disconnect();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
                UiSettings uiSettings = mMap.getUiSettings();
                uiSettings.setZoomControlsEnabled(true);
                uiSettings.setMyLocationButtonEnabled(true);
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker for first position, defined in route.xml
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        if (_debug == true) {
            for (int i = 0; i < treasures.size(); i++) {
                // Set up all checkpoints, to show what do we have
                mapAddCheckpoint(i);
            }
        } else {
            // Set up first checkpoint
            mapAddCheckpoint(checkPoints[currentPosition]);
        }
    }

    private void fillData() {

        for (TypedArray item : ResourceHelper.getMultiTypedArray(getApplicationContext())) {
            Treasure mTreasures = new Treasure(item.getString(0), item.getString(1), item.getString(2), item.getString(3), item.getString(4), item.getString(5), item.getInt(6, 0), item.getResourceId(7, -1));
            treasures.add(mTreasures);
        }
    }

    private void checkForTreasure(Location imHere) {

        Location newLocation = new Location("new point");
        newLocation.setLatitude(Double.parseDouble(treasures.get(checkPoints[currentPosition]).lat));
        newLocation.setLongitude(Double.parseDouble(treasures.get(checkPoints[currentPosition]).lng));
        float dist = imHere.distanceTo(newLocation);

        if (dist < 10) {
            Bundle args = new Bundle();
            android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            DialogFragment newFragment = new MyDialogFragment();
            args.putString("question", treasures.get(checkPoints[currentPosition]).question);
            args.putString("alt1", treasures.get(checkPoints[currentPosition]).alt_1);
            args.putString("alt2", treasures.get(checkPoints[currentPosition]).alt_2);
            args.putString("alt3", treasures.get(checkPoints[currentPosition]).alt_3);

            newFragment.setArguments(args);
            newFragment.show(ft, "dialog");

            // Vibrate
            Vibrator mVibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            mVibrator.vibrate(200);

            // Play music
            mediaPlayer = MediaPlayer.create(this, R.raw.metal_gong_dianakc);
            mediaPlayer.start();

            // Suspend map update until user finish Questioning dialog
            if (mGoogleApiClient.isConnected()) {
                mGoogleApiClient.disconnect();
            }
        }
    }

    private void mapAddCheckpoint(int currentPosition) {
        checkPointMarker = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(Double.parseDouble(treasures.get(currentPosition).lat), Double.parseDouble(treasures.get(currentPosition).lng)))
                .title(treasures.get(currentPosition).question)
                .icon(BitmapDescriptorFactory.fromResource(treasures.get(currentPosition).icon)));
    }

    @Override
    public void onConnected(Bundle bundle) {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        if (hereMarker != null) {
            hereMarker.remove();
        }
        // Getting latitude of the current location
        double latitude = location.getLatitude();

        // Getting longitude of the current location
        double longitude = location.getLongitude();

        // Creating a LatLng object for the current location
        LatLng latLng = new LatLng(latitude, longitude);
        hereMarker = mMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_check_p_zero)).title("You're here"));
        // Showing the current location in Google Map
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        // Zoom in the Google Map
        mMap.animateCamera(CameraUpdateFactory.zoomTo(cameraZoom));

        checkForTreasure(location);
    }

    // DialogFragment callback function
    public void onUserSelectValue(int selectedValue) {

        if (selectedValue != treasures.get(checkPoints[currentPosition]).answer) {
            Toast.makeText(getApplicationContext(), "Try again! ", Toast.LENGTH_LONG).show();
        } else {
            currentPosition++;
            if (currentPosition >= checkPoints.length) {
                currentPosition = 0;
                Toast.makeText(getApplicationContext(), "You win! Game Over!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Good job! Go to the next point", Toast.LENGTH_LONG).show();
                if (checkPointMarker != null) {
                    checkPointMarker.remove();
                }
                // Set up next checkpoint
                mapAddCheckpoint(checkPoints[currentPosition]);
            }
        }
        mGoogleApiClient.connect();
    }
}
