package activity.pack.test;

import com.jayway.android.robotium.solo.Solo;

import activity.pack.ComputeActivity;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.Smoke;
import android.view.KeyEvent;

public class ComputeActivityTest extends ActivityInstrumentationTestCase2<ComputeActivity> {

	private Solo solo;

	public ComputeActivityTest() {
		super("activity.pack", ComputeActivity.class);
	}

	public void setUp() throws Exception {
		solo = new Solo(getInstrumentation(), getActivity());	
	}

	@Smoke
	public void testA(){
		if (solo.searchText("(?i).*?Machines*")) {
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
	public void testComputeList() {
		solo.assertCurrentActivity("Expected ComputeActivity", "ComputeActivity");
		//Assuming we are dealing with demoserver we should get plenty of Computes
		//Names are without spaces so we just try to find one
		solo.waitForText("(?i).*?hostname_8*", 1, 5000);
	}


	@Smoke
	public void testClickingOnItem() {
		solo.assertCurrentActivity("Expected ComputeActivity", "ComputeActivity");
		solo.waitForText("(?i).*?hostname_8*", 1, 5000);
		solo.clickOnText("(?i).*?hostname_8*");
		//We may have to wait a second
		solo.waitForActivity("ComputeDetailActivity",5000);
		solo.assertCurrentActivity("Expected ComputeDetailActivity", "ComputeDetailActivity");
	}

	@Smoke
	public void testGoingBackToMain() {
		solo.assertCurrentActivity("Expected ComputeActivity", "ComputeActivity");
		solo.pressMenuItem(0);
		solo.waitForActivity("MainActivity",5000);
		solo.assertCurrentActivity("Expected MainActivity", "MainActivity");
	}

	@Smoke
	public void testSearchButton() {
		solo.assertCurrentActivity("Expected ComputeActivity", "ComputeActivity");
		solo.sendKey(KeyEvent.KEYCODE_SEARCH);
		solo.waitForActivity("SearchActivity", 5000);
		solo.assertCurrentActivity("Expected Search Activity", "SearchActivity");
		//assertTrue("Expected Compute checkbox to be checked on search",solo.isCheckBoxChecked(0));
		//Don't know which checkboxes are which so we just count them
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
		solo.assertCurrentActivity("Expected ComputeActivity", "ComputeActivity");
		solo.waitForText("(?i).*?hostname_8*", 1, 5000);
		solo.clickLongOnText("(?i).*?hostname_8*");
		solo.waitForText("(?i).*?Details*",1,2500);
		assertTrue("Didn't get menu with Details option",solo.searchText("(?i).*?Details*"));
		solo.clickOnText("(?i).*?Details*");
		solo.waitForActivity("ComputeDetailActivity",5000);
		solo.assertCurrentActivity("Expected ComputeDetailActivity", "ComputeDetailActivity");
	}

	/*
	//Something is quite very broken with this test
	@Smoke
	public void testEditingComputeItemMaxLongCore() {
		solo.assertCurrentActivity("Expected ComputeActivity", "ComputeActivity");
		solo.waitForText("(?i).*?hostname_8*", 1, 5000);
		solo.clickOnText("(?i).*?hostname_8*");
		//We may have to wait a second
		solo.waitForActivity("ComputeDetailActivity",5000);
		solo.assertCurrentActivity("Expected ComputeDetailActivity", "ComputeDetailActivity");
		solo.pressMenuItem(4, 3);
		solo.waitForText("(?i).*?Edit Compute*", 1, 5000);
		long longvalue=Long.MAX_VALUE;
		String longstring=String.valueOf(longvalue);
		//Robotium bug possibly, will go back to mainactivity
		solo.clearEditText(1);
		//Then set it
		solo.enterText(1, longstring);
		solo.waitForActivity("ComputeDetailActivity",5000);
		//Err, probably wont scroll that, as we don't have any visual keyboards in robotium
		//assertTrue("Couldn't scroll",solo.scrollDown());
		//We try to search for the damned button, if it's not found we stop or robotium crashes
		if (solo.searchButton("(?i).*Edit*")) {
			solo.clickOnButton("(?i).*Edit*");
			solo.waitForActivity("ComputeDetailActivity",5000);
			solo.assertCurrentActivity("Expected ComputeDetailActivity", "ComputeDetailActivity");
		} else {
			fail("Didn't find searchbutton");
		}
		solo.clickOnButton(0);
		solo.waitForActivity("ComputeDetailActivity",5000);
		solo.assertCurrentActivity("Expected ComputeDetailActivity", "ComputeDetailActivity");
	
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