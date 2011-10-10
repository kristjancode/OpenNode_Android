package activity.pack;

import core.interfaces.TemplateManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class TemplateActivity extends Activity {
	private ListView templateListView;
	private String[] listItems;
	@Override
	public void onCreate(final Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.template);
		TemplateManager templateManager = UI_Core.getCore().templateManager();
		boolean itemsLoaded = templateManager.loadItems();	//It seems to be unused, but it´s really for getting new data.
		int elementsIntemplateList = templateManager.itemCount();	
		core.models.Template templateList[] = new core.models.Template[elementsIntemplateList];
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
		              registerForContextMenu(arg0);
		              arg1.showContextMenu();
				}  		
		      });  
	}
	
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.template_menu, menu);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
        long itemID = info.position;
        menu.setHeaderTitle(listItems[(int) itemID]);
      }
    public boolean onContextItemSelected(MenuItem item)  {
		switch (item.getItemId()) {
		case R.id.extra_info_template:
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

}