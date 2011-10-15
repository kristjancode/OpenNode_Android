package activity.pack;

import core.interfaces.ComputeManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class ComputeActivity extends Activity {
	private ListView computeListView;
	private ListView computeExtraListView;
	private String[] listItems;
	private String[] extraListItems;
	private long selectedItemID;
	private ComputeManager computeManager;
	private core.models.Compute computeList[];
	private Menu menu;

	@Override
	public void onCreate(final Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.compute);
		selectedItemID = -1;
		computeManager = UI_Core.getCore().computeManager();
		boolean itemsLoaded = computeManager.load();	
		int ItemsInComputeList = computeManager.count();	
		computeList = new core.models.Compute[ItemsInComputeList];
		listItems = new String[ItemsInComputeList];

		for (int counter=0; counter<ItemsInComputeList; counter++){
			computeList[counter]=computeManager.item(counter);
			listItems[counter] = computeList[counter].name();
		}

		computeListView = (ListView) findViewById(R.id.list_compute);
		computeListView.setLongClickable(true);
		computeListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems));
		registerForContextMenu(computeListView);
		computeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {  				
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		    	selectedItemID = arg2;
		    	extra_info();
			}  		
	      });  
	}


    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.compute_menu, menu);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
        selectedItemID = info.position;    
        menu.setHeaderTitle(listItems[(int) selectedItemID]);
      }
    
    public boolean onContextItemSelected(MenuItem item)  {
		switch (item.getItemId()) {
		case R.id.extra_info_compute:
			extra_info();
			break;
		case R.id.start_machine:
			break;
		case R.id.stop_machine:
			break;
		case R.id.suspend_machine:
			break;
		case R.id.migrate_machine:
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
		core.models.Compute selectedItem = computeList[(int) selectedItemID];
		computeManager.details(selectedItem);
		computeExtraLabel.setText(selectedItem.name());
		
		extraListItems = new String[7];
		extraListItems[0] = ("Hostname : " + selectedItem.name());
		extraListItems[1] = ("Arch : " + selectedItem.arch());
		extraListItems[2] = ("Memory : " + selectedItem.memory());
		extraListItems[3] = ("Cpu : " + selectedItem.cpu());
		extraListItems[4] = ("Cores : " + selectedItem.cores());
		extraListItems[5] = ("Template : " + selectedItem.template());
		extraListItems[6] = ("State : " + selectedItem.state());
		
		computeExtraListView = (ListView) findViewById(R.id.list_extra);
		computeExtraListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, extraListItems));

		
	}
    
	public boolean onCreateOptionsMenu(Menu menu2) {
		menu = menu2;
		if (selectedItemID == -1){

		MenuInflater inflater = getMenuInflater();

		inflater.inflate(R.menu.compute_list_actionbar, menu2);
		}
		else{
			MenuInflater inflater = getMenuInflater();

			inflater.inflate(R.menu.compute_detail_actionbar, menu2);
		}
		return true;

	}
	public void invalidateOptionsMenu (){
			menu.clear();
		if (selectedItemID == -1){

		MenuInflater inflater = getMenuInflater();

		inflater.inflate(R.menu.compute_list_actionbar, menu);
		}
		else{
			MenuInflater inflater = getMenuInflater();

			inflater.inflate(R.menu.compute_detail_actionbar, menu);
		}

	}
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.actionbar_item_home:
			Intent intent = new Intent(this, MainActivity.class);
			this.startActivity(intent);
			break;
		case R.id.actionbar_item_create:
			Toast.makeText(this, "You pressed the text!", Toast.LENGTH_LONG).show();
			break;
		case R.id.actionbar_item_search:
			Toast.makeText(this, "You pressed the icon and text!", Toast.LENGTH_LONG).show();
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
