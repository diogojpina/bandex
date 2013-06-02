package br.usp.ime.bandex;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ListActivity {
	
	ArrayList<HashMap<String, String>> menuItemList = new ArrayList<HashMap<String,String>>();
	
	RestauranteParser restauranteParser = new RestauranteParser();
	private Restaurante[] restaurantes = new Restaurante[4];
	private int restauranteId = 0;
	

	private Spinner spinner;
	private ImageButton btnRefresh;
	private Button showMap;
	
	private static final int MOBILE = 2;
    private static final int WIFI = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		refreshRestaurante();
		
		this.spinner = (Spinner) findViewById(R.id.spinner);
		this.btnRefresh = (ImageButton) findViewById(R.id.btnRefresh);
		this.showMap = (Button) findViewById(R.id.showMap);
		
		ListView lv = getListView();
        lv.setOnItemClickListener(new OnItemClickListener() {
        	  
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent in = new Intent(getApplicationContext(), ComentarioActivity.class);
                 
                String sid = ((TextView) view.findViewById(R.id.menu_id)).getText().toString();
                showToast("Comentários sobre a refeição.");
                in.putExtra("menuId", sid);
                startActivity(in);
            }
        });		
        
        
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
				int pos, long id) {
				restauranteId = pos;
				new LoadRestauranteFromDB().execute();
					
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});	
        
        btnRefresh.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				refreshRestaurante();
			}
		});
        
        showMap.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, MapsActivity.class);
				startActivity(i);
			}
		});
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.bInfo:
	        	Restaurante res = restaurantes[restauranteId];
	        	String info = 	res.getName() + "\n\n" +
	        					res.getAddress() + "\n\n" +
	        					res.getTel();
	        					
                Intent in = new Intent(getApplicationContext(), RestauranteActivity.class);
                String sid = Integer.toString(restauranteId);  
                showToast(info);
                in.putExtra("restauranteId", sid);
                startActivity(in);	        	
	        	
	        	/*
                Intent in = new Intent(getApplicationContext(), RestauranteActivity.class);                
                //String sid = ((TextView) view.findViewById(R.id.menu_id)).getText().toString();                
                //in.putExtra("restauranteId", Integer.toString(restauranteId));
                startActivity(in);
                */
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}	
	
	private void refreshRestaurante() {
		switch (isNetworkAvailable()) {
			case MOBILE:
				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
				builder.setTitle("Você está usando 3G");
				builder.setMessage("Deseja atualizar as informações?");
				builder.setPositiveButton("Sim, atualizar com 3G", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						new UpdateRestauranteItems().execute();
					}					
				});
				builder.setNegativeButton("Não, utilizar antigas.", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
						new LoadRestauranteFromDB().execute();
					}
				});		
				
				AlertDialog alert = builder.create();
				alert.show();
				
				break;
			case WIFI:
				new UpdateRestauranteItems().execute();
				break;
			case 0:
				showToast("Sem conexÃ£o com a internet. Carregando as informaÃ§Ãµes salvas.");
				new LoadRestauranteFromDB().execute();
				break;				
		}
	}
	
	
	private int isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    if(activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
	    	if(activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI)
	    		return 1;
	    	if(activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE)
	    		return 2;
	    }
	    return 0;
	}	
	
	private void showToast(String msg) {
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
	}
	
	class UpdateRestauranteItems extends AsyncTask<String, String, String> {
		
		ProgressDialog pDialog;
		
	    @Override
	    protected void onPreExecute() {
	        super.onPreExecute();
	        pDialog = new ProgressDialog(MainActivity.this);
	        pDialog.setMessage("Atualizando as informações...");
	        pDialog.setIndeterminate(false);
	        pDialog.setCancelable(true);
	        pDialog.show();
	    }
	    
	    @Override
	    protected synchronized String doInBackground(String... args) {
	    	RestauranteDatabaseHandler restauranteDB = new RestauranteDatabaseHandler(getApplicationContext());

	    	restaurantes[0] = restauranteParser.getRestaurante(1);
			restaurantes[1] = restauranteParser.getRestaurante(2);
			restaurantes[2] = restauranteParser.getRestaurante(3);
			restaurantes[3] = restauranteParser.getRestaurante(4);
			if (restaurantes[0] == null || restaurantes[1] == null || restaurantes[2] == null || restaurantes[3] == null) {
				return null;
			}
			
						
			restauranteDB.addRestaurante(restaurantes[0]);
			restauranteDB.addRestaurante(restaurantes[1]);
			restauranteDB.addRestaurante(restaurantes[2]);
			restauranteDB.addRestaurante(restaurantes[3]);
			
	    	return null;
	    }
	    
	    protected void onPostExecute(String args) {
	    	pDialog.dismiss();
	    	new LoadRestauranteFromDB().execute();
	    }
		
	}
	
	class LoadRestauranteFromDB extends AsyncTask<String, String, String> {
		ProgressDialog pDialog;
		
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Carregando as informações...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();            
        }
        
        @Override
        protected String doInBackground(String... args) {
        	RestauranteDatabaseHandler restauranteDB = new RestauranteDatabaseHandler(getApplicationContext());

        	Restaurante res = restauranteDB.getRestaurante(restauranteId+1);
        	restaurantes[restauranteId] = res;
        	
        	System.out.println(res.getName());
        	
        	menuItemList.clear();        			
        	for (int i=0; i < res.getMenuList().size(); i++) {
        		br.usp.ime.bandex.Menu menu = res.getMenuList().get(i);
        		
        		HashMap<String, String> map = new HashMap<String, String>();
        		map.put("id", Integer.toString(menu.getId()));
        		map.put("day", menu.getDate());
        		//map.put("day", "12 12 1234");
        		map.put("extra", menu.getPeriodoName() + ": " + menu.getKcal() + "Kcal");
        		map.put("options", menu.getOptions());
        		
        		menuItemList.add(map);        	
        	}
        	return null;
        }
        
        protected void onPostExecute(String args) {
        	pDialog.dismiss();
        	
        	ListAdapter adapter = new SimpleAdapter(
        			MainActivity.this,
        			menuItemList, R.layout.list_row,
        			new String[] {"id", "day", "extra", "options"},
        			new int[] {R.id.menu_id, R.id.day, R.id.extra, R.id.description});
        	setListAdapter(adapter);
        	
        }
	}
}


