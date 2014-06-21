package com.example.blooddonor;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

public class MainActivity extends FragmentActivity implements TabListener {
	ViewPager pager = null;
	FragmentManager manager = null;
	ActionBar actionbar = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		manager = getSupportFragmentManager();
		pager = (ViewPager) findViewById(R.id.pager);
		actionbar = getActionBar();
		// Mentioning android bar that there will tabs added to you.
		actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		pager.setAdapter(new Scrollpager(manager));
		pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				actionbar.setSelectedNavigationItem(arg0);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
		// Adding tabs
		ActionBar.Tab friendsTab = actionbar.newTab();
		friendsTab.setText("Friends");
		friendsTab.setTabListener(this);

		ActionBar.Tab meTab = actionbar.newTab();
		meTab.setText("Me");
		meTab.setTabListener(this);

		ActionBar.Tab searchTab = actionbar.newTab();
		searchTab.setText("Search");
		searchTab.setTabListener(this);

		actionbar.addTab(friendsTab);
		actionbar.addTab(meTab);
		actionbar.addTab(searchTab);
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		pager.setCurrentItem(tab.getPosition());

	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

}

class Scrollpager extends FragmentPagerAdapter {

	public Scrollpager(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int i) {
		// TODO Auto-generated method stub
		Fragment fragment = null;
		if (i == 0) {
			fragment = new Friends();
		} else if (i == 1) {
			fragment = new Me();
		} else if (i == 2) {
			fragment = new Search();
		}
		return fragment;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 3;
	}

}
