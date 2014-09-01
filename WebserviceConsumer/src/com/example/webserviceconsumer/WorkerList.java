package com.example.webserviceconsumer;

import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

public class WorkerList extends Activity {
	private static final String url = "http://192.168.1.39:8080/kachuelitos2/services/JobOffersList?wsdl";
	private static final String namespace = "http://192.168.1.39:8080/kachuelitos2/";
	private static final String Method_JobOffersList = "JobOffersList";
	private static final String accionSoap_JobOffersList = "http://192.168.1.39:8080/kachuelitos2/services/JobOffersList";

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.workerlist);

		TextView tvrpta = (TextView)findViewById(R.id.tvRpta1);

		Intent i = getIntent();
		String out = "";
		String message = i.getStringExtra("works");
		AsyncTaskImpl thread = new AsyncTaskImpl();
		thread.SetMessage(message);

		thread.execute();
	}
	
	private class AsyncTaskImpl extends AsyncTask<String, String, String> {
		private String out = "";
		private String message = "";
		
		public void SetMessage(String m){
			message = m;
		}
		@Override
		protected String doInBackground(String... params) {
			final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			SoapObject request = new SoapObject(namespace, Method_JobOffersList);
			request.addProperty("worker", message);
			envelope.dotNet = true;
			envelope.bodyOut = request;
			final HttpTransportSE transport = new HttpTransportSE(url);

			System.out.print("vaentrarrrrrrrr");
			System.out.print("vaentrarrrrrrrr 2");
			System.out.print("dsads"+out);
			//tvrpta.setText(out);
			
			try {
				transport.call(accionSoap_JobOffersList, envelope);
				SoapObject Table = (SoapObject) envelope.bodyIn;

				String[][] output = null;

				if (Table != null) {
					SoapObject row = (SoapObject) Table.getProperty(0);

					if (row != null) {

						int rCount = Table.getPropertyCount();
						int cCount = ((SoapObject) Table.getProperty(0)).getPropertyCount();
						output = new String[rCount][cCount];
						for (int j = 0; j < rCount; j++) {

							for (int k = 0; k < cCount; k++) {
								output[j][k] = ((SoapObject) Table.getProperty(j))
										.getProperty(k).toString();

							}
							out += " - " + output[j][1] + " ";
							out += "\n";
							
						}
					}
				}
			
				
				//SoapPrimitive resultado = (SoapPrimitive) envelope.getResponse();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return out;
			}

		}
	
}
		


