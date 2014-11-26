package com.example.teste;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.example.teste.dao.Endereco;
import com.example.teste.dao.OperacoesDAO;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends FragmentActivity {

	private GoogleMap mMap;
	ArrayList<Endereco> ends = new ArrayList<Endereco>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//Verificando conexão com a internet
		ConnectivityManager conect = (ConnectivityManager) getSystemService(MainActivity.CONNECTIVITY_SERVICE);
		if (conect.getActiveNetworkInfo() == null
				|| !conect.getActiveNetworkInfo().isConnectedOrConnecting()) {
			
			AlertDialog dialog = new AlertDialog.Builder(this).create();
			dialog.setIcon(android.R.drawable.ic_dialog_alert);
			dialog.setTitle("Erro de Conexão!");
			dialog.setMessage("Certifique-se que o aparelho está conectado a internet e retorne ao aplicativo.");
			dialog.onBackPressed();
			dialog.setOnCancelListener(new DialogInterface.OnCancelListener()
			{
			    public void onCancel(DialogInterface dialog)
			    {
			         MainActivity.this.finish();
			    }
			});
			dialog.show();
		}
		
		try {
			MapsInitializer.initialize(this);
		} catch (GooglePlayServicesNotAvailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		setUpMapIfNeeded();
		
		OperacoesDAO bd = new OperacoesDAO(this);
		ends = bd.retornaCEPs();
		if(ends!=null){
		for (int i = 0; i < ends.size(); i++) {
			String cep = ends.get(i).getCep();
			if (!cep.equals("0")) {
				LatLng fromPosition = new LatLng(Double.parseDouble(ends.get(i).getLatitude()), Double.parseDouble(ends.get(i).getLongitude()));

				Marker kiel = mMap
						.addMarker(new MarkerOptions()
								.position(fromPosition)
								.title(ends.get(i).getCep())
								.snippet(ends.get(i).getRua()+", "+ends.get(i).getNumero())
								.icon(BitmapDescriptorFactory
										.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
				;
			}
		}
		}
		
		
	}
	
	private void setUpMapIfNeeded() {
	    // Do a null check to confirm that we have not already instantiated the map.
	    if (mMap == null) {
	        mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
	        // Check if we were successful in obtaining the map.
	        if (mMap != null) {
	        	mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
	           mMap.getUiSettings().setAllGesturesEnabled(true);
	           mMap.getUiSettings().setMyLocationButtonEnabled(true);
	           mMap.setMyLocationEnabled(true);
	           

	        }
	    }
	}
	
	@Override
	protected void onResume() {
	    super.onResume();
	    setUpMapIfNeeded();
	}
	
//	public double[] buscarCoordenadasEndereco(String endereco, int i) {
//		Geocoder geoCoder = new Geocoder(this, Locale.US);
//		List<Address> addresses = null;
//		double[] geo = {0,0};
//		try {
//			while(geo[0]==0){
//				addresses = geoCoder.getFromLocationName(endereco, 1);
//				if (addresses == null) {
//					Toast.makeText(this, "Local não encontrado!",
//							Toast.LENGTH_SHORT).show();
//					return null;
//				}
//				double lat = addresses.get(0).getLatitude();
//				double lon = addresses.get(0).getLongitude();
//				geo[0] = lat;
//				geo[1] = lon;
//			}
//			
//			addresses = geoCoder.getFromLocation(geo[0], geo[1], 1);
//			ends.get(i).setEndereco(addresses.get(0).getThoroughfare());
//			ends.get(i).setBairro(addresses.get(0).getSubLocality());
//			ends.get(i).setCidade(addresses.get(0).getLocality());
//			ends.get(i).setEstado(addresses.get(0).getAdminArea());
//			ends.get(i).setPais(addresses.get(0).getCountryName());
//			
//		} catch (IOException e) {
//			System.err.println(e.toString());
//		}// o numero um aqui é a quantidade maxima de resultados
//		return geo;
//	}

	
}
