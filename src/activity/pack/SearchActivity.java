package activity.pack;

import core.interfaces.SearchManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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
				searchList = new core.models.Item[ItemsInsearchList];
				for (int counter=0; counter<ItemsInsearchList; counter++){
					searchList[counter]= searchManager.item(counter);
					listItems[counter] = searchList[counter].name();
				}
				searchListView = (ListView) findViewById(R.id.search_list);
				searchListView.setAdapter(new ArrayAdapter<String>(searchActivity, android.R.layout.simple_list_item_1 , listItems));
				//registerForContextMenu(searchListView);
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