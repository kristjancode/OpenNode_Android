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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class TemplateActivity extends Activity {
	private ListView templateListView;
	private ListView templateExtraListView;
	private String[] listItems;
	private String[] extraListItems;
	private long selectedItemID;
	private TemplateManager templateManager;
	private core.models.Template templateList[];
	private Menu menu;
	private ComputeManager computeManager;
	@Override
	public void onCreate(final Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.template);
		selectedItemID = -1;
		templateManager = UI_Core.getCore().templateManager();
		computeManager = UI_Core.getCore().computeManager();
		boolean itemsLoaded = templateManager.load();	//It seems to be unused, but it´s really for getting new data.
		int elementsIntemplateList = templateManager.count();	
		templateList = new core.models.Template[elementsIntemplateList];
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
			    	selectedItemID = arg2;
			    	extra_info();
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
			extra_info();
			break;
		case R.id.update_template:
			update_template();
			break;
		case R.id.delete_template:
			delete_template();
			break;
		case R.id.create_vm:
			create_compute();
			break;

		}
		return true;
	}
	private void create_compute() {
		setContentView(R.layout.create_vm);
		if (menu != null){
			invalidateOptionsMenu ();
		}
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
		TextView idText = (TextView) findViewById(R.id.textView1);
		TextView tempText = (TextView) findViewById(R.id.textView9);
		final EditText nameEdit = (EditText) findViewById(R.id.editText1);
		final EditText archEdit = (EditText) findViewById(R.id.editText2);
		final EditText coresEdit = (EditText) findViewById(R.id.editText3);
		final EditText cpuEdit = (EditText) findViewById(R.id.editText4);
		final EditText memoryEdit = (EditText) findViewById(R.id.editText5);
		final EditText stateEdit = (EditText) findViewById(R.id.editText6);
		final int computeId = i;
		idText.setText("ID : "+computeId);
		tempText.setText("Template : "+selectedItem.name());
		
		Button createCompute = (Button) findViewById(R.id.btn_create_vm);
		createCompute.setText("Create");
		createCompute.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {

				Compute newCompute = new Compute(computeId,nameEdit.getText().toString(), archEdit.getText().toString(), Integer.parseInt(coresEdit.getText().toString()), Float.parseFloat(cpuEdit.getText().toString()), Integer.parseInt(memoryEdit.getText().toString()), stateEdit.getText().toString(), selectedItem.name());
				computeManager.create(newCompute);
				Intent myIntent = new Intent(view.getContext(), ComputeActivity.class);
				startActivityForResult(myIntent, 0);
			}
		});
		
	}
	private void extra_info() {
		setContentView(R.layout.extra);
		if (menu != null){
			invalidateOptionsMenu ();
		}
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
		menu = menu2;
		if (selectedItemID == -1){

		MenuInflater inflater = getMenuInflater();

		inflater.inflate(R.menu.template_list_actionbar, menu2);
		}
		else{
			MenuInflater inflater = getMenuInflater();

			inflater.inflate(R.menu.template_detail_actionbar, menu2);
		}
		return true;

	}
	public void invalidateOptionsMenu (){
		menu.clear();
		if (selectedItemID == -1){

		MenuInflater inflater = getMenuInflater();

		inflater.inflate(R.menu.template_list_actionbar, menu);
		}
		else{
			MenuInflater inflater = getMenuInflater();

			inflater.inflate(R.menu.template_detail_actionbar, menu);
		}
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.home:
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
		if (menu != null){
			invalidateOptionsMenu ();
		}
		final Template selectedItem = templateList[(int) selectedItemID];
		templateManager.details(selectedItem);
		templateManager.delete(selectedItem);
		Intent intent = new Intent(this, TemplateActivity.class);
		this.startActivity(intent);
		
	}

	private void update_template() {
		setContentView(R.layout.update_template);
		if (menu != null){
			invalidateOptionsMenu ();
		}
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
				Template newtemplate = new Template(selectedItem.id(),nameEdit.getText().toString(), Integer.parseInt(sizeEdit.getText().toString()), Integer.parseInt(typeEdit.getText().toString()));
				templateManager.update(selectedItem, newtemplate);
				Intent myIntent = new Intent(view.getContext(), TemplateActivity.class);
				startActivityForResult(myIntent, 0);
			}
		});	
		
	}

	public boolean onKeyUp(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_SEARCH){
			Intent intent = new Intent(this, SearchActivity.class);
			this.startActivity(intent);
                return false;
        }else{
                return super.onKeyUp(keyCode, event); 
        }
}
}