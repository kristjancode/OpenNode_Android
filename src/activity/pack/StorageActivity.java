package activity.pack;

import core.interfaces.NetworkManager;
import core.interfaces.StorageManager;
import core.models.Storage;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class StorageActivity extends Activity {
	private ListView storageListView;
	private String[] listItems;
	static long selectedItemID;
	private StorageManager storageManager;
	private core.models.Storage storageList[];
	private Menu menu;
	static int actionValue = 0;
	static int back = 0; //It shows if detailed view is visited from here or from search to know where to go back.
	@Override
	public void onCreate(final Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.storage);
		storageManager = UI_Core.getCore().storageManager();
		boolean itemsLoaded = storageManager.load();;
		int elementsInstorageList = storageManager.count();;	
		storageList = new core.models.Storage[elementsInstorageList];
		listItems = new String[elementsInstorageList];
		ImageView search = (ImageView) findViewById(R.id.btn_computeSearch);
		search.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
	        	SearchActivity.storCheck = true;
				Intent myIntent = new Intent(view.getContext(),
						SearchActivity.class);
				startActivityForResult(myIntent, 0);
			}

		});
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
					actionValue = 0;
					back = 1;
					Intent myIntent = new Intent(arg1.getContext(),	StorageDetailActivity.class);
					startActivityForResult(myIntent, 0);
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
			actionValue = 0;
			Intent intent = new Intent(this, StorageDetailActivity.class);
			this.startActivity(intent);
			break;
		case R.id.update_storage:
			actionValue = 1;
			back =1;
			Intent intent1 = new Intent(this, StorageDetailActivity.class);
			this.startActivity(intent1);
			//update_storage();
			break;
		case R.id.delete_storage:
				delete_storage();
			break;

		}
		return true;
	
    }


	public boolean onCreateOptionsMenu(Menu menu2) {

		MenuInflater inflater = getMenuInflater();

		inflater.inflate(R.menu.storage_list_actionbar, menu2);
		
		return true;

	}
	public void invalidateOptionsMenu (){

		MenuInflater inflater = getMenuInflater();

		inflater.inflate(R.menu.storage_list_actionbar, menu);

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
	public void delete_storage() {

		final core.models.Storage selectedItem = storageManager.item((int) StorageActivity.selectedItemID);
		storageManager.details(selectedItem);
		storageManager.delete(selectedItem);
		Intent intent = new Intent(this , StorageActivity.class);
		this.startActivity(intent);
		
	}
	private void create_storage() {
		setContentView(R.layout.update_storage);
		if (menu != null){
			invalidateOptionsMenu ();
		}
		int i=0;
		while (i<storageManager.count()){
			if (storageManager.item(i)==null){
				break;
			}
			i++;
		}
		TextView nameText = (TextView) findViewById(R.id.update_storage_label);
		nameText.setText("Create Storage");
		final EditText sizeEdit = (EditText) findViewById(R.id.editText2);
		final EditText typeEdit = (EditText) findViewById(R.id.editText3);
		final EditText nameEdit = (EditText) findViewById(R.id.editText4);
		final int storageId = i;
		
		Button updateStorage = (Button) findViewById(R.id.btn_update_storage);
		updateStorage.setText("Create");
		updateStorage.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Storage newStorage = new Storage(storageId,nameEdit.getText().toString(), Integer.parseInt(sizeEdit.getText().toString()), typeEdit.getText().toString());
				storageManager.create(newStorage);
				Intent myIntent = new Intent(view.getContext(), StorageActivity.class);
				startActivityForResult(myIntent, 0);
			}
		});			
		
	}

	public boolean onKeyUp(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_SEARCH){
        	SearchActivity.storCheck = true;
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