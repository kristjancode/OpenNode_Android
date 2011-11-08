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
    
    private class MyArrayAdapter<T> extends ArrayAdapter<T>
    {
        public MyArrayAdapter(Context context, int resource, int textViewResourceId, String[] listItems) {
            super(context, resource, textViewResourceId, (T[]) listItems);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = super.getView(position, convertView, parent);
            ImageView imageView = (ImageView) itemView.findViewById(R.id.icon);

    		if (searchManager.item(position) instanceof core.models.Compute){
	        		core.models.Compute selectedItem =(core.models.Compute) searchManager.item(position);
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
    		}
    		else{
    			imageView.setEnabled(false);
    			imageView.setImageResource(R.drawable.empty);
    			//imageView.setVisibility(false);
    		}
    		return itemView;
        }
    }
	@Override
	public void onCreate(final Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.search);
	    final CheckBox computeCheck = (CheckBox) findViewById(R.id.computeCheck);
	    final CheckBox networkCheck = (CheckBox) findViewById(R.id.networkCheck);
	    final CheckBox storageCheck = (CheckBox) findViewById(R.id.storageCheck);
	    final CheckBox templateCheck = (CheckBox) findViewById(R.id.templateCheck);
	    final CheckBox newsCheck = (CheckBox) findViewById(R.id.newsCheck);
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
				searchManager.resetFilters();
				if(computeCheck.isChecked()){searchManager.filterComputes(true);}
				if(networkCheck.isChecked()){searchManager.filterNetworks(true);}
				if(storageCheck.isChecked()){searchManager.filterStorages(true);}
				if(templateCheck.isChecked()){searchManager.filterTemplates(true);}
				if(newsCheck.isChecked()){searchManager.filterNews(true);}
				
				boolean itemsLoaded = searchManager.search(array);
				int ItemsInsearchList = searchManager.count();	
				listItems = new String[ItemsInsearchList];
				searchList = new core.models.Item[ItemsInsearchList];
				for (int counter=0; counter<ItemsInsearchList; counter++){
					searchList[counter]= searchManager.item(counter);
					listItems[counter] = searchList[counter].name();
				}
				searchListView = (ListView) findViewById(R.id.search_list);
				searchListView.setAdapter(new MyArrayAdapter<String>(searchActivity,R.layout.row , R.id.computeRow, listItems));
				//registerForContextMenu(searchListView);
				searchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {  				
					public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				    	selectedItemID = arg2;
				    	ComputeActivity.selectedItemID = arg2;
				    	NetworkActivity.selectedItemID = arg2;
				    	StorageActivity.selectedItemID = arg2;
				    	TemplateActivity.selectedItemID = arg2;
				    	Activity_StreamActivity.selectedItemID = arg2;
						if (searchManager.item(arg2) instanceof core.models.Compute){
							searchList[arg2]=(core.models.Compute) searchManager.item(arg2);
							Intent myIntent = new Intent(arg1.getContext(),	ComputeDetailActivity.class);
							startActivityForResult(myIntent, 0);
						}
						if (searchManager.item(arg2) instanceof core.models.Network){
							searchList[arg2]=(core.models.Network) searchManager.item(arg2);
							Intent myIntent2 = new Intent(arg1.getContext(),	NetworkDetailActivity.class);
							startActivityForResult(myIntent2, 0);
						}
						if (searchManager.item(arg2) instanceof core.models.Storage){
							searchList[arg2]=(core.models.Storage) searchManager.item(arg2);
							Intent myIntent3 = new Intent(arg1.getContext(),	StorageDetailActivity.class);
							startActivityForResult(myIntent3, 0);
						}
						if (searchManager.item(arg2) instanceof core.models.Template){
							searchList[arg2]=(core.models.Template) searchManager.item(arg2);
							Intent myIntent4 = new Intent(arg1.getContext(),	TemplateDetailActivity.class);
							startActivityForResult(myIntent4, 0);
						}
						if (searchManager.item(arg2) instanceof core.models.News){
							searchList[arg2]=(core.models.News) searchManager.item(arg2);
							Intent myIntent5 = new Intent(arg1.getContext(),	NewsDetailActivity.class);
							startActivityForResult(myIntent5, 0);
						}
				    	

	
				    	
				    	
					}  		
			      });  
			
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