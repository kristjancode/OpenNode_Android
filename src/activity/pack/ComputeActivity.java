package activity.pack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class ComputeActivity extends Activity {

    @Override
    public void onCreate(final Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.compute);

    }
    
    public boolean onCreateOptionsMenu(Menu menu2){ 

    	MenuInflater inflater = getMenuInflater(); 

    	inflater.inflate(R.menu.actionbar, menu2); 

    	return true;

    	}
    
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionbar_item_home:     
                Intent intent = new Intent(this, OpenActivity.class);
                this.startActivity(intent);
                                break;
            case R.id.actionbar_item_create:     Toast.makeText(this, "You pressed the text!", Toast.LENGTH_LONG).show();
                                break;
            case R.id.actionbar_item_search: Toast.makeText(this, "You pressed the icon and text!", Toast.LENGTH_LONG).show();
                                break;
        }
        return true;
    }


}