package hopur2.BusMobile;

import java.io.IOException;

import android.app.Activity;
import android.app.TabActivity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.AdapterView.OnItemClickListener;

public class RoutesMenu extends TabActivity {
	private ListView lv;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.routelayout);
		TabHost tabHost = getTabHost();  // The activity TabHost
		TabHost.TabSpec spec;

		Intent redIntent = new Intent().setClass(this, ColoredRoutes.class);
		redIntent.putExtra("hopur2.BusMobile.ColoredRoutes.COLOR",1);
		spec = tabHost.newTabSpec("red").setIndicator("Rauðar").setContent(redIntent);
		tabHost.addTab(spec);

		Intent greenIntent = new Intent().setClass(this, ColoredRoutes.class);
		greenIntent.putExtra("hopur2.BusMobile.ColoredRoutes.COLOR",2);
		spec = tabHost.newTabSpec("green").setIndicator("Grænar").setContent(greenIntent);
		tabHost.addTab(spec);

		Intent blueIntent = new Intent().setClass(this, ColoredRoutes.class);
		blueIntent.putExtra("hopur2.BusMobile.ColoredRoutes.COLOR",3);
		spec = tabHost.newTabSpec("blue").setIndicator("Bláar").setContent(blueIntent);
		tabHost.addTab(spec);

	}
}


