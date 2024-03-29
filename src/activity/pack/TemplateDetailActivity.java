package activity.pack;

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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import core.interfaces.ComputeManager;
import core.interfaces.TemplateManager;
import core.models.Compute;
import core.models.Template;

public class TemplateDetailActivity  extends Activity {
	private Template selectedItem;
	private ComputeManager computeManager = UI_Core.getCore().computeManager();
	private int actionValue = TemplateActivity.actionValue;
	private ListView templateExtraListView;
	private String[] extraListItems;
	static long selectedItemID;
	private TemplateManager templateManager;
	protected ArrayAdapter<CharSequence> mAdapter;
	protected ArrayAdapter<CharSequence> m2Adapter;
	private Activity templateActivity = this;
	private int editPage=0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (actionValue ==1){
			update_template();
		}

		else{
			if (actionValue ==2){
				create_compute();
			}
			else{
				setContentView(R.layout.extra);
				CreateTemplateManager();
				TextView computeExtraLabel = (TextView) findViewById(R.id.extra_label);;
				computeExtraLabel.setText(selectedItem.name());
				TextView smallId = (TextView) findViewById(R.id.smallId);
				smallId.setText("ID: " + selectedItem.id());

				extraListItems = new String[6];
				extraListItems[0] = ("Min disk size: " + selectedItem.minDiskSize());
				extraListItems[1] = ("Min memory size: " + selectedItem.minMemorySize());
				extraListItems[2] = ("Min cpu size: " + selectedItem.minCpu());
				extraListItems[3] = ("Max disk size: " + selectedItem.maxDiskSize());
				extraListItems[4] = ("Max memory size: " + selectedItem.maxMemorySize());
				extraListItems[5] = ("Max cpu size: " + selectedItem.maxCpu());

				templateExtraListView = (ListView) findViewById(R.id.list_extra);
				templateExtraListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, extraListItems));
			}
		}
	}
	private void CreateTemplateManager() {

		templateManager = UI_Core.getCore().templateManager();
		boolean itemsLoaded = templateManager.load();
		if (TemplateActivity.back==1){
			selectedItem = templateManager.item((int) TemplateActivity.selectedItemID);
		}
		else{
			selectedItem = (Template) UI_Core.core.searchManager().item((int) SearchActivity.selectedItemID);
		}	
	}

	public boolean onCreateOptionsMenu(Menu menu2) {

		MenuInflater inflater = getMenuInflater();

		inflater.inflate(R.menu.template_detail_actionbar, menu2);

		return true;

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
	private void create_compute() {

		setContentView(R.layout.create_vm);
		editPage=1;
		CreateTemplateManager();
		final core.models.Template selectedItem = templateManager.item((int) TemplateActivity.selectedItemID);
		templateManager.details(selectedItem);

		TextView nameText = (TextView) findViewById(R.id.create_vm_label);
		TextView tempText = (TextView) findViewById(R.id.textView9);
		final EditText nameEdit = (EditText) findViewById(R.id.editText1);
		final Spinner archEditSpinner = (Spinner) findViewById(R.id.spinnerArch);
		final EditText coresEdit = (EditText) findViewById(R.id.editText3);
		final EditText cpuEdit = (EditText) findViewById(R.id.editText4);
		final EditText memoryEdit = (EditText) findViewById(R.id.editText5);
		final Spinner stateEditSpinner = (Spinner) findViewById(R.id.spinnerState);

		this.mAdapter = ArrayAdapter.createFromResource(this, R.array.state_array,
				android.R.layout.simple_spinner_item);
		stateEditSpinner.setAdapter(this.mAdapter);
		this.m2Adapter = ArrayAdapter.createFromResource(this, R.array.arch_array,
				android.R.layout.simple_spinner_item);
		archEditSpinner.setAdapter(this.m2Adapter);

		tempText.setText("Template : "+selectedItem.name());

		Button createCompute = (Button) findViewById(R.id.btn_create_vm);
		createCompute.setText("Create");
		createCompute.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				if ((!nameEdit.getText().toString().equals("")) && (!coresEdit.getText().toString().equals("")) && (!cpuEdit.getText().toString().equals("")) && (!memoryEdit.getText().toString().equals(""))){

					if (selectedItem.minCpu()<=Integer.parseInt(cpuEdit.getText().toString())  && selectedItem.maxCpu()>=Integer.parseInt(cpuEdit.getText().toString())){
						if (selectedItem.minMemorySize()<=Integer.parseInt(memoryEdit.getText().toString())  && selectedItem.maxMemorySize()>=Integer.parseInt(memoryEdit.getText().toString())){
							Compute newCompute = new Compute(0,nameEdit.getText().toString(), archEditSpinner.getSelectedItem().toString(), Integer.parseInt(coresEdit.getText().toString()), Float.parseFloat(cpuEdit.getText().toString()), Integer.parseInt(memoryEdit.getText().toString()), stateEditSpinner.getSelectedItem().toString(), selectedItem.name());
							computeManager.create(newCompute);
							Intent myIntent = new Intent(view.getContext(), ComputeActivity.class);
							startActivityForResult(myIntent, 0);


						}
						else{
							Context context = getApplicationContext();
							CharSequence text = "Memory size has to be between template minimum ("+selectedItem.minMemorySize()+") and maximum ("+selectedItem.maxMemorySize()+") size.";
							int duration = Toast.LENGTH_LONG;

							Toast toast = Toast.makeText(context, text, duration);
							toast.show();
						}
					}
					else{
						Context context = getApplicationContext();
						CharSequence text = "Cpu has to be between template minimum ("+selectedItem.minCpu()+") and maximum ("+selectedItem.maxCpu()+") size.";
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
	private void delete_template() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Are you sure you want to delete this item?")
		.setCancelable(false)
		.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				final Template selectedItem = templateManager.item((int) TemplateActivity.selectedItemID);
				templateManager.delete(selectedItem);
				Intent intent = new Intent(templateActivity, TemplateActivity.class);
				templateActivity.startActivity(intent);
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

	private void update_template() {
		setContentView(R.layout.update_template);
		CreateTemplateManager();
		editPage=1;

		final EditText minSizeEdit = (EditText) findViewById(R.id.editText2);
		final EditText minMemoryEdit = (EditText) findViewById(R.id.editText3);
		final EditText nameEdit = (EditText) findViewById(R.id.editText4);
		final EditText maxSizeEdit = (EditText) findViewById(R.id.editText5);
		final EditText maxMemoryEdit = (EditText) findViewById(R.id.editText6);
		final EditText minCpuEdit = (EditText) findViewById(R.id.editText1);
		final EditText maxCpuEdit = (EditText) findViewById(R.id.editText7);


		minSizeEdit.setText("" + selectedItem.minDiskSize());
		minMemoryEdit.setText("" + selectedItem.minMemorySize());
		nameEdit.setText("" + selectedItem.name());
		minCpuEdit.setText("" + selectedItem.minCpu());
		maxMemoryEdit.setText("" + selectedItem.maxMemorySize());
		maxCpuEdit.setText("" + selectedItem.maxCpu());
		maxSizeEdit.setText("" + selectedItem.maxDiskSize());

		Button updateTemplate = (Button) findViewById(R.id.btn_update_template);
		updateTemplate.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				if ((!minSizeEdit.getText().toString().equals("")) && (!minMemoryEdit.getText().toString().equals("") && (!nameEdit.getText().toString().equals("")))){
					try {
						Integer.parseInt(minSizeEdit.getText().toString());
						Integer.parseInt(minMemoryEdit.getText().toString());
						Integer.parseInt(minCpuEdit.getText().toString());
						Integer.parseInt(maxSizeEdit.getText().toString());
						Integer.parseInt(maxMemoryEdit.getText().toString());
						Integer.parseInt(maxCpuEdit.getText().toString());
						if (Integer.parseInt(minSizeEdit.getText().toString()) <= Integer.parseInt(maxSizeEdit.getText().toString()) && Integer.parseInt(minMemoryEdit.getText().toString())<= Integer.parseInt(maxMemoryEdit.getText().toString()) && Integer.parseInt(minCpuEdit.getText().toString())<= Integer.parseInt(maxCpuEdit.getText().toString())){
							Template newTemplate = new Template(selectedItem.id(),nameEdit.getText().toString(), Integer.parseInt(minSizeEdit.getText().toString()), Integer.parseInt(minMemoryEdit.getText().toString()), Integer.parseInt(minCpuEdit.getText().toString()), Integer.parseInt(maxSizeEdit.getText().toString()), Integer.parseInt(maxMemoryEdit.getText().toString()), Integer.parseInt(maxMemoryEdit.getText().toString()));
							templateManager.update(selectedItem, newTemplate);
							Intent myIntent = new Intent(view.getContext(), TemplateActivity.class);
							startActivityForResult(myIntent, 0);
						}
						else{
							Context context = getApplicationContext();
							CharSequence text = "Bad input min size has to be smaller than max size";
							int duration = Toast.LENGTH_LONG;

							Toast toast = Toast.makeText(context, text, duration);
							toast.show();
						}
					}
					catch (Exception e){
						try{
							Integer.parseInt(maxSizeEdit.getText().toString());
						}
						catch(Exception e1){
							Context context = getApplicationContext();
							CharSequence text = "Bad input field max disk size";
							int duration = Toast.LENGTH_LONG;

							Toast toast = Toast.makeText(context, text, duration);
							toast.show();
						}
						try{
							Integer.parseInt(maxMemoryEdit.getText().toString());
						}
						catch(Exception e1){
							Context context = getApplicationContext();
							CharSequence text = "Bad input field max memory size";
							int duration = Toast.LENGTH_LONG;

							Toast toast = Toast.makeText(context, text, duration);
							toast.show();
						}
						try{
							Integer.parseInt(maxCpuEdit.getText().toString());
						}
						catch(Exception e1){
							Context context = getApplicationContext();
							CharSequence text = "Bad input field max cpu size";
							int duration = Toast.LENGTH_LONG;

							Toast toast = Toast.makeText(context, text, duration);
							toast.show();
						}
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
			SearchActivity.tempCheck = true;
			Intent intent = new Intent(this, SearchActivity.class);
			this.startActivity(intent);
			return false;
		}else{
			if(keyCode == KeyEvent.KEYCODE_BACK){
				if (TemplateActivity.back==1){
					Intent intent = new Intent(this, TemplateActivity.class);
					this.startActivity(intent);
				}
				else{
					Intent intent = new Intent(this, SearchActivity.class);
					this.startActivity(intent);
				}
				return false;
			}
			else{if(keyCode == KeyEvent.KEYCODE_MENU) {
				if(editPage==1) {
					return true;
				}
				return false;
			}
			return super.onKeyUp(keyCode, event); 
			}
		}
	}
}
