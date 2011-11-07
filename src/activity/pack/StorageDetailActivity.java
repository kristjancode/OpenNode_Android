package activity.pack;

import core.interfaces.StorageManager;
import core.models.Storage;
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

public class StorageDetailActivity extends Activity {
	public static StorageDetailActivity activity;
	private ListView storageExtraListView;
	private String[] extraListItems;
	private static StorageManager storageManager;
	private Storage selectedItem;
	private int actionValue = StorageActivity.actionValue;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (actionValue ==1){
			update_storage();
		}
		
		else{
				setContentView(R.layout.extra);
				CreateStorageManager();
				
				TextView computeExtraLabel = (TextView) findViewById(R.id.extra_label);
				computeExtraLabel.setText(selectedItem.name());
				TextView smallId = (TextView) findViewById(R.id.smallId);
				smallId.setText("ID : " + selectedItem.id());
			
				extraListItems = new String[2];
				extraListItems[0] = ("Size : " + selectedItem.size());
				extraListItems[1] = ("Type : " + selectedItem.type());
		
		
				
				storageExtraListView = (ListView) findViewById(R.id.list_extra);
				storageExtraListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, extraListItems));
		
		
			
		}
	}
	private void CreateStorageManager() {
		storageManager = UI_Core.getCore().storageManager();
		//boolean itemsLoaded = storageManager.load();
		
		selectedItem = storageManager.item((int) StorageActivity.selectedItemID);
		storageManager.details(selectedItem);
		
	}
	public boolean onCreateOptionsMenu(Menu menu2) {

			MenuInflater inflater = getMenuInflater();

			inflater.inflate(R.menu.detail_actionbar, menu2);
			return true;

	}


	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.home:
			Intent intent = new Intent(this, MainActivity.class);
			this.startActivity(intent);
			break;
		case R.id.update:
			update_storage();
			break;
		case R.id.delete:
			delete_storage();
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

	public void update_storage() {
		setContentView(R.layout.update_storage);
		CreateStorageManager();
		final core.models.Storage selectedItem = storageManager.item((int) StorageActivity.selectedItemID);
		storageManager.details(selectedItem);

		TextView idText = (TextView) findViewById(R.id.textView1);
		final EditText sizeEdit = (EditText) findViewById(R.id.editText2);
		final EditText typeEdit = (EditText) findViewById(R.id.editText3);
		final EditText nameEdit = (EditText) findViewById(R.id.editText4);
		
		idText.setText("ID : " + selectedItem.id());
		sizeEdit.setText("" + selectedItem.size());
		typeEdit.setText("" + selectedItem.type());
		nameEdit.setText("" + selectedItem.name());
		
		Button updateStorage = (Button) findViewById(R.id.btn_update_storage);
		updateStorage.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Storage newStorage = new Storage(selectedItem.id(),nameEdit.getText().toString(), Integer.parseInt(sizeEdit.getText().toString()), typeEdit.getText().toString());
				storageManager.update(selectedItem, newStorage);
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
     			Intent intent = new Intent(this, StorageActivity.class);
    			this.startActivity(intent);
                    return false;
        	 }
        	 else{
                return super.onKeyUp(keyCode, event); 
        	 }
        }
}
}
