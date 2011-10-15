package activity.pack;

import core.interfaces.NetworkManager;
import core.interfaces.TemplateManager;
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

public class TemplateActivity extends Activity {
	private ListView templateListView;
	private ListView templateExtraListView;
	private String[] listItems;
	private String[] extraListItems;
	private long selectedItemID;
	private TemplateManager templateManager;
	private core.models.Template templateList[];
	private Menu menu;
	@Override
	public void onCreate(final Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.template);
		selectedItemID = -1;
		templateManager = UI_Core.getCore().templateManager();
		boolean itemsLoaded = templateManager.load();	//It seems to be unused, but it´s really for getting new data.
		int elementsIntemplateList = templateManager.count();	
		templateList = new core.models.Template[elementsIntemplateList];
		listItems = new String[elementsIntemplateList];
		for (int counter=0; counter<elementsIntemplateList; counter++){
			templateList[counter]=templateManager.item(counter);
			listItems[counter] = templateList[counter].name();
		}
		templateListView = (ListView) findViewById(R.id.list_templates);
		templateListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1 , listItems));
		registerForContextMenu(templateListView);
		templateListView.setOnItemClickListener( new AdapterView.OnItemClickListener() {  				
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			    	selectedItemID = arg2;
			    	extra_info();
				}  		
		      });  
	}
	
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.template_menu, menu);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
        selectedItemID = info.position;    
        menu.setHeaderTitle(listItems[(int) selectedItemID]);
      }
    public boolean onContextItemSelected(MenuItem item)  {
		switch (item.getItemId()) {
		case R.id.extra_info_template:
			extra_info();
			break;
		case R.id.update_template:
			break;
		case R.id.delete_template:
			break;
		case R.id.create_vm:
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
		core.models.Template selectedItem = templateList[(int) selectedItemID];
		templateManager.details(selectedItem);
		computeExtraLabel.setText(selectedItem.name());
		
		extraListItems = new String[3];
		extraListItems[0] = ("ID : " + selectedItem.id());
		extraListItems[1] = ("Min disk size : " + selectedItem.minDiskSize());
		extraListItems[2] = ("Min memory size : " + selectedItem.minMemorySize());

		
		templateExtraListView = (ListView) findViewById(R.id.list_extra);
		templateExtraListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, extraListItems));
		
	}

	public boolean onCreateOptionsMenu(Menu menu2) {
		menu = menu2;
		if (selectedItemID == -1){

		MenuInflater inflater = getMenuInflater();

		inflater.inflate(R.menu.template_list_actionbar, menu2);
		}
		else{
			MenuInflater inflater = getMenuInflater();

			inflater.inflate(R.menu.template_detail_actionbar, menu2);
		}
		return true;

	}
	public void invalidateOptionsMenu (){
		menu.clear();
		if (selectedItemID == -1){

		MenuInflater inflater = getMenuInflater();

		inflater.inflate(R.menu.template_list_actionbar, menu);
		}
		else{
			MenuInflater inflater = getMenuInflater();

			inflater.inflate(R.menu.template_detail_actionbar, menu);
		}
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