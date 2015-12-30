package com.rentmitra.properties;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rentmitra.BaseFragment;
import com.rentmitra.R;
import com.rentmitra.invites.InviteFragment;
import com.rentmitra.maintenance.MaintenanceFragment;
import com.rentmitra.payment.PaymentFragment;

public class PropertiesFragment extends BaseFragment {

	private static PropertiesFragment mFragment;
	
	private TextView mAboutTv;
	private LinearLayout mEmailLl;
	
	private ViewPager viewPager;

	
	public static BaseFragment getInstance(Context context) {
		if (null == mFragment) {
			mFragment = new PropertiesFragment();
		}
		return mFragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.properties_fragment, null);
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		viewPager 	= (ViewPager) view.findViewById(R.id.pager);
	}
	
	
	/********* PAGER ADAPTER *************/

	class SampleFragmentPagerAdapter extends FragmentPagerAdapter {

		SampleFragmentPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int i) {
//			return mTabsList.get(i).createFragment();
			if(i==0)
				return new PropertiesInnerFr();
			if(i==1)
				return new MaintenanceFragment();
			if(i==2)
				return new PaymentFragment();
			else
				return new InviteFragment();
//			return new MessageFragment();
		}

		@Override
		public int getCount() {
			return 4;
		}

		
		@Override
		public CharSequence getPageTitle(int position) {
			return "";
		}

	}
	
	

		/********* API Implementation *************/

}
