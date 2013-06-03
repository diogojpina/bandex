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
			bandejao_usp[0] = new BandejaoInfo(-23.559739, -46.721552, "Restaurante Central", "Praça do Relógio Solar, travessa 8, nº 300, Cidade Universitária", icon);
			bandejao_usp[1] = new BandejaoInfo(-23.560565, -46.735457, "Restaurante da Física", "Rua do Matão, Travessa R - Instituto de Física - Cidade Universitária", icon);
			bandejao_usp[2] = new BandejaoInfo(-23.563731, -46.725713, "Restaurante da Química", "Av. Lineu Prestes, 748 - Instituto de Químicas - Cidade Universitária", icon);
			bandejao_usp[3] = new BandejaoInfo(-23.558935, -46.740676, "Restaurante da PUSP-C", "Av. Prof. Almeida Prado, 1280 - Cidade Universitária", icon);
			
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
