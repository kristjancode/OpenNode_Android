package activity.pack.test;

import activity.pack.MainActivity;

import android.test.ActivityInstrumentationTestCase2;

public class Activity extends
ActivityInstrumentationTestCase2<MainActivity> {

	public Activity() {
		super("activity.pack",MainActivity.class);
	}
	
	//Superbasic test to check if everything works
	public void testSanity() {
		assertEquals(2, 1 + 1);
	}

}
