package activity.pack;

import core.interfaces.NewsManager;
import core.models.News;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Activity_StreamActivity extends Activity {
	private ListView newsListView;
	private ListView newsExtraListView;
	private String[] listItems;
	private String[] extraListItems;
	private long selectedItemID;
	private NewsManager newsManager;
	private core.models.News newsList[];
	private Menu menu;
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
			    	extra_info();
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
			extra_info();
			break;
		case R.id.make_comment:
			break;
		}
		return true;
	}


	private void extra_info() {
		setContentView(R.layout.extra);
		if (menu != null){
			invalidateOptionsMenu ();
		}
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
		menu = menu2;
		if (selectedItemID == -1){

		MenuInflater inflater = getMenuInflater();

		inflater.inflate(R.menu.news_list_actionbar, menu2);
		}
		else{
			MenuInflater inflater = getMenuInflater();

			inflater.inflate(R.menu.news_detail_actionbar, menu2);
		}
		return true;

	}
	public void invalidateOptionsMenu (){
		menu.clear();
		if (selectedItemID == -1){

		MenuInflater inflater = getMenuInflater();

		inflater.inflate(R.menu.news_list_actionbar, menu);
		}
		else{
			MenuInflater inflater = getMenuInflater();

			inflater.inflate(R.menu.news_detail_actionbar, menu);
		}
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
		if (menu != null){
			invalidateOptionsMenu ();
		}
		TextView computeExtraLabel = (TextView) findViewById(R.id.extra_label);
		News selectedItem = newsList[(int) selectedItemID];
		newsManager.details(selectedItem);
		computeExtraLabel.setText(selectedItem.name());
		newsManager.delete(selectedItem);
		Intent intent = new Intent(this, Activity_StreamActivity.class);
		this.startActivity(intent);
		
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