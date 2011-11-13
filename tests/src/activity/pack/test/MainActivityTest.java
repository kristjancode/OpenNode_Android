package activity.pack.test;

import com.jayway.android.robotium.solo.Solo;

import activity.pack.MainActivity;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.Smoke;
import android.view.KeyEvent;

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

	private Solo solo;

	public MainActivityTest() {
		super("activity.pack", MainActivity.class);
	}

	public void setUp() throws Exception {
		solo = new Solo(getInstrumentation(), getActivity());	
	}

	//This is for logging in before running other tests
	//Kind of workaround, as robotium doesn't support setting up first
	//Disgusting
	@Smoke
	public void testA(){
		if (!solo.searchText("(?i).*?Url:*")) {
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
	public void testMainNetwork(){
		//We check if we get all the items and they are responsive
		solo.assertCurrentActivity("Expected MainActivity", "MainActivity");
		solo.clickOnText("Network");
		solo.assertCurrentActivity("Expected NetworkActivity", "NetworkActivity");

	}

	@Smoke
	public void testMainCompute(){
		//We check if we get all the items and they are responsive
		solo.assertCurrentActivity("Expected MainActivity", "MainActivity");
		solo.clickOnText("Compute");
		solo.assertCurrentActivity("Expected ComputeActivity", "ComputeActivity");

	}

	@Smoke
	public void testMainStorage(){
		//We check if we get all the items and they are responsive
		solo.assertCurrentActivity("Expected MainActivity", "MainActivity");
		solo.clickOnText("Storage");
		solo.assertCurrentActivity("Expected StorageActivity", "StorageActivity");

	}

	@Smoke
	public void testMainTemplate(){
		//We check if we get all the items and they are responsive
		solo.assertCurrentActivity("Expected MainActivity", "MainActivity");
		solo.clickOnText("Template");
		solo.assertCurrentActivity("Expected TemplateActivity", "TemplateActivity");

	}

	@Smoke
	public void testMainActivitystream(){
		//We check if we get all the items and they are responsive
		solo.assertCurrentActivity("Expected MainActivity", "MainActivity");
		solo.clickOnText("(?i).*?Activity*");
		//To avoid timing issues
		solo.waitForActivity("Activity_StreamActivity",5000);
		solo.assertCurrentActivity("Expected Activity stream Activity", "Activity_StreamActivity");

	}

	@Smoke public void testSearchOnMainscreen(){
		solo.assertCurrentActivity("Expected MainActivity", "MainActivity");
		solo.sendKey(KeyEvent.KEYCODE_SEARCH);
		solo.waitForActivity("SearchActivity", 5000);
		solo.assertCurrentActivity("Expected Search Activity", "SearchActivity");
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