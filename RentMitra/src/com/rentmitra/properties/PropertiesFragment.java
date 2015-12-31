package com.rentmitra.properties;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rentmitra.BaseFragment;
import com.rentmitra.R;
import com.rentmitra.invites.InviteFragment;
import com.rentmitra.maintenance.MaintenanceFragment;
import com.rentmitra.payment.PaymentFragment;

public class PropertiesFragment extends BaseFragment implements OnClickListener{

	private static PropertiesFragment mFragment;
	
	private TextView mAboutTv;
	private LinearLayout mEmailLl;
	private ViewPager mViewPager;
	private Button mPropertyBtn, mMaintenanceBtn, mPaymentBtn, mInviteBtn; 
	
	private Context mContext;
	
	public static BaseFragment getInstance(Context context) {
		if (null == mFragment) {
			mFragment = new PropertiesFragment();
		}
		return mFragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.properties_fragment, null);
		mContext = getActivity();
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		mPropertyBtn 	= (Button)view.findViewById(R.id.propertyBtn);
		mMaintenanceBtn = (Button)view.findViewById(R.id.maintanenceBtn);
		mPaymentBtn 	= (Button)view.findViewById(R.id.paymentBtn);
		mInviteBtn 		= (Button)view.findViewById(R.id.inviteBtn);
		
		mPropertyBtn.setOnClickListener(this);
		mMaintenanceBtn.setOnClickListener(this);
		mPaymentBtn.setOnClickListener(this);
		mInviteBtn.setOnClickListener(this);
		
		mViewPager 	= (ViewPager) view.findViewById(R.id.pager);
		mViewPager.setAdapter(new SampleFragmentPagerAdapter(getChildFragmentManager()));
		mPropertyBtn.setBackgroundColor(mContext.getResources().getColor(R.color.lt_green));
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int pos) {
				//Toast.makeText(getActivity(), ""+pos, Toast.LENGTH_SHORT).show();
				setTabEvent(pos);
				
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
	}
	
	
	/********* PAGER ADAPTER *************/

	
	class SampleFragmentPagerAdapter extends FragmentStatePagerAdapter{

		public SampleFragmentPagerAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int i) {
		
			if(i==0)
				return new PropertiesInnerFr();
			if(i==1)
				return new MaintenanceFragment();
			if(i==2)
				return new PaymentFragment();
			else
				return new InviteFragment();
		}

		@Override
		public int getCount() {
			return 4;
		}
	}


	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.propertyBtn:
			setTabEvent(0);
			break;

		case R.id.maintanenceBtn:
			setTabEvent(1);
			break;

		case R.id.paymentBtn:
			setTabEvent(2);
			break;

		case R.id.inviteBtn:
			setTabEvent(3);
			break;

		default:
			break;
		}
	}
	
	private void setTabEvent(int pos){
		
		mViewPager.setCurrentItem(pos);
		mPropertyBtn.setBackgroundColor(mContext.getResources().getColor(R.color.pink));
		mMaintenanceBtn.setBackgroundColor(mContext.getResources().getColor(R.color.pink));
		mPaymentBtn.setBackgroundColor(mContext.getResources().getColor(R.color.pink));
		mInviteBtn.setBackgroundColor(mContext.getResources().getColor(R.color.pink));
		
		switch (pos) {
			case 0:	mPropertyBtn.setBackgroundColor(mContext.getResources().getColor(R.color.lt_green));
					break;
			case 1:	mMaintenanceBtn.setBackgroundColor(mContext.getResources().getColor(R.color.lt_green));
					break;
			case 2:	mPaymentBtn.setBackgroundColor(mContext.getResources().getColor(R.color.lt_green));
					break;
			case 3:	mInviteBtn.setBackgroundColor(mContext.getResources().getColor(R.color.lt_green));
					break;
		}
	}
	
	/*class SampleFragmentPagerAdapter extends FragmentPagerAdapter {

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
	}*/
	
	

		/********* API Implementation *************/

}
