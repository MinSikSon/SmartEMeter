/*
 about (v3.1) : combined chart at ChartYear.java
 ref source) http://www.javaadvent.com/2012/12/achartengine-charting-library-for.html
 ref) http://www.programkr.com/blog/MQDN0ADMwYT3.html
 ref) http://www.achartengine.org/content/javadoc/index.html
 display) width 480, 720, 1080
*/
// http://android.codeandmagic.org/android-gaugeview-library/

package son.funkydj3.smartemeter.achartengine;

import son.funkydj3.smartemeter.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Chart extends FragmentActivity implements OnClickListener  {
	private static final int NUMBER_OF_PAGERS = 3;
	public static ViewPager mViewPager;
	private MyFragmentPagerAdapter mMyFragmentPagerAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chart);
		init();
		mViewPager = (ViewPager) findViewById(R.id.viewpager);
		mMyFragmentPagerAdapter = new MyFragmentPagerAdapter(
				getSupportFragmentManager());
		mViewPager.setAdapter(mMyFragmentPagerAdapter);
	}
	
	private Button btn_main_1;
	private Button btn_main_2;
	private Button btn_main_3;
	private void init() {
		btn_main_1 = (Button) findViewById(R.id.btn_Chart1);
		btn_main_2 = (Button) findViewById(R.id.btn_Chart2);
		btn_main_3 = (Button) findViewById(R.id.btn_Chart3);

		// ImageButton default state
		btn_main_1.setOnClickListener(this);
		btn_main_2.setOnClickListener(this);
		btn_main_3.setOnClickListener(this);
	}
	
	private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

		public MyFragmentPagerAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int index) {
			// TODO Auto-generated method stub
			if (index == 0) {
				// return PageFragment_Create.newInstance("My Message " + index);
				return ChartYear.newInstance("MONTHLY Electric Charge");
			} else if (index == 1) {
				return ChartMonth.newInstance("DAILY Electric Charge");
			} else {
				return ChartDay.newInstance("HOURLY Electric Charge");
			}
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return NUMBER_OF_PAGERS;
		}

		@Override
		public void startUpdate(View arg0) {
			Log.d("SON", "startUpdate");
		}

		@Override
		public void finishUpdate(View arg0) {
			Log.d("SON", "finishUpdate");
		}
		

	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.btn_Chart1)
			setCurrentInflateItem(0);
		else if (v.getId() == R.id.btn_Chart2)
			setCurrentInflateItem(1);
		else if (v.getId() == R.id.btn_Chart3)
			setCurrentInflateItem(2);
	}
	
	private void setCurrentInflateItem(int type) {
		Log.d("Main", "private void setCurrentInflateItem(int type)");

		if (type == 0) {
			mViewPager.setCurrentItem(0);
		} else if (type == 1) {
			mViewPager.setCurrentItem(1);
		} else if (type == 2) {
			mViewPager.setCurrentItem(2);
		}
	}
	
	
}
