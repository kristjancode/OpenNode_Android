package activity.pack;

import core.interfaces.StorageManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class StorageActivity extends Activity {
	private ListView storageListView;
	@Override
	public void onCreate(final Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.storage);
		StorageManager storageManager = UI_Core.getCore().storageManager();
		boolean itemsLoaded = storageManager.loadItems();
		int elementsInstorageList = storageManager.itemCount();	
		core.models.Storage storageList[] = new core.models.Storage[elementsInstorageList];
		String listItems[] = new String[elementsInstorageList];
		for (int counter=0; counter<elementsInstorageList; counter++){
			storageList[counter]=storageManager.item(counter);
			listItems[counter] = storageList[counter].name();
		}
		storageListView = (ListView) findViewById(R.id.list_storage);
		storageListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1 , listItems));
		storageListView.setOnItemClickListener( new AdapterView.OnItemClickListener() {  
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					Intent intent = new Intent(arg1.getContext(), MainActivity.class);  //No menu yet!
					startActivity(intent);
				}  
		      });  
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