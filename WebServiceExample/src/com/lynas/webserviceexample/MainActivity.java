package com.lynas.webserviceexample;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



public class MainActivity extends Activity {
	TextView tvMyMessage;
	Button bGoForIt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tvMyMessage = (TextView) findViewById(R.id.textView2);
		bGoForIt = (Button) findViewById(R.id.button1);
		
		bGoForIt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new loadSomeStuff().execute("88");
				
			}
		});
		
		
		
		
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	private class loadSomeStuff extends AsyncTask<String, Integer, String>{
		//private static final String SOAP_ACTION1 = "http://tempuri.org/FahrenheitToCelsius";
		private static final String SOAP_ACTION = "http://www.w3schools.com/webservices/CelsiusToFahrenheit";
		private static final String METHOD_NAME = "CelsiusToFahrenheit";
		private static final String NAMESPACE = "http://www.w3schools.com/webservices/";
		private static final String URL = "http://www.w3schools.com/webservices/tempconvert.asmx";
		
		
		@Override
		protected String doInBackground(String... arg) {
			
			String finalResult="initial";
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
			//Property which holds input parameters
			PropertyInfo celsiusPI = new PropertyInfo();
			//Set Name
			celsiusPI.setName("Celsius");
			//Set Value
			celsiusPI.setValue("32");
			//Set dataType
			celsiusPI.setType(double.class);
			//Add the property to request object
			request.addProperty(celsiusPI);
			//Create envelope
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			envelope.dotNet = true;
			//Set output SOAP object
			envelope.setOutputSoapObject(request);
			//Create HTTP call object
			HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

			try {
				//Invole web service
				androidHttpTransport.call(SOAP_ACTION, envelope);
				//Get the response
				SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
				//Assign it to fahren static variable
				finalResult = response.toString();

			} catch (Exception e) {
				finalResult = "error "+ e.getMessage();
			}
			
			
			
			return finalResult;
		}
		
		@Override
		protected void onPostExecute(String result){
			tvMyMessage.setText("web res: "+result);
		}
	}
}
