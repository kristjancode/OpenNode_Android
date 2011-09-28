package suvaline.pack;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class OpenActivity extends Activity {
	public OpenActivity activity;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
    }
        
    public void btnSearchClicked(View view) {
    	setContentView(R.layout.search);
    }


    
    public boolean onCreateOptionsMenu(Menu menu2){ 

    	MenuInflater inflater = getMenuInflater(); 

    	inflater.inflate(R.menu.actionbar, menu2); 

    	return true;

    	}
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionbar_item_home:     setContentView(R.layout.main);
                                break;
            case R.id.actionbar_item_create:     Toast.makeText(this, "You pressed the text!", Toast.LENGTH_LONG).show();
                                break;
            case R.id.actionbar_item_search: Toast.makeText(this, "You pressed the icon and text!", Toast.LENGTH_LONG).show();
                                break;
        }
        return true;
    }
}