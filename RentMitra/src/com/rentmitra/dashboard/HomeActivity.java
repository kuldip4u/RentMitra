package com.rentmitra.dashboard;

import java.util.ArrayList;
import java.util.Arrays;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rentmitra.R;
import com.rentmitra.Singleton;
import com.widget.HeaderLayout;

public class HomeActivity extends FragmentActivity implements OnClickListener{
	
	private ListView mSlideLV;
	private static DrawerLayout mDrawerLayout;
	HeaderLayout mHeaderLayout;
	private FragmentManager mFragmentManager;
	private FragmentTransaction mFragmentTransaction;
	private Intent intent;
	private ImageView mCloseIv, mUserProfileSliderIV;
	private TextView mUserNameSliderTV, mUserStatusSliderTV;
	
	
	boolean doubleBackToExitPressedOnce = false;

	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.home_activity);
		
		mFragmentManager = getSupportFragmentManager();
		controllInitialization();
		
	}
	
	private void controllInitialization() {
		mSlideLV = (ListView) findViewById(R.id.left_drawer);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		mHeaderLayout = new HeaderLayout(this);
	
		setLeftNavigationSlider();
		//setHeader(R.drawable.icon_menu, getString(R.string.app_name), R.drawable.btn_blue_plus_normal);
		mHeaderLayout.setListenerITI(this, null, this);
	
		
		/*mFragmentManager = getSupportFragmentManager();
		mFragmentTransaction = mFragmentManager.beginTransaction();
		CardListFragment baseFragment = new CardListFragment();
		mFragmentTransaction.add(R.id.container_fragment, baseFragment);
		mFragmentTransaction.commit();*/
		
	}

	/** .......///.........LISTENERS.........//..... */
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {/*
		super.onActivityResult(requestCode, resultCode, data);
		
		switch (requestCode) {
		case AddCardActivity.CATEGORY:
			
			if(null != data && data.getExtras() != null){
				Category mCategory = (Category)data.getExtras().getSerializable("CATEGORY");
				CardListFragment baseFragment = new CardListFragment();
				Bundle bundle = new Bundle();
				bundle.putSerializable("Category", mCategory);
				baseFragment.setArguments(bundle);
				mFragmentTransaction.replace(R.id.container_fragment, baseFragment);
				mFragmentTransaction.commit();
			}
			else
				setFragment(0);
			break;

		default:
			break;
		}
		
	*/}
	
	@Override
	public void onBackPressed() {

	    if (doubleBackToExitPressedOnce) {
	        super.onBackPressed();
	        return;
	    }

	    this.doubleBackToExitPressedOnce = true;
	    Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

	    new Handler().postDelayed(new Runnable() {

	        @Override
	        public void run() {
	            doubleBackToExitPressedOnce=false;                       
	        }
	    }, 2000);
		
	}
	
	@Override
	public void onClick(View v) {
		
		
		switch (v.getId()) {
		
		case R.id.img_left:
			Singleton.hideSoftKeyboard(v);
			v.postDelayed(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					sliderListener();		
				}
			}, 200);
		
			break;

		case R.id.img_right:
			/*Singleton.hideSoftKeyboard(v);
			intent = new Intent(HomeActivity.this, AddCardActivity.class);
			startActivity(intent);*/
			break;	
			
		default:
			break;
		}
	}
	
	
	public void setHeader(int left, String heading, int right){
		mHeaderLayout.setHeaderITI(left, heading, right);
	}

	private void setLeftNavigationSlider() {

		/*int width = (int) (getResources().getDimension(R.dimen.space_mid10) * 16);
		ListView.LayoutParams rlp = new ListView.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, width);
		View header = (View) getLayoutInflater().inflate(R.layout.user_info_view, null);
		header.setLayoutParams(rlp);
		
		String[] sliderOptions = new String[]{"Home", "About Owner", "Manage Category"};
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mSlideLV = (ListView) findViewById(R.id.left_drawer);
        mSlideLV.addHeaderView(header, null, false);
        
        OptionAdapter optionAdapter = new OptionAdapter(this);
        mSlideLV.setAdapter(optionAdapter);
        List<String> list = Arrays.asList(sliderOptions); 
        optionAdapter.setList(list);
        
		mSlideLV.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
							mDrawerLayout.closeDrawers();
						}
					}
				}, 300);
				setFragment(position);
			}
		});	
	*/
	
		int width = (int) (getResources().getDimension(R.dimen.space_mid10) * 16);
		ListView.LayoutParams rlp = new ListView.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, width);
		View header = (View) getLayoutInflater().inflate(R.layout.user_info_view, null);
		mCloseIv = (ImageButton) header.findViewById(R.id.search_ib);
		mCloseIv.setOnClickListener(this);

		header.setLayoutParams(rlp);
		String[] sliderOptions = new String[]{"Properties", "Maintenance", "Payment", "Invites", "Edit Profile"};
		
		ArrayList<String> sliderOptionsAL = new ArrayList<String>(Arrays.asList(sliderOptions));
		OptionAdapter optionAdapter = new OptionAdapter(this);
		mSlideLV.addHeaderView(header, null, false);
		mSlideLV.setAdapter(optionAdapter);
		mSlideLV.setOnItemClickListener(NavigationDrawerItemClickListener);
		mUserNameSliderTV 	= (TextView) header.findViewById(R.id.user_name_tv);
		mUserStatusSliderTV = (TextView) header.findViewById(R.id.user_type_tv);
		mUserProfileSliderIV= (ImageView) header.findViewById(R.id.profile_image_iv);
	
	}
	

	OnItemClickListener NavigationDrawerItemClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			setSliderItem(position);
		}
	};
	
	OnClickListener SliderListener = new OnClickListener() {


		@Override
		public void onClick(View v) {
			v.setBackgroundColor(getResources().getColor(R.color.separator));
			sliderListener();
		}
	};
	
	private void setSliderItem(int position) {
		if (position >= 0) {
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
						mDrawerLayout.closeDrawers();
					}
				}
			}, 300);
			switchFragment(position,"");
		}
	}
	
	public static void sliderListener() {
		if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
			mDrawerLayout.closeDrawers();
		} else {
			mDrawerLayout.openDrawer(GravityCompat.START);
		}
	}
	
	public void switchFragment(int position, String str){
		mFragmentManager = getSupportFragmentManager();
		mFragmentTransaction = mFragmentManager.beginTransaction();
		
		switch (position) {/*
		case 1:
			CardListFragment baseFragment = new CardListFragment();
			mFragmentTransaction.replace(R.id.container_fragment, baseFragment);
			mFragmentTransaction.commit();
			break;

		case 2:
			AboutFragment aboutFragment = new AboutFragment();
			mFragmentTransaction.replace(R.id.container_fragment, aboutFragment);
			mFragmentTransaction.commit();
			
			break;
			
		case 3:
			Intent mIntent = new Intent(this, CategoryActivity.class);
			startActivityForResult(mIntent, AddCardActivity.CATEGORY);// Activity is started with requestCode 2  
			break;
		
		*/}
	} 

	
	

	
}
