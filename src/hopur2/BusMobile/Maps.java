package hopur2.BusMobile;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class Maps extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Intent myIntent=null;
        try {
        	myIntent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=http://notendur.hi.is/~thm4/kml/Leid15.kml&ie=UTF8&t=h&z=11"));
        } finally{
        	startActivity(myIntent);
        }
        
    }

}