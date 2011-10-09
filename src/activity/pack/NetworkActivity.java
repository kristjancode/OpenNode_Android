package activity.pack;

import core.interfaces.NetworkManager;
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

public class NetworkActivity extends Activity {
	private ListView networkListView;
	@Override
	public void onCreate(final Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.network);
		NetworkManager networkManager = UI_Core.getCore().networkManager();
		boolean itemsLoaded = networkManager.loadItems();	//It seems to be unused, but it�s really for getting new data.
		int elementsInnetworkList = networkManager.itemCount();	
		core.models.Network networkList[] = new core.models.Network[elementsInnetworkList];
		String listItems[] = new String[elementsInnetworkList];
		for (int counter=0; counter<elementsInnetworkList; counter++){
			networkList[counter]=networkManager.item(counter);
			listItems[counter] = networkList[counter].name();
		}
		networkListView = (ListView) findViewById(R.id.list_network);
		networkListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1 , listItems));
		networkListView.setOnItemClickListener( new AdapterView.OnItemClickListener() {  
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
