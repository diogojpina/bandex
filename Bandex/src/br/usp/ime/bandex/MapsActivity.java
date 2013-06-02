package br.usp.ime.bandex;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public class MapsActivity extends FragmentActivity {

	private static final LatLng USP_CENTER = new LatLng(-23.560289,-46.727471);
	private GoogleMap googleMap;
	private SupportMapFragment fm;
	private BandejoesUSP bandex;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		bandex = new BandejoesUSP();
		
		int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());
		
		if(status != ConnectionResult.SUCCESS) {
			int requestCode = 10;
			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
			dialog.show();
		}
		else {
			fm = new SupportMapFragment() {
				@Override
				public void onActivityCreated(Bundle b) {
					super.onActivityCreated(b);

					initializeMap();
					showBandejoes();
				}
			};
			getSupportFragmentManager().beginTransaction().add(android.R.id.content, fm).commit();
		}
	}
	
	protected void showBandejoes() {
		bandex.showAllBandexOnMap(googleMap);
	}

	protected void initializeMap() {
		googleMap = fm.getMap();
		googleMap.moveCamera(CameraUpdateFactory.newLatLng(USP_CENTER));
		googleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
		googleMap.setOnMarkerClickListener(new OnMarkerClickListener() {
			@Override
			public boolean onMarkerClick(Marker marker) {
				marker.showInfoWindow();
				return false;
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.maps, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.showMe:
	        	googleMap.setMyLocationEnabled(!googleMap.isMyLocationEnabled());
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
}