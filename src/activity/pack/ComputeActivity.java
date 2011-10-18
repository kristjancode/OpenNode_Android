package activity.pack;

import core.interfaces.ComputeManager;
import core.models.Compute;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


public class ComputeActivity extends Activity {
	private ListView computeListView;
	private ListView computeExtraListView;
	private String[] listItems;
	private String[] extraListItems;
	static long selectedItemID;
	private ComputeManager computeManager;
	private core.models.Compute computeList[];
	static int actionValue=0;

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

		computeListView = (ListView) findViewById(R.id.list_compute);
		computeListView.setLongClickable(true);
		computeListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems));
		registerForContextMenu(computeListView);
		computeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {  				
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				actionValue = 0;
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
			Intent intent = new Intent(this, ComputeDetailActivity.class);
			this.startActivity(intent);
			break;
		case R.id.start_machine:
			start_machine();
			break;
		case R.id.stop_machine:
			stop_machine();
			break;
		case R.id.suspend_machine:
			suspend_machine();
			break;
		case R.id.delete_machine:
			delete_machine();
			break;
		}
		return true;
	}
    
	public boolean onCreateOptionsMenu(Menu menu2) {

		MenuInflater inflater = getMenuInflater();

		inflater.inflate(R.menu.compute_list_actionbar, menu2);

		return true;

	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.home:
			Intent intent = new Intent(this, MainActivity.class);
			this.startActivity(intent);
			break;
		case R.id.settings:
			break;
		}
		return true;
	}
	
	private void create_compute() {
		setContentView(R.layout.create_vm);

		int i=0;
		while (i<computeManager.count()){
			if (computeManager.item(i)==null){
				break;
			}
			i++;
		}
		TextView nameText = (TextView) findViewById(R.id.create_vm_label);
		nameText.setText("Create Compute");
		TextView idText = (TextView) findViewById(R.id.textView1);
		final EditText nameEdit = (EditText) findViewById(R.id.editText1);
		final EditText archEdit = (EditText) findViewById(R.id.editText2);
		final EditText coresEdit = (EditText) findViewById(R.id.editText3);
		final EditText cpuEdit = (EditText) findViewById(R.id.editText4);
		final EditText memoryEdit = (EditText) findViewById(R.id.editText5);
		final EditText stateEdit = (EditText) findViewById(R.id.editText6);
		final int computeId = i;
		idText.setText("ID : "+computeId);
		
		Button createCompute = (Button) findViewById(R.id.btn_create_vm);
		createCompute.setText("Create");
		createCompute.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {

				Compute newCompute = new Compute(computeId,nameEdit.getText().toString(), archEdit.getText().toString(), Integer.parseInt(coresEdit.getText().toString()), Float.parseFloat(cpuEdit.getText().toString()), Integer.parseInt(memoryEdit.getText().toString()), stateEdit.getText().toString(), "Suva");
				computeManager.create(newCompute);
				Intent myIntent = new Intent(view.getContext(), ComputeActivity.class);
				startActivityForResult(myIntent, 0);
			}
		});			
		
	}
	private void delete_machine() {
		core.models.Compute selectedItem = computeManager.item((int) ComputeActivity.selectedItemID);
		computeManager.details(selectedItem);
		computeManager.delete(selectedItem);
		Intent intent = new Intent(this, ComputeActivity.class);
		this.startActivity(intent);
		
	}


	private void suspend_machine() {
		core.models.Compute selectedItem = computeManager.item((int) ComputeActivity.selectedItemID);
		computeManager.details(selectedItem);
		Compute newItem = new Compute(selectedItem.id(),selectedItem.name(),selectedItem.arch(), selectedItem.cores(), selectedItem.cpu(), selectedItem.memory(), "suspended", selectedItem.template());
		computeManager.update(selectedItem, newItem);

		
	}

	private void stop_machine() {

		core.models.Compute selectedItem = computeManager.item((int) ComputeActivity.selectedItemID);
		computeManager.details(selectedItem);
		Compute newItem = new Compute(selectedItem.id(),selectedItem.name(),selectedItem.arch(), selectedItem.cores(), selectedItem.cpu(), selectedItem.memory(), "stopped", selectedItem.template());
		computeManager.update(selectedItem, newItem);

		
	}

	private void start_machine() {
		core.models.Compute selectedItem = computeManager.item((int) ComputeActivity.selectedItemID);
		computeManager.details(selectedItem);
		Compute newItem = new Compute(selectedItem.id(),selectedItem.name(),selectedItem.arch(), selectedItem.cores(), selectedItem.cpu(), selectedItem.memory(), "running", selectedItem.template());
		computeManager.update(selectedItem, newItem);

	}


	public boolean onKeyUp(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_SEARCH){
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
