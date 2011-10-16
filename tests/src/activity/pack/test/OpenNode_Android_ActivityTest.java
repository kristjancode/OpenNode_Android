package activity.pack.test;

import activity.pack.MainActivity;

import android.test.ActivityInstrumentationTestCase2;

public class OpenNode_Android_ActivityTest extends
ActivityInstrumentationTestCase2<MainActivity> {

	public OpenNode_Android_ActivityTest() {
		super("activity.pack",MainActivity.class);
		// TODO Auto-generated constructor stub
	}
	public void testSanity() {
		assertEquals(2, 1 + 1);
	}

}
