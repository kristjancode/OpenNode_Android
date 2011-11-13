package activity.pack.test;

import com.jayway.android.robotium.solo.Solo;

import activity.pack.NetworkActivity;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.Smoke;
import android.view.KeyEvent;

public class NetworkActivityTest extends ActivityInstrumentationTestCase2<NetworkActivity> {

	private Solo solo;

	public NetworkActivityTest() {
		super("activity.pack", NetworkActivity.class);
	}

	public void setUp() throws Exception {
		solo = new Solo(getInstrumentation(), getActivity());	
	}
	
	//This is for logging in before running other tests
	//Kind of workaround, as robotium doesn't support setting up first
	//Disgusting
	@Smoke
	public void testA(){
		if (solo.searchText("(?i).*?Network Devices*")) {
			solo.pressMenuItem(0);
			solo.waitForActivity("MainActivity",5000);
			solo.pressMenuItem(1);
			solo.waitForActivity("LoginActivity",5000);
		}
		solo.assertCurrentActivity("Expected LoginActivity", "LoginActivity");
		solo.clearEditText(0);
		solo.enterText(0,"10.0.2.2:8080");
		solo.clearEditText(1);
		solo.enterText(1, "opennode");
		solo.clearEditText(2);
		solo.enterText(2, "demo");
		if (!solo.isCheckBoxChecked(0)) {
			solo.clickOnCheckBox(0);
		}
		solo.clickOnButton(0);
		solo.assertCurrentActivity("Expected MainActivity","MainActivity");
	}

	@Smoke
	public void testNetworkList() {
		solo.assertCurrentActivity("Expected NetworkActivity", "NetworkActivity");
		//Assuming we are dealing with demoserver we should get plenty of networks
		solo.waitForText("(?i).*?network_6*", 1, 5000);
	}
	
	@Smoke
	public void testClickingOnItem() {
		solo.assertCurrentActivity("Expected NetworkActivity", "NetworkActivity");
		solo.waitForText("(?i).*?network_6*", 1, 5000);
		solo.clickOnText("(?i).*?network_6*");
		//We may have to wait a second
		solo.waitForActivity("NetworkDetailActivity",5000);
		solo.assertCurrentActivity("Expected NetworkDetailActivity", "NetworkDetailActivity");
	}

	@Smoke
	public void testGoingBackToMain() {
		solo.assertCurrentActivity("Expected NetworkActivity", "NetworkActivity");
		solo.pressMenuItem(0);
		solo.waitForActivity("MainActivity",5000);
		solo.assertCurrentActivity("Expected MainActivity", "MainActivity");
	}
	
	@Smoke
	public void testSearchButton() {
		solo.assertCurrentActivity("Expected NetworkActivity", "NetworkActivity");
		solo.sendKey(KeyEvent.KEYCODE_SEARCH);
		solo.waitForActivity("SearchActivity", 5000);
		solo.assertCurrentActivity("Expected Search Activity", "SearchActivity");
		//assertTrue("Expected Network checkbox to be checked on search",solo.isCheckBoxChecked(1));
		//Don't know where checkboxes are, so we just count
		int checkboxes=0;
		//Check if other boxes are empty
		for (int i=0;i<5;i++){
			/*
			if (i!=1) 
				assertTrue("Expected other checkboxes to be empty",solo.isCheckBoxChecked(i));
			*/
			if (!solo.isCheckBoxChecked(i))
				checkboxes+=1;
		}
		assertTrue("Should be 4 checkboxes unchecked",checkboxes==4);
	}
	
	@Smoke
	public void testLongPress() {
		solo.assertCurrentActivity("Expected NetworkActivity", "NetworkActivity");
		solo.waitForText("(?i).*?network_6*", 1, 5000);
		solo.clickLongOnText("(?i).*?network_6*");
		solo.waitForText("(?i).*?Details*",1,2500);
		assertTrue("Didn't get menu with Details option",solo.searchText("(?i).*?Details*"));
		solo.clickOnText("(?i).*?Details*");
		solo.waitForActivity("NetworkDetailActivity",5000);
		solo.assertCurrentActivity("Expected NetworkDetailActivity", "NetworkDetailActivity");
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