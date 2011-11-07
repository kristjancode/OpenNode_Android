package activity.pack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import core.interfaces.NetworkManager;
import core.models.Network;

public class NetworkDetailActivity extends Activity {
	public NetworkDetailActivity activity;
	private ListView networkExtraListView;
	private String[] extraListItems;
	private NetworkManager networkManager;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.extra);
		networkManager = UI_Core.getCore().networkManager();
		//boolean itemsLoaded = networkManager.load();;
		
		Network selectedItem = networkManager.item((int) NetworkActivity.selectedItemID);
		networkManager.details(selectedItem);
		
		TextView computeExtraLabel = (TextView) findViewById(R.id.extra_label);
		computeExtraLabel.setText(selectedItem.name());
		TextView smallId = (TextView) findViewById(R.id.smallId);
		smallId.setText("ID : " + selectedItem.id());
		
		extraListItems = new String[4];
		extraListItems[0] = ("IP : " + selectedItem.ip());
		extraListItems[1] = ("Mask : " + selectedItem.mask());
		extraListItems[2] = ("Address allocation : " + selectedItem.addressAllocation());
		extraListItems[3] = ("Gateway : " + selectedItem.gateway());

		
		networkExtraListView = (ListView) findViewById(R.id.list_extra);
		networkExtraListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, extraListItems));
		
			}
	public boolean onCreateOptionsMenu(Menu menu2) {

			//MenuInflater inflater = getMenuInflater();

			//inflater.inflate(R.menu.detail_actionbar, menu2);
			return true;

	}


	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.home:
			Intent intent = new Intent(this, MainActivity.class);
			this.startActivity(intent);
			break;
		}
		return true;
	}

	public boolean onKeyUp(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_SEARCH){
        	SearchActivity.netwCheck = true;
			Intent intent = new Intent(this, SearchActivity.class);
			this.startActivity(intent);
                return false;
        }else{
        	 if(keyCode == KeyEvent.KEYCODE_BACK){
     			Intent intent = new Intent(this, NetworkActivity.class);
    			this.startActivity(intent);
                    return false;
        	 }
        	 else{
                return super.onKeyUp(keyCode, event); 
        	 }
        }
}
}
