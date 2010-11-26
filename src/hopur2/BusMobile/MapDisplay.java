package hopur2.BusMobile;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;


public class MapDisplay extends MapActivity {
	
	public static RouteOverlay routeOverlay;
	public static PointsOverlay pointsOverlay;
	public static GeoPoint srcGeoPoint;
	private MyLocationOverlay location;
	public static int zoom = 12;

	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.maps);
		MapView mpvw = (MapView) findViewById(R.id.myMapView1);
		
		location = new MyLocationOverlay(this,mpvw);
		location.enableMyLocation();
		
		pointsOverlay.setContext(this);
	    
		mpvw.setBuiltInZoomControls(true);
	    mpvw.getOverlays().add(routeOverlay);
	    mpvw.getOverlays().add(pointsOverlay);
	    mpvw.getOverlays().add(location);
	    mpvw.getController().animateTo(srcGeoPoint);
	    mpvw.getController().setZoom(zoom);
	}
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	protected void onResume(){
		location.enableMyLocation();
		super.onResume();
	}
	
	protected void onPause(){
		location.disableMyLocation();
		super.onPause();
	}
	
}


