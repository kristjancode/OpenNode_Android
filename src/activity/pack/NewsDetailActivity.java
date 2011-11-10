package activity.pack;

import android.app.Activity;
import android.content.Context;
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
import core.interfaces.NetworkManager;
import core.interfaces.NewsManager;
import core.models.Network;
import core.models.News;
import core.models.Template;

public class NewsDetailActivity extends Activity {
	public NewsDetailActivity activity;
	private ListView newsExtraListView;
	private String[] extraListItems;
	private NewsManager newsManager;
	private News selectedItem;
	private ArrayAdapter<CharSequence> mAdapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.extra);
		newsManager = UI_Core.getCore().newsManager();
		//boolean itemsLoaded = newsManager.load();;
		if (Activity_StreamActivity.back==1){
			selectedItem = newsManager.item((int) Activity_StreamActivity.selectedItemID);
		}
		else{
			selectedItem = (News) UI_Core.core.searchManager().item((int) SearchActivity.selectedItemID);
		}
		TextView computeExtraLabel = (TextView) findViewById(R.id.extra_label);
		computeExtraLabel.setText(selectedItem.name());
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
		case R.id.newsEdit:
			edit_news();
			break;
		}
		return true;
	}
	private void edit_news() {
		setContentView(R.layout.edit_news);

		final Spinner typeSpinner = (Spinner) findViewById(R.id.newsTypeSpinner);
		final EditText titleEdit = (EditText) findViewById(R.id.newsTitleText);
		final EditText contentEdit = (EditText) findViewById(R.id.newsContentText);
		
		this.mAdapter = ArrayAdapter.createFromResource(this, R.array.type_array,
                android.R.layout.simple_spinner_item);
        typeSpinner.setAdapter(this.mAdapter);

        int i = mAdapter.getPosition(selectedItem.type());
        typeSpinner.setSelection(i);

		titleEdit.setText("" + selectedItem.name());
		contentEdit.setText("" + selectedItem.content());
		
		Button updateNews = (Button) findViewById(R.id.btn_news_edit);
		updateNews.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				if ((!titleEdit.getText().toString().equals("")) && (!contentEdit.getText().toString().equals(""))){
					News newNew = new News(selectedItem.id(), titleEdit.getText().toString(),typeSpinner.getSelectedItem().toString(), contentEdit.getText().toString());
					newsManager.update(selectedItem, newNew);
					Intent myIntent = new Intent(view.getContext(), Activity_StreamActivity.class);
					startActivityForResult(myIntent, 0);
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
         		if (Activity_StreamActivity.back==1){
 	     			Intent intent = new Intent(this, Activity_StreamActivity.class);
 	    			this.startActivity(intent);
         		}
         		else{
         			Intent intent = new Intent(this, SearchActivity.class);
         			this.startActivity(intent);
         		}
                    return false;
        	 }
        	 else{
                return super.onKeyUp(keyCode, event); 
        	 }
        }
}
}
