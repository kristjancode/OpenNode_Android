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
import core.interfaces.NetworkManager;
import core.interfaces.NewsManager;
import core.models.Network;
import core.models.News;

public class NewsDetailActivity extends Activity {
	public NewsDetailActivity activity;
	private ListView newsExtraListView;
	private String[] extraListItems;
	private NewsManager newsManager;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.extra);
		newsManager = UI_Core.getCore().newsManager();
		//boolean itemsLoaded = newsManager.load();;
		
		News selectedItem = newsManager.item((int) Activity_StreamActivity.selectedItemID);
		TextView computeExtraLabel = (TextView) findViewById(R.id.extra_label);
		computeExtraLabel.setText(selectedItem.name());
		newsManager.details(selectedItem);
		computeExtraLabel.setText(selectedItem.name());
		TextView smallId = (TextView) findViewById(R.id.smallId);
		smallId.setText("ID : " + selectedItem.id());
		
		extraListItems = new String[3];
		extraListItems[0] = ("Type : " + selectedItem.type());
		extraListItems[1] = ("Title : " + selectedItem.name());
		extraListItems[2] = ("Content : " + selectedItem.content());
		
		newsExtraListView = (ListView) findViewById(R.id.list_extra);
		newsExtraListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, extraListItems));
		
			}
	public boolean onCreateOptionsMenu(Menu menu2) {

			MenuInflater inflater = getMenuInflater();

			inflater.inflate(R.menu.news_detail_actionbar, menu2);
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
			comment_news();
			break;
		}
		return true;
	}
	private void comment_news() {
		Context context = getApplicationContext();
		CharSequence text = "Functionality will be added in next version.";
		int duration = Toast.LENGTH_LONG;

		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
		
	}
	private void delete_news() {
		
		TextView computeExtraLabel = (TextView) findViewById(R.id.extra_label);
		News selectedItem = newsManager.item((int) Activity_StreamActivity.selectedItemID);
		newsManager.details(selectedItem);
		computeExtraLabel.setText(selectedItem.name());
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
     			Intent intent = new Intent(this, Activity_StreamActivity.class);
    			this.startActivity(intent);
                    return false;
        	 }
        	 else{
                return super.onKeyUp(keyCode, event); 
        	 }
        }
}
}
