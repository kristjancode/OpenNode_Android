package activity.pack;

import core.interfaces.TemplateManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class TemplateActivity extends Activity {
	private ListView templateListView;
	@Override
	public void onCreate(final Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.template);
		TemplateManager templateManager = UI_Core.getCore().templateManager();
		boolean itemsLoaded = templateManager.loadItems();	//It seems to be unused, but it´s really for getting new data.
		int elementsIntemplateList = templateManager.itemCount();	
		core.models.Template templateList[] = new core.models.Template[elementsIntemplateList];
		String listItems[] = new String[elementsIntemplateList];
		for (int counter=0; counter<elementsIntemplateList; counter++){
			templateList[counter]=templateManager.item(counter);
			listItems[counter] = templateList[counter].name();
		}
		templateListView = (ListView) findViewById(R.id.list_templates);
		templateListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1 , listItems));
		templateListView.setOnItemClickListener( new AdapterView.OnItemClickListener() {  
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					Intent intent = new Intent(arg1.getContext(), MainActivity.class);  //No menu yet!
					startActivity(intent);
				}  
		      });  
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