package activity.pack;

import core.interfaces.NetworkManager;
import core.interfaces.StorageManager;
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

public class StorageActivity extends Activity {
	private ListView storageListView;
	private String[] listItems;
	private ListView storageExtraListView;
	private String[] extraListItems;
	private long selectedItemID;
	private StorageManager storageManager;
	private core.models.Storage storageList[];
	@Override
	public void onCreate(final Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.storage);
		storageManager = UI_Core.getCore().storageManager();
		boolean itemsLoaded = storageManager.load();;
		int elementsInstorageList = storageManager.count();;	
		storageList = new core.models.Storage[elementsInstorageList];
		listItems = new String[elementsInstorageList];
		for (int counter=0; counter<elementsInstorageList; counter++){
			storageList[counter]=storageManager.item(counter);
			listItems[counter] = storageList[counter].name();
		}
		storageListView = (ListView) findViewById(R.id.list_storage);
		storageListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1 , listItems));
		registerForContextMenu(storageListView);
		storageListView.setOnItemClickListener( new AdapterView.OnItemClickListener() {  				
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			    	selectedItemID = arg2;
			    	extra_info();
				}  		
		      });  
	}
	
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.storage_menu, menu);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
        selectedItemID = info.position;    
        menu.setHeaderTitle(listItems[(int) selectedItemID]);
      }
    public boolean onContextItemSelected(MenuItem item)  {
		switch (item.getItemId()) {
		case R.id.extra_info_storage:
			extra_info();
			break;
		case R.id.update_storage:
			break;
		case R.id.delete_storage:
			break;

		}
		return true;
	}

	private void extra_info() {
		setContentView(R.layout.extra);
		TextView computeExtraLabel = (TextView) findViewById(R.id.extra_label);
		core.models.Storage selectedItem = storageList[(int) selectedItemID];
		storageManager.details(selectedItem);
		computeExtraLabel.setText(selectedItem.name());
		
		extraListItems = new String[3];
		extraListItems[0] = ("ID : " + selectedItem.id());
		extraListItems[1] = ("Size : " + selectedItem.size());
		extraListItems[2] = ("Type : " + selectedItem.type());


		
		storageExtraListView = (ListView) findViewById(R.id.list_extra);
		storageExtraListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, extraListItems));
		
		
	}

	public boolean onCreateOptionsMenu(Menu menu2) {

		MenuInflater inflater = getMenuInflater();

		inflater.inflate(R.menu.actionbar, menu2);

		return true;

	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.actionbar_item_home:
			Intent intent = new Intent(this, MainActivity.class);
			this.startActivity(intent);
			break;
		case R.id.actionbar_item_create:
			Toast.makeText(this, "You pressed the text!", Toast.LENGTH_LONG)
					.show();
			break;
		case R.id.actionbar_item_search:
			Toast.makeText(this, "You pressed the icon and text!",
					Toast.LENGTH_LONG).show();
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
                return super.onKeyUp(keyCode, event); 
        }
}
}