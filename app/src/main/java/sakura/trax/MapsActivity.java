package sakura.trax;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Random;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private TextView status;

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
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        status = (TextView) findViewById(R.id.traffic);

        // Add a marker in Honolulu and move the camera
        LatLng hnl = new LatLng(21.306944, -157.858333);
        mMap.addMarker(new MarkerOptions().position(hnl).title("Marker"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hnl, 14));

        //Depending on traffic, set appropriate low/med/high announcment
        Random random = new Random(); //currently randoms traffic rate
        int num = random.nextInt(100);
        if(num < 33){
            status.setText("Low Traffic");
            status.setBackgroundColor(Color.parseColor("#5ee340"));
        }
        else if(num < 66){
            status.setText("Medium Traffic");
            status.setBackgroundColor(Color.parseColor("#f0d531"));
        }
        else {
            status.setText("High Traffic");
            status.setBackgroundColor(Color.parseColor("#ec2424"));
        }
    }
}
