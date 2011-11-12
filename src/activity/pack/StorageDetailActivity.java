package activity.pack;

import core.interfaces.StorageManager;
import core.models.News;
import core.models.Storage;
import core.models.Template;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class StorageDetailActivity extends Activity {
	public static StorageDetailActivity activity;
	private ListView storageExtraListView;
	private String[] extraListItems;
	private static StorageManager storageManager;
	private Storage selectedItem;
	private int actionValue = StorageActivity.actionValue;
	private ArrayAdapter<CharSequence> mAdapter;
	private Activity storageActivity = this;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CreateStorageManager();
		if (actionValue ==1){
			update_storage();
		}

		else{
			setContentView(R.layout.extra2);
			ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBarStorage);

			TextView computeExtraLabel = (TextView) findViewById(R.id.extra_label);
			computeExtraLabel.setText(selectedItem.name());
			TextView smallId = (TextView) findViewById(R.id.smallId);
			smallId.setText("ID : " + selectedItem.id());

			progressBar.setProgress(100*(selectedItem.capacity()-selectedItem.available())/selectedItem.capacity());
			extraListItems = new String[3];
			extraListItems[0] = ("Available: " + selectedItem.available());
			extraListItems[1] = ("Capacity: " + selectedItem.capacity());
			extraListItems[2] = ("Type: " + selectedItem.type());



			storageExtraListView = (ListView) findViewById(R.id.list_extra);
			storageExtraListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, extraListItems));



		}
	}
	private void CreateStorageManager() {
		storageManager = UI_Core.getCore().storageManager();
		//boolean itemsLoaded = storageManager.load();

		if (StorageActivity.back==1){
			selectedItem = storageManager.item((int) StorageActivity.selectedItemID);
		}
		else{
			selectedItem = (Storage) UI_Core.core.searchManager().item((int) SearchActivity.selectedItemID);
		}

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
	    AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    builder.setMessage("Are you sure you want to delete this item?")
	           .setCancelable(false)
	           .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	           		storageManager.delete(selectedItem);
	        		Intent intent = new Intent(storageActivity, StorageActivity.class);
	        		storageActivity.startActivity(intent);
	               }
	           })
	           .setNegativeButton("No", new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	                    dialog.cancel();
	               }
	           });
	    AlertDialog alert = builder.create();
	    alert.show();


	}

	public void update_storage() {
		setContentView(R.layout.update_storage);
		//CreateStorageManager();


		final EditText sizeEdit = (EditText) findViewById(R.id.editText2);
		final EditText availableEdit = (EditText) findViewById(R.id.editText1);
		final Spinner typeEditSpinner = (Spinner) findViewById(R.id.storageSpinner);
		final EditText nameEdit = (EditText) findViewById(R.id.editText4);

		this.mAdapter = ArrayAdapter.createFromResource(this, R.array.type2_array,
				android.R.layout.simple_spinner_item);
		typeEditSpinner.setAdapter(this.mAdapter);
		int i = mAdapter.getPosition(selectedItem.type());
		typeEditSpinner.setSelection(i);

		availableEdit.setText("" + selectedItem.available());
		sizeEdit.setText("" + selectedItem.capacity());
		nameEdit.setText("" + selectedItem.name());

		Button updateStorage = (Button) findViewById(R.id.btn_update_storage);
		updateStorage.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				if ((!nameEdit.getText().toString().equals("")) && (!sizeEdit.getText().toString().equals(""))){
					if (Integer.parseInt(availableEdit.getText().toString())<selectedItem.capacity()){
						Storage newStorage = new Storage(selectedItem.id(),nameEdit.getText().toString(), typeEditSpinner.getSelectedItem().toString(), Integer.parseInt(sizeEdit.getText().toString()), Integer.parseInt(availableEdit.getText().toString()));
						storageManager.update(selectedItem, newStorage);
						Intent myIntent = new Intent(view.getContext(), StorageActivity.class);
						startActivityForResult(myIntent, 0);
					}
					else{
						Context context = getApplicationContext();
						CharSequence text = "The value of available can not be larger than the value of capacity";
						int duration = Toast.LENGTH_LONG;

						Toast toast = Toast.makeText(context, text, duration);
						toast.show();
					}
				}
				else{
					Context context = getApplicationContext();
					CharSequence text = "All fields must be filled";
					int duration = Toast.LENGTH_LONG;

					Toast toast = Toast.makeText(context, text, duration);
					toast.show();
				}
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
				if (StorageActivity.back==1){
					Intent intent = new Intent(this, StorageActivity.class);
					this.startActivity(intent);
				}
				else{
					Intent intent = new Intent(this, SearchActivity.class);
					this.startActivity(intent);
				}
				return false;

			}
			else{
				return super.onKeyUp(keyCode, event); 
			}
		}
	}
}
