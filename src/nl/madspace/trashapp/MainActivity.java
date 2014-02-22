package nl.madspace.trashapp;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends FragmentActivity implements
OnInfoWindowClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		SupportMapFragment mapFragment = (SupportMapFragment) this
				.getSupportFragmentManager().findFragmentById(R.id.map);
		GoogleMap map = mapFragment.getMap();

		map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(51.4424005,
				5.4799979), 12));
		// map.setOnMarkerClickListener(this);
		map.setOnInfoWindowClickListener(this);
		InputStream is = getResources().openRawResource(R.raw.trashcans);
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		try {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] rowdata = line.split(",");
				double lat = Double.parseDouble(rowdata[0]);
				double lng = Double.parseDouble(rowdata[1]);
				map.addMarker(new MarkerOptions()
				.position(new LatLng(lat, lng)).title(
						"Klaag over deze afvalbak").icon(BitmapDescriptorFactory.fromResource(R.drawable.vuilniszak)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onInfoWindowClick(Marker marker) {
		Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(takePicture, 0);
	}
}