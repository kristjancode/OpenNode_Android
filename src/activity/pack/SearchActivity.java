package activity.pack;

import core.Core;
import core.interfaces.SearchManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class SearchActivity extends Activity {
	private ListView searchListView;
	private String[] listItems;
	static long selectedItemID;
	private SearchManager searchManager;
	private core.models.Item searchList[];
    static boolean compCheck=false;
    static boolean netwCheck=false;
    static boolean storCheck=false;
    static boolean tempCheck=false;
    static boolean newCheck=false;
    Activity searchActivity = this;
    private int[] detailedInfo;
     
	@Override
	public void onCreate(final Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.search);
	    CheckBox computeCheck = (CheckBox) findViewById(R.id.computeCheck);
	    CheckBox networkCheck = (CheckBox) findViewById(R.id.networkCheck);
	    CheckBox storageCheck = (CheckBox) findViewById(R.id.storageCheck);
	    CheckBox templateCheck = (CheckBox) findViewById(R.id.templateCheck);
	    CheckBox newsCheck = (CheckBox) findViewById(R.id.newsCheck);
		computeCheck.setChecked(compCheck);
		networkCheck.setChecked(netwCheck);
		storageCheck.setChecked(storCheck);
		templateCheck.setChecked(tempCheck);
		newsCheck.setChecked(newCheck);
		backToFalse();
		
		Button search = (Button) findViewById(R.id.btn_Search_Search);
		search.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				EditText searchEditText = (EditText) findViewById(R.id.editText123);
				String searchText = searchEditText.getText().toString();
		        String[] array = searchText.split(" ");

				searchManager = UI_Core.getCore().searchManager();
				boolean itemsLoaded = searchManager.search(array);
				int ItemsInsearchList = searchManager.count();	
				listItems = new String[ItemsInsearchList];
				detailedInfo = new int[ItemsInsearchList];
				searchList = new core.models.Item[ItemsInsearchList];
				for (int counter=0; counter<ItemsInsearchList; counter++){
					searchList[counter]= searchManager.item(counter);
					listItems[counter] = searchList[counter].name();
					if (searchManager.item(counter) instanceof core.models.Compute){
						detailedInfo[counter]=1;
					}
					if (searchManager.item(counter) instanceof core.models.Network){
						detailedInfo[counter]=2;
					}
					if (searchManager.item(counter) instanceof core.models.Storage){
						detailedInfo[counter]=3;
					}
					if (searchManager.item(counter) instanceof core.models.Template){
						detailedInfo[counter]=4;
					}
					if (searchManager.item(counter) instanceof core.models.News){
						detailedInfo[counter]=5;
					}
				}
				searchListView = (ListView) findViewById(R.id.search_list);
				searchListView.setAdapter(new ArrayAdapter<String>(searchActivity, android.R.layout.simple_list_item_1 , listItems));
				//registerForContextMenu(searchListView);
				searchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {  				
					public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				    	selectedItemID = arg2;
				    	ComputeActivity.selectedItemID = arg2;
				    	NetworkActivity.selectedItemID = arg2;
				    	StorageActivity.selectedItemID = arg2;
				    	TemplateActivity.selectedItemID = arg2;
				    	Activity_StreamActivity.selectedItemID = arg2;
				    	switch (detailedInfo[arg2]){
				    	
				    	case 1:
							Intent myIntent = new Intent(arg1.getContext(),	ComputeDetailActivity.class);
							startActivityForResult(myIntent, 0);
				    		break;
				    	case 2:
							Intent myIntent2 = new Intent(arg1.getContext(),	NetworkDetailActivity.class);
							startActivityForResult(myIntent2, 0);
				    		break;
				    	case 3:
							Intent myIntent3 = new Intent(arg1.getContext(),	StorageDetailActivity.class);
							startActivityForResult(myIntent3, 0);
				    		break;
				    	case 4:
							Intent myIntent4 = new Intent(arg1.getContext(),	TemplateDetailActivity.class);
							startActivityForResult(myIntent4, 0);
				    		break;				  	
				    	case 5:
							Intent myIntent5 = new Intent(arg1.getContext(),	NewsDetailActivity.class);
							startActivityForResult(myIntent5, 0);
				    		break;
	
				    	}
				    	
					}  		
			      });  
			
				searchManager.resetFilters();
			}

		});

	}
	private void backToFalse() {
	    compCheck=false;
	    netwCheck=false;
	    storCheck=false;
	    tempCheck=false;
	    newCheck=false;

		
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