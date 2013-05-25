package br.usp.ime.bandex;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ComentarioSendActivity extends Activity {
	private int menuId;
	
	
	private EditText username;
	private EditText comentario;
	private Button btnEnviar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comentario_send);
		
				Intent in = getIntent();
		this.menuId = Integer.parseInt(in.getStringExtra("menuId"));
		
		
		this.username = (EditText) findViewById(R.id.username);
		this.comentario = (EditText) findViewById(R.id.comentario);
		this.btnEnviar = (Button) findViewById(R.id.btnEnviar);
		
		 btnEnviar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				HttpClient httpClient = new DefaultHttpClient();
				JSONObject dados = new JSONObject();
				
				
				try {
			        HttpPost request = new HttpPost("http://uspservices.deusanyjunior.dj/comentariosrefeicao");
			        //StringEntity params =new StringEntity("details={\"name\":\"myname\",\"age\":\"20\"} ");
			        request.addHeader("content-type", "application/json");			        

			        //request.setEntity(params);			        
			        
					dados.put("menu_id", menuId);
					dados.put("commenter", username.getText());
					dados.put("message", comentario.getText());
			        request.setEntity((HttpEntity) dados);
			        
			        HttpResponse response = httpClient.execute(request);

			    }catch (Exception ex) {
			        ex.printStackTrace();
			    } finally {
			        httpClient.getConnectionManager().shutdown();
			    }				
								

				Intent in = new Intent(getApplicationContext(), ComentarioActivity.class);
				showToast("Comentários sobre a refeição.");
                in.putExtra("menuId", Integer.toString(menuId));
                startActivity(in);
			}
		});	
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.comentario_send, menu);
		return true;
	}
	
	private void showToast(String msg) {
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
	}

}
