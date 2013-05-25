package br.usp.ime.bandex;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.TextView;

public class RestauranteActivity extends Activity {
	
	private Restaurante restaurante = new Restaurante();
	private int restauranteId = 0;	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_restaurante);
		
		Intent in = getIntent();
		this.restauranteId = Integer.parseInt(in.getStringExtra("restauranteId"));
		
		RestauranteDatabaseHandler restauranteDB = new RestauranteDatabaseHandler(getApplicationContext());
		restaurante = restauranteDB.getRestaurante(this.restauranteId);
		
		TextView name = (TextView) findViewById(R.id.name);
		TextView adrress = (TextView) findViewById(R.id.address);
		TextView tel = (TextView) findViewById(R.id.tel);
		
		name.setText(restaurante.getName());
		adrress.setText(restaurante.getAddress());
		tel.setText(restaurante.getTel());
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.restaurante, menu);
		return true;
	}

}
