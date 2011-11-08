package activity.pack;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import core.interfaces.ComputeManager;
import core.models.Compute;
import core.models.Compute;
import core.models.Template;

public class ComputeDetailActivity extends Activity {
	public ComputeDetailActivity activity;
	private ListView computeExtraListView;
	private String[] extraListItems;
	private ComputeManager computeManager;
	private Compute selectedItem;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

			setContentView(R.layout.extra);
			
			computeManager = UI_Core.getCore().computeManager();
			//boolean itemsLoaded = computeManager.load();;
			TextView smallId = (TextView) findViewById(R.id.smallId);
			TextView computeExtraLabel = (TextView) findViewById(R.id.extra_label);
			if (ComputeActivity.back==1){
				selectedItem = computeManager.item((int) ComputeActivity.selectedItemID);
			}
			else{
				selectedItem = (Compute) UI_Core.core.searchManager().item((int) SearchActivity.selectedItemID);
			}
			computeManager.details(selectedItem);
			computeExtraLabel.setText(selectedItem.name());
			smallId.setText("ID : " + selectedItem.id());
			
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

			MenuInflater inflater = getMenuInflater();

			inflater.inflate(R.menu.compute_detail_actionbar, menu2);
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
		case R.id.start_machine:
			start_machine();
			break;
		case R.id.stop_machine:
			stop_machine();
			break;
		case R.id.migrate_machine:
			migrate_machine();
			break;
		case R.id.suspend_machine:
			suspend_machine();
			break;
		case R.id.delete_machine:
			delete_machine();
			break;
		default:
			
		}
		return true;
	}
	
	private void migrate_machine() {
		Context context = getApplicationContext();
		CharSequence text = "Functionality will be added in next version.";
		int duration = Toast.LENGTH_LONG;

		Toast toast = Toast.makeText(context, text, duration);
		toast.show();		
	}
	private void delete_machine() {
		Compute selectedItem = computeManager.item((int) ComputeActivity.selectedItemID);
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
			Intent intent = new Intent(this, SearchActivity.class);
			this.startActivity(intent);
                return false;
        }else{
        	 if(keyCode == KeyEvent.KEYCODE_BACK){
         		if (ComputeActivity.back==1){
 	     			Intent intent = new Intent(this, ComputeActivity.class);
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
