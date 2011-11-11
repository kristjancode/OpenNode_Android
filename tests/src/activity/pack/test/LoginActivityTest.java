package activity.pack.test;

import com.jayway.android.robotium.solo.Solo;

import activity.pack.LoginActivity;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.Smoke;

public class LoginActivityTest extends ActivityInstrumentationTestCase2<LoginActivity> {

	private Solo solo;

	public LoginActivityTest() {
		super("activity.pack", LoginActivity.class);
	}

	public void setUp() throws Exception {
		solo = new Solo(getInstrumentation(), getActivity());	
	}
	
	@Smoke
	public void testLogin() throws Exception {
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
