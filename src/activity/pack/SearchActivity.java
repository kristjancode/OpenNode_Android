package activity.pack;

import core.Core;
import core.interfaces.SearchManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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
    			//imageView.setEnabled(false);
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
		        
				InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(searchEditText.getWindowToken(), 0);
				
				searchManager = UI_Core.getCore().searchManager();
				searchManager.resetFilters();
				if(computeCheck.isChecked()){searchManager.filterComputes(true);}
				if(networkCheck.isChecked()){searchManager.filterNetworks(true);}
				if(storageCheck.isChecked()){searchManager.filterStorages(true);}
				if(templateCheck.isChecked()){searchManager.filterTemplates(true);}
				if(newsCheck.isChecked()){searchManager.filterNews(true);}
				long start, end, elapsed;

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
				registerForContextMenu(searchListView);
				searchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {  				
					public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				    	selectedItemID = arg2;
				    	ComputeActivity.selectedItemID = arg2;
				    	NetworkActivity.selectedItemID = arg2;
				    	StorageActivity.selectedItemID = arg2;
				    	TemplateActivity.selectedItemID = arg2;
				    	Activity_StreamActivity.selectedItemID = arg2;
						if (searchManager.item(arg2) instanceof core.models.Compute){
							Intent myIntent = new Intent(arg1.getContext(),	ComputeDetailActivity.class);
							startActivityForResult(myIntent, 0);
						}
						if (searchManager.item(arg2) instanceof core.models.Network){
							Intent myIntent2 = new Intent(arg1.getContext(),	NetworkDetailActivity.class);
							startActivityForResult(myIntent2, 0);
						}
						if (searchManager.item(arg2) instanceof core.models.Storage){
							Intent myIntent3 = new Intent(arg1.getContext(),	StorageDetailActivity.class);
							startActivityForResult(myIntent3, 0);
						}
						if (searchManager.item(arg2) instanceof core.models.Template){
							Intent myIntent4 = new Intent(arg1.getContext(),	TemplateDetailActivity.class);
							startActivityForResult(myIntent4, 0);
						}
						if (searchManager.item(arg2) instanceof core.models.News){
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
		inflater.inflate(R.menu.compute_list_actionbar, menu2);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.home:
			Intent intent = new Intent(this, MainActivity.class);
			this.startActivity(intent);
			break;

		}
		return true;
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