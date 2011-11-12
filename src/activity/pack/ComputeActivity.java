package activity.pack;

import java.util.List;

import core.interfaces.ComputeManager;
import core.models.Compute;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;


public class ComputeActivity extends Activity {
	private ListView computeListView;
	private ListView computeExtraListView;
	private String[] listItems;
	private String[] extraListItems;
	static long selectedItemID;
	private ComputeManager computeManager;
	private core.models.Compute computeList[];
	static int actionValue=0;
	static int back = 0;
	protected ArrayAdapter<CharSequence> mAdapter;
	protected ArrayAdapter<CharSequence> m2Adapter;
	private int editPage=0;
	private Activity computeActivity = this;


	private class MyArrayAdapter<T> extends ArrayAdapter<T>
	{
		public MyArrayAdapter(Context context, int resource, int textViewResourceId, String[] listItems) {
			super(context, resource, textViewResourceId, (T[]) listItems);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View itemView = super.getView(position, convertView, parent);
			ImageView imageView = (ImageView) itemView.findViewById(R.id.icon);
			core.models.Compute selectedItem = computeManager.item(position);
			//computeManager.details(selectedItem);    
			if (selectedItem.state().equals("running")){
				imageView.setImageResource(R.drawable.start48);
			}
			else {
				if (selectedItem.state().equals("stopped")){
					imageView.setImageResource(R.drawable.stop48);
				}
				else{
					if (selectedItem.state().equals("suspended")){
						imageView.setImageResource(R.drawable.delete48);
					}
				}
			}            


			return itemView;
		}
	}
	@Override
	public void onCreate(final Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.compute);
		computeManager = UI_Core.getCore().computeManager();
		boolean itemsLoaded = computeManager.load();	
		int ItemsInComputeList = computeManager.count();	
		computeList = new core.models.Compute[ItemsInComputeList];
		listItems = new String[ItemsInComputeList];

