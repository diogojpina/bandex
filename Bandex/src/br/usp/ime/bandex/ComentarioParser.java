package br.usp.ime.bandex;

import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.util.Log;

public class ComentarioParser {
	
	public List<Comentario> getComentarios(int id) {
		List<Comentario> comentarios = new ArrayList<Comentario>();
		String cardapio_xml = null;
		
		cardapio_xml = this.getXmlFromUrl("http://uspservices.deusanyjunior.dj/cardapio/" + id + ".xml");
		try {
			Document doc = this.getDomElement(cardapio_xml);
			NodeList nodeList;
			Element e;
			
			
			
			nodeList = doc.getElementsByTagName("comment");
			for (int i=0; i < nodeList.getLength(); i++) {
				e = (Element) nodeList.item(i);
				
				Comentario comentario = new Comentario();
				comentario.setId(Integer.parseInt(this.getValue(e, "id")));
				comentario.setCommenter(this.getValue(e, "commenter"));
				comentario.setMessage(this.getValue(e, "message"));
				
				comentarios.add(comentario);
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return comentarios;
	}
	
	
	private String getXmlFromUrl(String url) {
		String xml = null;

		try {
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(url);

			HttpResponse httpResponse = httpClient.execute(httpGet);
			HttpEntity httpEntity = httpResponse.getEntity();
			xml = EntityUtils.toString(httpEntity);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return xml;
	}
	
	private Document getDomElement(String xml) {
		Document doc = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {

			DocumentBuilder db = dbf.newDocumentBuilder();

			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xml));
			doc = (Document) db.parse(is);

		} catch (ParserConfigurationException e) {
			Log.e("Error: ", e.getMessage());
			return null;
		} catch (SAXException e) {
			Log.e("Error: ", e.getMessage());
			return null;
		} catch (IOException e) {
			Log.e("Error: ", e.getMessage());
			return null;
		}

		return doc;
	}
	
	private final String getElementValue(Node elem) {
		Node child;
		String ret = "";
		if (elem != null) {
			if (elem.hasChildNodes()) {
				for (child = elem.getFirstChild(); child != null; child = child.getNextSibling()) {
					if (child.getNodeType() == Node.TEXT_NODE || ( child.getNodeType() == Node.CDATA_SECTION_NODE))
						ret += child.getNodeValue();
				}
			}
		}
		return ret;
	}	
	
	private String getValue(Element item, String str) {
		NodeList n = item.getElementsByTagName(str);
		return this.getElementValue(n.item(0));
	}	
	
}


