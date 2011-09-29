package suvaline.pack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class OpenActivity extends Activity {
	public OpenActivity activity;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        ImageButton search = (ImageButton) findViewById(R.id.btn_Search);
        search.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), Search.class);
                startActivityForResult(myIntent, 0);
            }

        });
        Button activity = (Button) findViewById(R.id.btn_Activity_Stream);
        activity.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), Activity_Stream.class);
                startActivityForResult(myIntent, 0);
            }
        });
	    Button network = (Button) findViewById(R.id.btn_Network);
	    network.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View view) {
	            Intent myIntent = new Intent(view.getContext(), Network.class);
	            startActivityForResult(myIntent, 0);
	        }
	    });
	    Button template = (Button) findViewById(R.id.btn_Template);
	    template.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View view) {
	            Intent myIntent = new Intent(view.getContext(), Template.class);
	            startActivityForResult(myIntent, 0);
	        }
	    });
	    Button storage = (Button) findViewById(R.id.btn_Storage);
	    storage.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View view) {
	            Intent myIntent = new Intent(view.getContext(), Storage.class);
	            startActivityForResult(myIntent, 0);
	        }
	    });
	    Button compute = (Button) findViewById(R.id.btn_Compute);
	    compute.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View view) {
	            Intent myIntent = new Intent(view.getContext(), Compute.class);
	            startActivityForResult(myIntent, 0);
	        }
	    });
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