package activity.pack.test;

import com.jayway.android.robotium.solo.Solo;

import activity.pack.LoginActivity;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.Smoke;
import android.view.KeyEvent;

public class LoginActivityTest extends ActivityInstrumentationTestCase2<LoginActivity> {

	private Solo solo;

	public LoginActivityTest() {
		super("activity.pack", LoginActivity.class);
	}

	public void setUp() throws Exception {
		solo = new Solo(getInstrumentation(), getActivity());	
	}

	@Smoke
	public void testCorrectLogin() throws Exception {
		solo.assertCurrentActivity("Expected LoginActivity", "LoginActivity");
		//Clear the hostname (field 0)
		solo.clearEditText(0);
		//Then set it
		solo.enterText(0,"10.0.2.2:8080");
		//Clear the username (field 1)
		solo.clearEditText(1);
		//Then set it
		solo.enterText(1, "opennode");
		//Clear the password
		solo.clearEditText(2);
		//Then set it
		solo.enterText(2, "demo");
		//Lets try to actually login (button 0 should be Login)
		solo.clickOnButton(0);
		//Check if we were redirected to MainActivity
		solo.assertCurrentActivity("Expected MainActivity","MainActivity");
	}

	@Smoke
	public void testIncorrectLogin() {
		solo.assertCurrentActivity("Expected LoginActivity", "LoginActivity");
		//Clear the hostname (field 0)
		solo.clearEditText(0);
		//Then set it
		solo.enterText(0,"10.0.2.2:8080");
		//Clear the username (field 1)
		solo.clearEditText(1);
		//Then set it
		solo.enterText(1, "opennode");
		//Clear the password
		solo.clearEditText(2);
		//Then set it
		solo.enterText(2, "bad_pass");
		//Lets try to actually login (button 0 should be Login)
		solo.clickOnButton(0);
		//Check if we see the toast (we regex the toastmessage, just in case)
		assertTrue("Didn't get the toast message with bad password",solo.waitForText("(?i).*?Authentication failed*", 1, 1000));
		//Check if we are still in LoginActivity
		solo.assertCurrentActivity("Expected LoginActivity","LoginActivity");
	}
	
	//Suppress for solo.MENU
	@SuppressWarnings("static-access")
	@Smoke
	public void testRememberLoginCredetianls() {
		solo.assertCurrentActivity("Expected LoginActivity", "LoginActivity");
		//Clear the hostname (field 0)
		solo.clearEditText(0);
		//Then set it
		solo.enterText(0,"10.0.2.2:8080");
		//Clear the username (field 1)
		solo.clearEditText(1);
		//Then set it
		solo.enterText(1, "opennode");
		//Clear the password
		solo.clearEditText(2);
		//Then set it
		solo.enterText(2, "demo");
		//Set the checkbox (0 as we only have one) to remember login
		if (!solo.isCheckBoxChecked(0)) {
			solo.clickOnCheckBox(0);
		}
		//Lets try to actually login (button 0 should be Login)
		solo.clickOnButton(0);
		//Check if we were redirected to MainActivity
		solo.assertCurrentActivity("Expected MainActivity","MainActivity");
		//Try to log out by first pressing MENU
		solo.sendKey(solo.MENU);
		//Lets find logout button
		assertTrue("Log out didn't appear",solo.waitForText("Log out", 1, 100));
		//solo.clickOnMenuItem("");
		solo.clickOnText("Log out");
		//Check if we are back on login screen
		solo.assertCurrentActivity("Expected LoginActivity", "LoginActivity");
		//Check if we can just click button relog
		solo.clickOnButton(0);
		//And Mainscreen
		solo.assertCurrentActivity("Expected MainActivity","MainActivity");
	}
	
	/*
	@Smoke
	public void testWithNoInfo() {
		//Clear all fields
		solo.assertCurrentActivity("Expected LoginActivity", "LoginActivity");
		for (int i=0;i<4;i++) {
			solo.clearEditText(i);
		}
		//And submit
		solo.clickOnButton(0);
		//Make sure we get failed toast and stay on same window
		assertTrue("Didn't get the toast message with bad password",solo.waitForText("(?i).*?Authentication failed*", 1, 1000));
		solo.assertCurrentActivity("Expected LoginActivity","LoginActivity");
		
	}
	*/

	@Override
	public void tearDown() throws Exception {
		try {
			//Robotium will finish all the activities that have been opened
			solo.finalize(); 	
		} catch (Throwable e) {
			e.printStackTrace();
		}
		getActivity().finish();
		super.tearDown();
	} 

}
