package br.usp.ime.bandex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class ComentarioActivity extends ListActivity {
	
	ArrayList<HashMap<String, String>> menuItemList = new ArrayList<HashMap<String,String>>();	
	
	private List<Comentario> comentarios;
	private int menuId;
	

	private static final int MOBILE = 2;
    private static final int WIFI = 1;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comentario);
		
		Intent in = getIntent();
		this.menuId = Integer.parseInt(in.getStringExtra("menuId"));
		
		refreshComentarios();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.comentario, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.btnComentario:
	        	Intent in = new Intent(getApplicationContext(), ComentarioSendActivity.class);
                in.putExtra("menuId", Integer.toString(this.menuId));
                startActivityForResult(in, 1);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}	
	
	
	private void refreshComentarios() {
		switch (isNetworkAvailable()) {
			case MOBILE:
				AlertDialog.Builder builder = new AlertDialog.Builder(ComentarioActivity.this);
				builder.setTitle("Você está usando 3G");
				builder.setMessage("Deseja atualizar os comentários?");
				builder.setPositiveButton("Sim, atualizar com 3G", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						new UpdateComentarios().execute();
					}					
				});
				builder.setNegativeButton("Não.", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
						new LoadComentarioFromDB().execute();
					}
				});		
				
				AlertDialog alert = builder.create();
				alert.show();
				
				break;
			case WIFI:
				new UpdateComentarios().execute();
				break;
			case 0:
				showToast("Sem conexão com a internet. Carregando as informações salvas.");
				new LoadComentarioFromDB().execute();
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
	
	class UpdateComentarios extends AsyncTask<String, String, String> {
		
		ProgressDialog pDialog;
		
	    @Override
	    protected void onPreExecute() {
	        super.onPreExecute();
	        pDialog = new ProgressDialog(ComentarioActivity.this);
	        pDialog.setMessage("Atualizando as informações...");
	        pDialog.setIndeterminate(false);
	        pDialog.setCancelable(true);
	        pDialog.show();
	    }
	    
	    @Override
	    protected synchronized String doInBackground(String... args) {
	    	ComentarioParser parser = new ComentarioParser();
	    	RestauranteDatabaseHandler restauranteDB = new RestauranteDatabaseHandler(getApplicationContext());
	    	
	    	comentarios = parser.getComentarios(menuId);
	    	for (int i=0; i < comentarios.size(); i++) {
        		Comentario comentario = comentarios.get(i);
        		restauranteDB.addComment(comentario, menuId);
	    	}
			
	    	return null;
	    }
	    
	    protected void onPostExecute(String args) {
	    	pDialog.dismiss();
	    	new LoadComentarioFromDB().execute();
	    }
		
	}
	
	class LoadComentarioFromDB extends AsyncTask<String, String, String> {
		ProgressDialog pDialog;
		
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ComentarioActivity.this);
            pDialog.setMessage("Carregando os comentários...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();            
        }
        
        @Override
        protected String doInBackground(String... args) {
        	RestauranteDatabaseHandler restauranteDB = new RestauranteDatabaseHandler(getApplicationContext());
        	
        	comentarios = restauranteDB.listComentarios(menuId);
        	
        	if (comentarios == null) 
        		comentarios = new ArrayList<Comentario>();
        	
        	System.out.println(comentarios.size());
        	
        	menuItemList.clear();        			
        	for (int i=0; i < comentarios.size(); i++) {
        		Comentario comentario = comentarios.get(i);
        		
        		HashMap<String, String> map = new HashMap<String, String>();
        		map.put("id", Integer.toString(comentario.getId()));
        		map.put("commenter", comentario.getCommenter());
        		map.put("message", comentario.getMessage());
        		
        		menuItemList.add(map);        	
        	}
        	return null;
        }
        
        protected void onPostExecute(String args) {
        	pDialog.dismiss();
        	
        	ListAdapter adapter = new SimpleAdapter(
        			ComentarioActivity.this,
        			menuItemList, R.layout.row_comentario,
        			new String[] {"id", "commenter", "message"},
        			new int[] {R.id.comentario_id, R.id.commenter, R.id.message});
        	setListAdapter(adapter);
        	
        }
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == RESULT_OK) {
			refreshComentarios();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}	
	

}
