package nl.madspace.trashapp;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

public class MainActivity extends FragmentActivity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		SupportMapFragment mapFragment = (SupportMapFragment) this
				.getSupportFragmentManager().findFragmentById(R.id.map);
		GoogleMap map = mapFragment.getMap();

		map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(51.4424005, 5.4799979), 12));
	}
}