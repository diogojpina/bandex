package br.usp.ime.bandex;

import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class BandejoesUSP {

	private BandejaoInfo[] bandejao_usp = null; 
	private List<Marker> markerList;
	
	public BandejoesUSP() {
		fillBandejoesUsp();
	}
	
	private void fillBandejoesUsp() {
		int icon = R.drawable.ic_imeusp; // icon bandex
		if(bandejao_usp == null) {
			bandejao_usp = new BandejaoInfo[4];
			bandejao_usp[0] = new BandejaoInfo(-23.565128, -46.712408, "bandex 1", "horarios: ", icon);
			bandejao_usp[1] = new BandejaoInfo(-23.551064, -46.732149, "bandex 2", "horarios: ", icon);
			bandejao_usp[2] = new BandejaoInfo(-23.569258, -46.741118, "bandex 3", "horarios: ", icon);
			bandejao_usp[3] = new BandejaoInfo(-23.569258, -46.741118, "bandex 4", "horarios: ", icon);
			
			markerList = new ArrayList<Marker>();
		}
	}

	public void showAllBandexOnMap(GoogleMap googleMap) {
		cleanBandejoes();
		for (BandejaoInfo bandex : bandejao_usp) {
			Marker marker = addBandejaoToMaps(bandex, googleMap);
			markerList.add(marker);
		}
	}

	private Marker addBandejaoToMaps(BandejaoInfo i, GoogleMap googleMap) {
		MarkerOptions marker = new MarkerOptions();
		marker.position(new LatLng(i.getLatitude(), i.getLongitude())).title(i.getTitle()).snippet(i.getSchedule()).icon(BitmapDescriptorFactory.fromResource(i.getIcon()));
		return googleMap.addMarker(marker);
	}
	
	public void cleanBandejoes() {
		if(markerList != null && !markerList.isEmpty()) {
			for (Marker marker : markerList) {
				marker.remove();
			}
			markerList.clear();
		}
	}
}
