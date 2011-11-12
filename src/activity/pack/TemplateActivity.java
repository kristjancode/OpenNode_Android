package activity.pack;

import core.interfaces.ComputeManager;
import core.interfaces.NetworkManager;
import core.interfaces.TemplateManager;
import core.models.Compute;
import core.models.Storage;
import core.models.Template;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class TemplateActivity extends Activity {
	private ListView templateListView;
	private ListView templateExtraListView;
	private String[] listItems;
	private String[] extraListItems;
	static long selectedItemID;
	private TemplateManager templateManager;
	private core.models.Template templateList[];
	private ComputeManager computeManager;
	static int actionValue = 0;
	static int back = 0;

	@Override
	public void onCreate(final Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.template);
		templateManager = UI_Core.getCore().templateManager();
		computeManager = UI_Core.getCore().computeManager();
		boolean itemsLoaded = templateManager.load();	//It seems to be unused, but it´s really for getting new data.
		int elementsIntemplateList = templateManager.count();	
		templateList = new core.models.Template[elementsIntemplateList];
		listItems = new String[elementsIntemplateList];
		ImageView search = (ImageView) findViewById(R.id.btn_computeSearch);
		search.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				SearchActivity.tempCheck = true;
				back=0;
				Intent myIntent = new Intent(view.getContext(),
						SearchActivity.class);
				startActivityForResult(myIntent, 0);
			}

		});
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
				actionValue = 0;
				back = 1;
				Intent myIntent = new Intent(arg1.getContext(),	TemplateDetailActivity.class);
				startActivityForResult(myIntent, 0);
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
			actionValue = 0;
			back=1;
			Intent intent = new Intent(this, TemplateDetailActivity.class);
			this.startActivity(intent);
			break;
		case R.id.update_template:
			actionValue = 1;
			back=1;
			Intent intent1 = new Intent(this, TemplateDetailActivity.class);
			this.startActivity(intent1);
			break;
		case R.id.delete_template:
			delete_template();
			break;
		case R.id.create_vm2:
			actionValue = 2;
			back=1;
			Intent intent2 = new Intent(this, TemplateDetailActivity.class);
			this.startActivity(intent2);
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
		final core.models.Template selectedItem = templateList[(int) selectedItemID];
		templateManager.details(selectedItem);

		TextView nameText = (TextView) findViewById(R.id.create_vm_label);
		nameText.setText("Create Compute");
		TextView tempText = (TextView) findViewById(R.id.textView9);
		final EditText nameEdit = (EditText) findViewById(R.id.editText1);
		final EditText archEdit = (EditText) findViewById(R.id.editText2);
		final EditText coresEdit = (EditText) findViewById(R.id.editText3);
		final EditText cpuEdit = (EditText) findViewById(R.id.editText4);
		final EditText memoryEdit = (EditText) findViewById(R.id.editText5);
		final Spinner stateEditSpinner = (Spinner) findViewById(R.id.spinnerState);
		final String stateEdit = stateEditSpinner.getSelectedItem().toString();

		tempText.setText("Template : "+selectedItem.name());

		Button createCompute = (Button) findViewById(R.id.btn_create_vm);
		createCompute.setText("Create");
		createCompute.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {

				Compute newCompute = new Compute(0,nameEdit.getText().toString(), archEdit.getText().toString(), Integer.parseInt(coresEdit.getText().toString()), Float.parseFloat(cpuEdit.getText().toString()), Integer.parseInt(memoryEdit.getText().toString()), stateEdit, selectedItem.name());
				computeManager.create(newCompute);
				Intent myIntent = new Intent(view.getContext(), ComputeActivity.class);
				startActivityForResult(myIntent, 0);
			}
		});

	}
	private void extra_info() {
		setContentView(R.layout.extra);

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

		MenuInflater inflater = getMenuInflater();

		inflater.inflate(R.menu.template_list_actionbar, menu2);

		return true;

	}


	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.home:
			back=0;
			Intent intent = new Intent(this, MainActivity.class);
			this.startActivity(intent);
			break;
		case R.id.create_vm:
			create_compute();
			break;
		case R.id.update:
			update_template();
			break;
		case R.id.delete:
			delete_template();
			break;
		}
		return true;
	}
	private void delete_template() {

		final Template selectedItem = templateList[(int) selectedItemID];
		templateManager.details(selectedItem);
		templateManager.delete(selectedItem);
		Intent intent = new Intent(this, TemplateActivity.class);
		this.startActivity(intent);

	}

	private void update_template() {
		setContentView(R.layout.update_template);
		final core.models.Template selectedItem = templateList[(int) selectedItemID];
		templateManager.details(selectedItem);

		TextView idText = (TextView) findViewById(R.id.textView1);
		final EditText sizeEdit = (EditText) findViewById(R.id.editText2);
		final EditText typeEdit = (EditText) findViewById(R.id.editText3);
		final EditText nameEdit = (EditText) findViewById(R.id.editText4);

		idText.setText("ID : " + selectedItem.id());
		sizeEdit.setText("" + selectedItem.minDiskSize());
		typeEdit.setText("" + selectedItem.minMemorySize());
		nameEdit.setText("" + selectedItem.name());

		Button updatetemplate = (Button) findViewById(R.id.btn_update_storage);
		updatetemplate.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				sizeEdit.getText();
				typeEdit.getText();
				nameEdit.getText();
				Template newtemplate = new Template(selectedItem.id(),nameEdit.getText().toString(), Integer.parseInt(sizeEdit.getText().toString()), Integer.parseInt(typeEdit.getText().toString()), 0, 0, 0, 0);
				templateManager.update(selectedItem, newtemplate);
				Intent myIntent = new Intent(view.getContext(), TemplateActivity.class);
				startActivityForResult(myIntent, 0);
			}
		});	

	}

	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_SEARCH){
			back=0;
			SearchActivity.tempCheck = true;
			Intent intent = new Intent(this, SearchActivity.class);
			this.startActivity(intent);
			return false;
		}else{
			if(keyCode == KeyEvent.KEYCODE_BACK){
				back=0;
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