		for (int counter=0; counter<ItemsInComputeList; counter++){
			computeList[counter]=computeManager.item(counter);
			listItems[counter] = computeList[counter].name();
		}
		ImageView search = (ImageView) findViewById(R.id.btn_computeSearch);
		search.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				SearchActivity.compCheck = true;
				back=0;
				Intent myIntent = new Intent(view.getContext(),	SearchActivity.class);
				startActivityForResult(myIntent, 0);
			}

		});
		computeListView = (ListView) findViewById(R.id.list_compute);
		//computeListView.setLongClickable(true);
		computeListView.setAdapter(new MyArrayAdapter<String>(this,R.layout.row , R.id.computeRow, listItems));



		registerForContextMenu(computeListView);		
		computeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {  				
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				actionValue = 0;
				back = 1;
				selectedItemID = arg2;
				Intent myIntent = new Intent(arg1.getContext(),	ComputeDetailActivity.class);
				startActivityForResult(myIntent, 0);
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
			back = 1;
			Intent intent = new Intent(this, ComputeDetailActivity.class);
			this.startActivity(intent);
			break;
		case R.id.start_machine:
			start_machine();
			break;
		case R.id.compute_edit:
			edit_compute();
			break;
		case R.id.stop_machine:
			stop_machine();
			break;
		case R.id.suspend_machine:
			suspend_machine();
			break;
		case R.id.migrate_machine:
			migrate_machine();
			break;
		case R.id.delete_machine:
			delete_machine();
			break;
		}
		return true;
	}

	private void edit_compute() {
		setContentView(R.layout.create_vm);
		final core.models.Compute selectedItem = computeManager.item((int) ComputeActivity.selectedItemID);
		editPage=1;
		TextView nameText = (TextView) findViewById(R.id.create_vm_label);
		nameText.setText("Edit Compute");
		TextView tempText = (TextView) findViewById(R.id.textView9);
		final EditText nameEdit = (EditText) findViewById(R.id.editText1);
		final Spinner archEditSpinner = (Spinner) findViewById(R.id.spinnerArch);
		final EditText coresEdit = (EditText) findViewById(R.id.editText3);
		final EditText cpuEdit = (EditText) findViewById(R.id.editText4);
		final EditText memoryEdit = (EditText) findViewById(R.id.editText5);
		final Spinner stateEditSpinner = (Spinner) findViewById(R.id.spinnerState);
		nameEdit.setText(""+selectedItem.name());
		coresEdit.setText(""+selectedItem.cores());
		cpuEdit.setText(""+selectedItem.cpu());
		memoryEdit.setText(""+selectedItem.memory());
		this.mAdapter = ArrayAdapter.createFromResource(this, R.array.state_array,
				android.R.layout.simple_spinner_item);
		stateEditSpinner.setAdapter(this.mAdapter);

		int i = mAdapter.getPosition(selectedItem.state());
		stateEditSpinner.setSelection(i);

		this.m2Adapter = ArrayAdapter.createFromResource(this, R.array.arch_array,
				android.R.layout.simple_spinner_item);
		archEditSpinner.setAdapter(this.m2Adapter);
		int j = m2Adapter.getPosition(selectedItem.arch());
		archEditSpinner.setSelection(j);
		tempText.setText("Template : "+selectedItem.template());

		tempText.setText("Template : "+selectedItem.template());

		//final int computeId = i;
		//idText.setText("ID : "+computeId);

		Button createCompute = (Button) findViewById(R.id.btn_create_vm);
		createCompute.setText("Edit");
		createCompute.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				if ((!nameEdit.getText().toString().equals("")) && (!coresEdit.getText().toString().equals("")) && (!cpuEdit.getText().toString().equals("")) && (!memoryEdit.getText().toString().equals(""))){
					Compute newCompute = new Compute(0,nameEdit.getText().toString(), archEditSpinner.getSelectedItem().toString(), Integer.parseInt(coresEdit.getText().toString()), Float.parseFloat(cpuEdit.getText().toString()), Integer.parseInt(memoryEdit.getText().toString()), stateEditSpinner.getSelectedItem().toString(), selectedItem.template());
					computeManager.update(selectedItem, newCompute);
					Intent myIntent = new Intent(view.getContext(), ComputeActivity.class);
					startActivityForResult(myIntent, 0);
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


	private void migrate_machine() {
		Context context = getApplicationContext();
		CharSequence text = "Functionality will be added in next version.";
		int duration = Toast.LENGTH_LONG;

		Toast toast = Toast.makeText(context, text, duration);
		toast.show();

	}


	public boolean onCreateOptionsMenu(Menu menu2) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.compute_list_actionbar, menu2);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.home:
			back=0;
			Intent intent = new Intent(this, MainActivity.class);
			this.startActivity(intent);
			break;

		}
		return true;
	}


	private void delete_machine() {
	    AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    builder.setMessage("Are you sure you want to delete this item?")
	           .setCancelable(false)
	           .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	           		Compute selectedItem = computeManager.item((int) ComputeActivity.selectedItemID);
	        		computeManager.details(selectedItem);
	        		computeManager.delete(selectedItem);
	        		Intent intent = new Intent(computeActivity, ComputeActivity.class);
	        		computeActivity.startActivity(intent);
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


	private void suspend_machine() {
		core.models.Compute selectedItem = computeManager.item((int) ComputeActivity.selectedItemID);
		computeManager.details(selectedItem);
		Compute newItem = new Compute(selectedItem.id(),selectedItem.name(),selectedItem.arch(), selectedItem.cores(), selectedItem.cpu(), selectedItem.memory(), "suspended", selectedItem.template());
		computeManager.update(selectedItem, newItem);
		Intent intent = new Intent(this, ComputeActivity.class);
		this.startActivity(intent);
	}

	private void stop_machine() {

		core.models.Compute selectedItem = computeManager.item((int) ComputeActivity.selectedItemID);
		computeManager.details(selectedItem);
		Compute newItem = new Compute(selectedItem.id(),selectedItem.name(),selectedItem.arch(), selectedItem.cores(), selectedItem.cpu(), selectedItem.memory(), "stopped", selectedItem.template());
		computeManager.update(selectedItem, newItem);
		Intent intent = new Intent(this, ComputeActivity.class);
		this.startActivity(intent);

	}

	private void start_machine() {
		core.models.Compute selectedItem = computeManager.item((int) ComputeActivity.selectedItemID);
		computeManager.details(selectedItem);
		Compute newItem = new Compute(selectedItem.id(),selectedItem.name(),selectedItem.arch(), selectedItem.cores(), selectedItem.cpu(), selectedItem.memory(), "running", selectedItem.template());
		computeManager.update(selectedItem, newItem);
		Intent intent = new Intent(this, ComputeActivity.class);
		this.startActivity(intent);
	}


	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_SEARCH){
			back=0;
			SearchActivity.compCheck = true;
			Intent intent = new Intent(this, SearchActivity.class);
			this.startActivity(intent);
			return false;
		}else{
			if(keyCode == KeyEvent.KEYCODE_BACK){
				if (editPage==1){
					editPage=0;
					Intent intent = new Intent(this, ComputeActivity.class);
					this.startActivity(intent);
				}
				else{
					back=0;
					Intent intent = new Intent(this, MainActivity.class);
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
