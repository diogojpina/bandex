package br.usp.ime.bandex;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;

public class CardapioParser {
	
	public void getCardapio(String url) {
		String cardapio_xml = null;
		
		cardapio_xml = this.getXmlFromUrl(url);
		System.out.println(url);
		try {
			//Document doc = this.getDomElement(cardapio_xml);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	private String getXmlFromUrl(String url) {
		String xml = null;

		try {
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(url);

			HttpResponse httpResponse = httpClient.execute(httpGet);
			//HttpEntity httpEntity = httpResponse.getEntity();
			//xml = EntityUtils.toString(httpEntity);
		} 
		catch (Exception e) {
			System.out.println("aqui");
			e.printStackTrace();
		}
		return xml;
	}	

}
