package br.usp.ime.bandex;


import android.os.Bundle;
import android.app.ListActivity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

public class MainActivity extends ListActivity {
	
	private Restaurante[] restaurantes;
	private int restauranteId;
	private Spinner spinner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		this.restaurantes = new Restaurante[4];
		this.restauranteId = 0;
		RestauranteParser restauranteParser = new RestauranteParser();
		restaurantes[0] = restauranteParser.getRestaurante(1);
		restaurantes[1] = restauranteParser.getRestaurante(2);
		restaurantes[2] = restauranteParser.getRestaurante(3);
		restaurantes[3] = restauranteParser.getRestaurante(4);
		
		this.spinner = (Spinner) findViewById(R.id.spinner);
		
		ListView lv = getListView();
        lv.setOnItemClickListener(new OnItemClickListener() {
        	  
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Intent in = new Intent(getApplicationContext(), DisplayRssFeedActivity.class);
                 
                //String sid = ((TextView) view.findViewById(R.id.rss_id)).getText().toString();
                //showToast("Carregando o id: " + sid);
                //in.putExtra("rss_id", sid);
                //startActivity(in);
            }
        });		
		
		
		
		
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				System.out.println(restaurantes[pos].getName());

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
