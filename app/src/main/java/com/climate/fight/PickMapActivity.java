package com.climate.fight;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.climate.fight.R;

import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.Marker;

public class PickMapActivity extends AppCompatActivity {

    private double lat = 0.0, longi = 0.0;
    Marker prmk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_map);
        MapView map = findViewById(R.id.map_pick);
        findViewById(R.id.map_pick_ok).setOnClickListener(view -> {
            Intent i = new Intent().putExtra("lat", lat).putExtra("lon", longi);
            setResult(117, i);
        });
        final MapEventsReceiver mReceive = new MapEventsReceiver(){
            @Override
            public boolean singleTapConfirmedHelper(GeoPoint p) {
                lat = p.getLatitude();
                longi = p.getLongitude();
                if(prmk != null) map.getOverlays().remove(prmk);
                Marker evMarker = new Marker(map);
                evMarker.setPosition(p);
                evMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                evMarker.setIcon(ContextCompat.getDrawable(PickMapActivity.this, R.drawable.pin_place));
                map.getOverlays().add(evMarker);
                prmk = evMarker;
                Toast.makeText(getBaseContext(),lat + " - " + longi, Toast.LENGTH_LONG).show();
                return false;
            }
            @Override
            public boolean longPressHelper(GeoPoint p) {
                return false;
            }
        };
        map.getOverlays().add(new MapEventsOverlay(mReceive));
    }
}
