package activity.pack;

import core.interfaces.NewsManager;
import core.models.News;
import android.app.Activity;
import android.content.Context;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Activity_StreamActivity extends Activity {
	private ListView newsListView;
	private ListView newsExtraListView;
	private String[] listItems;
	private String[] extraListItems;
	static long selectedItemID;
	private NewsManager newsManager;
	private core.models.News newsList[];
	private Menu menu;
	static int back = 0;
	@Override
	public void onCreate(final Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.activity_stream);
		selectedItemID = -1;
		newsManager = UI_Core.getCore().newsManager();
		boolean itemsLoaded = newsManager.load();	//It seems to be unused, but it´s really for getting new data.
		int elementsInnewsList = newsManager.count();	
		newsList = new core.models.News[elementsInnewsList];
		listItems = new String[elementsInnewsList];
		ImageView search = (ImageView) findViewById(R.id.btn_computeSearch);
		search.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
	        	SearchActivity.newCheck = true;
				Intent myIntent = new Intent(view.getContext(),
						SearchActivity.class);
				startActivityForResult(myIntent, 0);
			}

		});
		for (int counter=0; counter<elementsInnewsList; counter++){
			newsList[counter]=newsManager.item(counter);
			listItems[counter] = newsList[counter].name();
		}
		newsListView = (ListView) findViewById(R.id.list_activity);
		newsListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1 , listItems));
		registerForContextMenu(newsListView);
		newsListView.setOnItemClickListener( new AdapterView.OnItemClickListener() {  				
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			    	selectedItemID = arg2;
			    	back = 1;
					Intent myIntent = new Intent(arg1.getContext(),	NewsDetailActivity.class);
					startActivityForResult(myIntent, 0);
				}  		
		      });  
	}
	
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.news_menu, menu);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
        selectedItemID = info.position;    
        menu.setHeaderTitle(listItems[(int) selectedItemID]);
      }
    public boolean onContextItemSelected(MenuItem item)  {
		switch (item.getItemId()) {
		case R.id.extra_info_news:
			Intent intent = new Intent(this, NewsDetailActivity.class);
			this.startActivity(intent);
			break;
		case R.id.make_comment:
			make_comment();
			break;
		case R.id.delete_news:
			delete_news();
			break;
		}
		return true;
	}


	private void make_comment() {
		Context context = getApplicationContext();
		CharSequence text = "Functionality will be added in next version.";
		int duration = Toast.LENGTH_LONG;

		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
		
	}

	private void extra_info() {
		setContentView(R.layout.extra);

		TextView computeExtraLabel = (TextView) findViewById(R.id.extra_label);
		News selectedItem = newsList[(int) selectedItemID];
		newsManager.details(selectedItem);
		computeExtraLabel.setText(selectedItem.name());
		
		extraListItems = new String[4];
		extraListItems[0] = ("ID : " + selectedItem.id());
		extraListItems[1] = ("Type : " + selectedItem.type());
		extraListItems[2] = ("Title : " + selectedItem.name());
		extraListItems[3] = ("Content : " + selectedItem.content());
		
		newsExtraListView = (ListView) findViewById(R.id.list_extra);
		newsExtraListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, extraListItems));
		
	}

	public boolean onCreateOptionsMenu(Menu menu2) {

		MenuInflater inflater = getMenuInflater();

		inflater.inflate(R.menu.news_list_actionbar, menu2);

		return true;

	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.home:
			Intent intent = new Intent(this, MainActivity.class);
			this.startActivity(intent);
			break;
		case R.id.delete:
			delete_news();
			break;
		case R.id.comment:
			break;
		}
		return true;
	}
	private void delete_news() {

		News selectedItem = newsList[(int) selectedItemID];
		newsManager.details(selectedItem);
		newsManager.delete(selectedItem);
		Intent intent = new Intent(this, Activity_StreamActivity.class);
		this.startActivity(intent);
		
	}

	public boolean onKeyUp(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_SEARCH){
        	SearchActivity.newCheck = true;
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