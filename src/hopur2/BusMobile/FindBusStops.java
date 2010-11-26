package hopur2.BusMobile;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;



import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.*;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

/* Klasi sem finnur nálægar stanzstöðvar útfrá
 * GPS hnitum notanda.
 * 
 * 
 * 
 */

public class FindBusStops extends Activity {
	
	private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 1; // in Meters
	private static final long MINIMUM_TIME_BETWEEN_UPDATES = 1; // in Milliseconds
	
	private LocationManager locationManager;
	private Location location;
	protected Button retrieveLocationButton;
	private FindBusStopsData busStopsData;
	String [][] busStops;
	double lon1, lon2, gpsLon, gpsLat;
	double lat1, lat2;
	
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.findbusstoplayout);

		/* Vegna þess að emulator-inn er svo ónákvæmur þá 
		 * ákvað ég að hafa statískar breytur til þess að 
		 * herma eftir staðsetningu notanda
		 * 
		 */


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);      
        locationManager.requestLocationUpdates(
        		LocationManager.GPS_PROVIDER, 
        		MINIMUM_TIME_BETWEEN_UPDATES, 
        		MINIMUM_DISTANCE_CHANGE_FOR_UPDATES,
        		new MyLocationListener()
        );
		Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		gpsLon = location.getLongitude();
		gpsLat = location.getLatitude();
		lon1 = gpsLon - 0.008939;
		lon2 = gpsLon + 0.008939;
		lat1 = gpsLat - 0.004505;
		lat2 = gpsLat + 0.004505;
		
		//Þetta er kóðinn sem á að nota á Android símanum
//		lon1 = location.getLongitude() - 0.008939;
//		lon2 = location.getLongitude() + 0.008939;
//		lat1 = location.getLatitude() - 0.004505;
//		lat2 = location.getLatitude() + 0.004505; 
		busStopsData = new FindBusStopsData(this,lon1,lon2,lat1,lat2);
		busStops  = busStopsData.getBusStops();
		
		displayBusStops(busStops);
        
        retrieveLocationButton = (Button) findViewById(R.id.retrieve_location_button);
        retrieveLocationButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				showMap();
				//showCurrentLocation();
			}
		});
		
    }    
    
    public void displayBusStops (String[][] busStops) {
    	TableLayout tblBusStops = (TableLayout)findViewById(R.id.BusStopName);
    	tblBusStops.setBackgroundColor(Color.rgb(255, 250, 250));
    	TableRow trHead = new TableRow(this);
    	trHead.findViewById(R.id.trHead);
    	for (int i=0; i!=busStops.length; i++) {
    		TableRow tr = new TableRow(this);
    		if (i%2==0) {
    			tr.setBackgroundColor(Color.rgb(255, 140, 0));
    		}
    		TextView tv = new TextView(this);
    		tv.setText(busStops[i][0]);
    		tv.setTextColor(Color.rgb(0, 0, 0));
    		tr.addView(tv);
    		
    		
    		double lontmp = Double.parseDouble(busStops[i][2]);
    		double lattmp = Double.parseDouble(busStops[i][3]);
    		int tmp = calculateDistance(lontmp,lattmp);
    		TextView dis = new TextView(this);
    		dis.setText(Integer.toString(tmp));
    		
    		tr.addView(dis);
    		
    		tblBusStops.addView(tr);
    	}
    	
    } //displayBusStops ends
    private int calculateDistance (double lon, double lat) {
    	//Nota Haversine formúluna
    	// R er radíus jaðar = 6371 km
//    	gpsLon = -21.949332;
//		gpsLat = 64.140493;
    	int R = 6371000;
    	double dlon = Math.toRadians(gpsLon - lon);
    	double dlat = Math.toRadians(gpsLat - lat);
    	double a = Math.sin(dlat/2) * Math.sin(dlat/2) + 
    			  (Math.cos(Math.toRadians(lat))) * 
    			  (Math.cos(Math.toRadians(gpsLat))) *
    			   Math.sin(dlon/2) * Math.sin(dlon/2);
    	double c = Math.atan2(Math.sqrt(a),Math.sqrt(1-a));
    	int distance = (int)(R*c);
    	
    	return distance;
    }

	protected void showCurrentLocation() {	
		Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		if (location != null) {
			String message = String.format(
					"Current Location \n Longitude: %1$s \n Latitude: %2$s",
					location.getLongitude(), location.getLatitude()
			);
			Toast.makeText(FindBusStops.this, message,
					Toast.LENGTH_LONG).show();
		}

	} 
	private void showMap(){
		Intent map = new Intent(this,hopur2.BusMobile.MapDisplay.class);
		map.putExtra("hopur2.BusMobile.MapDisplay.SRC_LON",gpsLon);
		map.putExtra("hopur2.BusMobile.MapDisplay.SRC_LAT",gpsLat);
		map.putExtra("hopur2.BusMobile.MapDisplay.ZOOM",15);

		try {
			this.startActivity(map);
		}
		catch(ActivityNotFoundException e){
			e.toString();
		}
	}

	private class MyLocationListener implements LocationListener {

		public void onLocationChanged(Location location) {
			String message = String.format(
					"New Location \n Longitude: %1$s \n Latitude: %2$s",
					location.getLongitude(), location.getLatitude()
			);
			Toast.makeText(FindBusStops.this, message, Toast.LENGTH_LONG).show();
		}

		public void onStatusChanged(String s, int i, Bundle b) {
			Toast.makeText(FindBusStops.this, "Provider status changed",
					Toast.LENGTH_LONG).show();
		}

		public void onProviderDisabled(String s) {
			Toast.makeText(FindBusStops.this,
					"Provider disabled by the user. GPS turned off",
					Toast.LENGTH_LONG).show();
		}

		public void onProviderEnabled(String s) {
			Toast.makeText(FindBusStops.this,
					"Provider enabled by the user. GPS turned on",
					Toast.LENGTH_LONG).show();
		}

	}
    
}