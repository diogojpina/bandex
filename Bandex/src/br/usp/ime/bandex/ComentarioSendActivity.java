package br.usp.ime.bandex;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
				new SendComment().execute();
			}
		});
	}
	
	private void showToast(String msg) {
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
	}

	class SendComment extends AsyncTask<String, String, String> {
		ProgressDialog pDialog;
		
	    @Override
	    protected void onPreExecute() {
	        super.onPreExecute();
	        pDialog = new ProgressDialog(ComentarioSendActivity.this);
	        pDialog.setMessage("Enviando comentário");
	        pDialog.setIndeterminate(false);
	        pDialog.setCancelable(true);
	        pDialog.show();
	    }
	    
	    @Override
	    protected synchronized String doInBackground(String... args) {
	    	HttpClient httpClient = new DefaultHttpClient();
			
			try {
		        HttpPost request = new HttpPost("http://uspservices.deusanyjunior.dj/comentariosrefeicao");
		        request.addHeader("content-type", "application/json");			        

		        JSONObject dados = new JSONObject();
		        JSONObject nome = new JSONObject();
				
		        dados.put("message", comentario.getText());
				dados.put("commenter", username.getText());
				dados.put("menu_id", menuId);
				nome.put("menuscomment", dados);
		        request.setEntity(new StringEntity(nome.toString()));
		        
		        httpClient.execute(request);

		    }catch (Exception ex) {
		        ex.printStackTrace();
		        return "erro";
		    } finally {
		        httpClient.getConnectionManager().shutdown();
		    }
	    	return "sucesso";
	    }
	    
	    protected void onPostExecute(String result) {
	    	pDialog.dismiss();
	    	if(result.contains("erro"))
	    		showToast("Erro ao enviar a mensagem");
	    	else
	    		showToast("Mensagem enviada com sucesso");
	    	setResult(RESULT_OK);
            finish();
	    }
		
	}

}
