package nl.madspace.trashapp;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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

		Drawable drawable = getResources().getDrawable(R.drawable.vuilniszak);
		BitmapDrawable bd = (BitmapDrawable)drawable.getCurrent();
		Bitmap b = bd.getBitmap();
		Bitmap bhalfsize=Bitmap.createScaledBitmap(b, b.getWidth()/2,b.getHeight()/2, false);


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
						"Klaag over deze afvalbak").icon(BitmapDescriptorFactory.fromBitmap(bhalfsize)));
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

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		super.onActivityResult(arg0, arg1, arg2);
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setTitle("Succes");
		alertDialog.setMessage("Uw klacht en foto is naar de gemeente verstuurd!");

		alertDialog.show();
	}
}