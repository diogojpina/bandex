package br.usp.ime.bandex;

import com.google.android.maps.MapActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.TextView;

public class RestauranteActivity extends MapActivity {
	private Restaurante restaurante = new Restaurante();
	private int restauranteId = 0;	

	private GoogleMap googleMap;
	private SupportMapFragment fm;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_restaurante);
		
		Intent in = getIntent();
		this.restauranteId = Integer.parseInt(in.getStringExtra("restauranteId"));
		
		RestauranteDatabaseHandler restauranteDB = new RestauranteDatabaseHandler(getApplicationContext());
		restaurante = restauranteDB.getRestaurante(this.restauranteId);
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.restaurante, menu);
		return true;
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

}
