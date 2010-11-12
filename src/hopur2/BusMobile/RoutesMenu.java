package hopur2.BusMobile;

import java.io.IOException;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public final class RoutesMenu extends Activity {
private ListView lv;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.routesmenu);
       
       
        
        DataBaseHelper dbh = new DataBaseHelper(this);
       
        try{
        	//if(!dbh.checkDatabase()){
        		dbh.getReadableDatabase();
        		dbh.copyDatabase();
        	//}
        }catch(IOException IOe){} 
        
       
        
        //dbh.openDatabase();
        
        String myPath = this.getDatabasePath("BusMobile").getPath();
        SQLiteDatabase myDataBase = SQLiteDatabase.openDatabase(myPath, null,SQLiteDatabase.OPEN_READONLY); 
        
        
        
        Cursor c = myDataBase.rawQuery("select number, name, _id from Route;", null);
        c.moveToFirst();
	    
		String[] LeidirNofn = new String[c.getCount()];
		final int[] LeidirID = new int[c.getCount()];
		int n=0;
		
	    while (!c.isAfterLast())
	    {
	    	String q = "Leið "+c.getString(0)+"\n"+" "+c.getString(1);
	    	LeidirNofn[n]= q;
	    	LeidirID[n]=c.getInt(2);
	    	c.moveToNext();
	    	n++;
	    };
	    
	    c.close();
	    myDataBase.close();
	    dbh.close();
        
        lv =(ListView)findViewById(R.id.ListView01);
        
        ArrayAdapter<String> NofnAdapter = new ArrayAdapter<String>(this,R.layout.list_item, LeidirNofn);
        lv.setAdapter(NofnAdapter);
       
		lv.setOnItemClickListener(new OnItemClickListener(){
			public void onItemClick(AdapterView<?> a, View v, int position, long id) 
			{
				
				int RouteID = LeidirID[position];
				getLeidarTafla(RouteID);
				
				/* AlertDialog.Builder adb = new AlertDialog.Builder(BusMobile.this);
        		adb.setTitle("Leidarkerfi");
        		adb.setMessage("Selected Item is = "+lv.getItemAtPosition(position));
        		adb.setPositiveButton("Ok", null);
        		adb.show(); */
        	}
        });
		
		}
	public void getLeidarTafla(int RouteID) {
		Intent intent = new Intent (this, hopur2.BusMobile.LeidarTafla.class);
		try {this.startActivity(intent);}
		catch(ActivityNotFoundException e){e.toString();}
		hopur2.BusMobile.LeidarTafla.RouteID = RouteID;
	}
}


