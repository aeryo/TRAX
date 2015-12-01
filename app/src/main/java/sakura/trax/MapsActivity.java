package sakura.trax;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Random;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private TextView status;
    private Circle circle;

    //Set colours, ugly variables --  will deal with later*
    private final String GREEN = "#5ee340";
    private final String TGREEN = "#445ee340";
    private final String YELLOW = "#f0d531";
    private final String TYELLOW = "#44f0d531";
    private final String RED = "#ec2424";
    private final String TRED = "#44ec2424";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        status = (TextView)findViewById(R.id.traffic);

        // Add a marker in Honolulu and move the camera
        LatLng hnl = new LatLng(21.296596, -157.821586);
        mMap.addMarker(new MarkerOptions().position(hnl).title("Marker"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hnl, 14));

        //Creates radius for current location -- currently invisible
        circle = mMap.addCircle(new CircleOptions()
            .center(hnl)
            .radius(1300)
            .strokeColor(Color.TRANSPARENT)
            .fillColor(Color.TRANSPARENT)
        );

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false); //hides square button located top right
        } else {
            // Show rationale and request permission.
        }

        //Depending on traffic, set appropriate low/med/high announcement
        Random random = new Random(); //currently randoms traffic rate
        int num = random.nextInt(100);
        if(num < 33){
            status.setText("Low Traffic");
            status.setBackgroundColor(Color.parseColor(GREEN));
            circle.setFillColor(Color.parseColor(TGREEN));
        }
        else if(num < 66){
            status.setText("Medium Traffic");
            status.setBackgroundColor(Color.parseColor(YELLOW));
            circle.setFillColor(Color.parseColor(TYELLOW));
        }
        else {
            status.setText("High Traffic");
            status.setBackgroundColor(Color.parseColor(RED));
            circle.setFillColor(Color.parseColor(TRED));
        }
    }
}
