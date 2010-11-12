package hopur2.BusMobile;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;


public class MapMenu extends Activity {

	ImageButton kort2;
	@Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	this.setContentView(R.layout.maps);
	
	ImageButton kort1 =(ImageButton)findViewById(R.id.kort1);
	OnClickListener kort1Listener = new OnClickListener(){
    	public void onClick(View V)
    	{
    		Intent myIntent=null;
        	String uri = "geo:0,0?q=http://notendur.hi.is/~thm4/kml/Leid1.kml&ie=UTF8&t=h&z=11";
            try {
            	myIntent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
            } finally{
            	startActivity(myIntent);
            }    
    	}
    };
    kort1.setOnClickListener(kort1Listener);
    
    ImageButton kort2 =(ImageButton)findViewById(R.id.kort2);
    OnClickListener kort2listener = new OnClickListener(){
    	public void onClick(View V)
    	{
    		Intent myIntent=null;
        	String uri = "geo:0,0?q=http://notendur.hi.is/~thm4/kml/Leid2.kml&ie=UTF8&t=h&z=11";
            try {
            	myIntent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
            } finally{
            	startActivity(myIntent);
            }    
    	}
    };
    kort2.setOnClickListener(kort2listener);
    
    ImageButton kort3 =(ImageButton)findViewById(R.id.kort3);
    OnClickListener kort3listener = new OnClickListener(){
    	public void onClick(View V)
    	{
    		Intent myIntent=null;
        	String uri = "geo:0,0?q=http://notendur.hi.is/~thm4/kml/Leid3.kml&ie=UTF8&t=h&z=11";
            try {
            	myIntent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
            } finally{
            	startActivity(myIntent);
            }    
    	}
    };
    kort3.setOnClickListener(kort3listener);
	}
	
	}


