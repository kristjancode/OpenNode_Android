package activity.pack;

import java.net.MalformedURLException;
import java.net.URL;

import activity.pack.R;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	public static final String PREFS_NAME = "MyPrefsFile";
	private static final String PREF_URL = "url";
	private static final String PREF_USERNAME = "username";
	private static final String PREF_PASSWORD = "password";

	@Override
	public void onCreate(final Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.login);
		SharedPreferences pref = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);  
		final String username = pref.getString(PREF_USERNAME, null);
		final String password = pref.getString(PREF_PASSWORD, null);
		final String url = pref.getString(PREF_URL, null);
		final EditText username2 = (EditText) findViewById(R.id.et_login_username);
		final EditText password2 = (EditText) findViewById(R.id.et_login_password);
		final EditText url2 = (EditText) findViewById(R.id.et_login_url);
		username2.setText(username);
		password2.setText(password);
		url2.setText(url);



		Button login = (Button) findViewById(R.id.btn_login_login);
		login.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				//try {
					//URL urlReal = new URL(url2.getText().toString());
				//} catch (MalformedURLException e) {
				//	e.printStackTrace();
				//}
				CheckBox password_remembered = (CheckBox) findViewById(R.id.password_remembered);
				password_remembered.hasSelection();
				if (username == null || password == null || url == null || username2.getText().toString() != username) {
					if (password_remembered.isChecked()){
						getSharedPreferences(PREFS_NAME,MODE_PRIVATE)
				        .edit()
				        .putString(PREF_URL, url2.getText().toString())
				        .putString(PREF_USERNAME, username2.getText().toString())
				        .putString(PREF_PASSWORD, password2.getText().toString())
				        .commit();
					}
					else{
						getSharedPreferences(PREFS_NAME,MODE_PRIVATE)
				        .edit()
				        .putString(PREF_URL, url2.getText().toString())
				        .putString(PREF_USERNAME, username2.getText().toString())
				        .putString(PREF_PASSWORD, null)
				        .commit();
					}
				}			

				
				Intent myIntent = new Intent(view.getContext(),
						MainActivity.class);
				startActivityForResult(myIntent, 0);
			}
		});
	}

	public boolean onCreateOptionsMenu(Menu menu2) {

		//MenuInflater inflater = getMenuInflater();

		//inflater.inflate(R.menu.actionbar, menu2);

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
	public boolean onKeyUp(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	        moveTaskToBack(true);
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
       }

}
