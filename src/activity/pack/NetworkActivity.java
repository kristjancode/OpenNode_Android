package activity.pack;

import core.interfaces.NetworkManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class NetworkActivity extends Activity {
	private ListView networkListView;
	private ListView networkExtraListView;
	private String[] listItems;
	private String[] extraListItems;
	private long selectedItemID;
	private NetworkManager networkManager;
	private core.models.Network networkList[];
	private Menu menu;
	
	public void onCreate(final Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.network);
		selectedItemID = -1;
		networkManager = UI_Core.getCore().networkManager();
		boolean itemsLoaded = networkManager.load();	//It seems to be unused, but it�s really for getting new data.
		int elementsInnetworkList = networkManager.count();	
		networkList = new core.models.Network[elementsInnetworkList];
		listItems = new String[elementsInnetworkList];
		for (int counter=0; counter<elementsInnetworkList; counter++){
			networkList[counter]=networkManager.item(counter);
			listItems[counter] = networkList[counter].name();
		}
		networkListView = (ListView) findViewById(R.id.list_network);
		networkListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1 , listItems));
		registerForContextMenu(networkListView);
		networkListView.setOnItemClickListener( new AdapterView.OnItemClickListener() {  				
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			    	selectedItemID = arg2;
			    	extra_info();
				}  		
		      });  
	}
	public boolean onCreateOptionsMenu(Menu menu2) {
		menu = menu2;
		if (selectedItemID == -1){
			MenuInflater inflater = getMenuInflater();
	
			inflater.inflate(R.menu.network_list_actionbar, menu2);
		}
		return true;

	}
	public void invalidateOptionsMenu (){

		MenuInflater inflater = getMenuInflater();

		inflater.inflate(R.menu.network_list_actionbar, menu);
		menu.clear();
	
	}
	
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.network_menu, menu);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
        selectedItemID = info.position;    
        menu.setHeaderTitle(listItems[(int) selectedItemID]);
      }
    public boolean onContextItemSelected(MenuItem item)  {
		switch (item.getItemId()) {
		case R.id.extra_info_network:
			extra_info();
			break;
		}
		return true;
	}

	private void extra_info() {
		setContentView(R.layout.extra);
		if (menu != null){
			invalidateOptionsMenu ();
		}
		TextView computeExtraLabel = (TextView) findViewById(R.id.extra_label);
		core.models.Network selectedItem = networkList[(int) selectedItemID];
		networkManager.details(selectedItem);
		computeExtraLabel.setText(selectedItem.name());
		
		extraListItems = new String[5];
		extraListItems[0] = ("ID : " + selectedItem.id());
		extraListItems[1] = ("IP : " + selectedItem.ip());
		extraListItems[2] = ("Mask : " + selectedItem.mask());
		extraListItems[3] = ("Address allocation : " + selectedItem.addressAllocation());
		extraListItems[4] = ("Gateway : " + selectedItem.gateway());

		
		networkExtraListView = (ListView) findViewById(R.id.list_extra);
		networkExtraListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, extraListItems));
		
		
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
			Intent intent = new Intent(this, SearchActivity.class);
			this.startActivity(intent);
                return false;
        }else{
        	 if(keyCode == KeyEvent.KEYCODE_BACK){
     			Intent intent = new Intent(this, MainActivity.class);
    			this.startActivity(intent);
                    return false;
        	 }
        	 else{
                return super.onKeyUp(keyCode, event); 
        	 }
        }
}

}
