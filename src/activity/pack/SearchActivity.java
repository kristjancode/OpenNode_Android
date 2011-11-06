package activity.pack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

public class SearchActivity extends Activity {

    static boolean compCheck=false;
    static boolean netwCheck=false;
    static boolean storCheck=false;
    static boolean tempCheck=false;
    static boolean newCheck=false;

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