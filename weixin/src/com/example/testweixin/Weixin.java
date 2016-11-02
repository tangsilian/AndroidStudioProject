package com.example.testweixin;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.example.fragment.ChatmainFragement;
import com.example.fragment.ContactmainFragement;
import com.example.fragment.FindmainFragement;
import com.example.fragment.MyFragement;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechUtility;
import com.jauker.widget.BadgeView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Weixin extends FragmentActivity implements OnClickListener {
	public ViewPager viewpage;
	public FragmentPagerAdapter mAdapter;
	public List<Fragment> mFragments;
	private TextView chatText;
	private TextView contactText;
	private TextView findText;
	private TextView myText;
	private BadgeView badgeview;
	private LinearLayout linerlayout;
	private int mCurrentPageIndex;
	private ImageButton mImgWeixin;
	private ImageButton mImgFrd;
	private ImageButton mImgAddress;
	private ImageButton mImgSettings;
	private View home1;
	private View home2;
	private View home3;
	private View home4;
	private View menubar;

	/*
	 * Handler handler = new Handler() { public void handleMessage(Message msg)
	 * {
	 * 
	 * if(msg.what==1){text1.setText("1");}
	 * 
	 * };
	 * 
	 * };
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		home1 = findViewById(R.id.id_wechat);
		home2 = findViewById(R.id.id_contact);
		home3 = findViewById(R.id.id_find);
		home4 = findViewById(R.id.id_my);
		menubar =findViewById(R.id.menubar);
		linerlayout = (LinearLayout) findViewById(R.id.id_ll);
		home1.setOnClickListener(this);
		home2.setOnClickListener(this);
		home3.setOnClickListener(this);
		home4.setOnClickListener(this);
		menubar.setOnClickListener(this);
		// 初始化数据
		initView();
		// initData();
	}

	/*
	 * private void initData() { // TODO Auto-generated method stub
	 * 
	 * //处理比较耗时的操作
	 * 
	 * Message msg=new Message(); msg.what=1; handler.sendMessage(msg);
	 * 
	 * 
	 * 
	 * 
	 * }
	 */

	private void initView() {
		chatText = (TextView) findViewById(R.id.wechat);
		contactText = (TextView) findViewById(R.id.contact);
		findText = (TextView) findViewById(R.id.find);
		myText = (TextView) findViewById(R.id.my);
		viewpage = (ViewPager) findViewById(R.id.id_viewpager);
		mImgWeixin = (ImageButton) findViewById(R.id.id_tab_weixin_img);
		mImgFrd = (ImageButton) findViewById(R.id.id_tab_frd_img);
		mImgAddress = (ImageButton) findViewById(R.id.id_tab_address_img);
		mImgSettings = (ImageButton) findViewById(R.id.id_tab_settings_img);
		mFragments = new ArrayList();
		ChatmainFragement tab01 = new ChatmainFragement();
		ContactmainFragement tab02 = new ContactmainFragement();
		FindmainFragement tab03 = new FindmainFragement();
		MyFragement atb04 = new MyFragement();
		mFragments.add(tab01);
		mFragments.add(tab02);
		mFragments.add(tab03);
		mFragments.add(atb04);
		mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

			@Override
			public Fragment getItem(int arg0) {
				// TODO Auto-generated method stub
				return mFragments.get(arg0);
			}

			@Override
			public int getCount() {
				// TODO Auto-generated method stub

				return mFragments.size();
			}

		};
		viewpage.setAdapter(mAdapter);
		viewpage.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub
				resetTextView();
				switch (position) {
				case 0:
					if (badgeview != null) {

						linerlayout.removeView(badgeview);
					} else {
						badgeview = new BadgeView(getApplicationContext());
						badgeview.setBadgeCount(7);
						linerlayout.addView(badgeview);
					}
					chatText.setTextColor(Color.GREEN);
					mImgWeixin.setImageResource(R.drawable.tab_weixin_pressed);

					break;
				case 1:
					contactText.setTextColor(Color.GREEN);
					mImgFrd.setImageResource(R.drawable.tab_find_frd_pressed);
					break;
				case 2:
					findText.setTextColor(Color.GREEN);
					mImgAddress.setImageResource(R.drawable.tab_address_pressed);
					break;
				case 3:
					myText.setTextColor(Color.GREEN);
					mImgSettings.setImageResource(R.drawable.tab_settings_pressed);

					break;
				}

			}

			private void resetTextView() {
				// TODO Auto-generated method stub
				chatText.setTextColor(Color.BLACK);
				findText.setTextColor(Color.BLACK);
				contactText.setTextColor(Color.BLACK);
				myText.setTextColor(Color.BLACK);
				mImgWeixin.setImageResource(R.drawable.tab_weixin_normal);
				mImgFrd.setImageResource(R.drawable.tab_find_frd_normal);
				mImgAddress.setImageResource(R.drawable.tab_address_normal);
				mImgSettings.setImageResource(R.drawable.tab_settings_normal);
			}

		});

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.id_wechat:
			viewpage.setCurrentItem(0);
			break;
		case R.id.id_contact:
			viewpage.setCurrentItem(1);
			break;
		case R.id.id_find:
			viewpage.setCurrentItem(2);
			break;
		case R.id.id_my:
			viewpage.setCurrentItem(3);
			break;
		case R.id.menubar:
		    startActivity(new Intent(getApplicationContext(),DialogActivity.class));  
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		/*
		 * case R.id.create_new: Intent intent = new Intent(MainActivity.this,
		 * NextActivity.class); item.setIntent(intent); break; case R.id.open:
		 * Toast.makeText(MainActivity.this, "打开文件", 3).show(); break; case
		 * R.id.load: Toast.makeText(MainActivity.this, "加载文件", 3).show();
		 * break; case R.id.save: Toast.makeText(MainActivity.this, "保存文件",
		 * 3).show(); break;
		 */
		default:
			break;
		}
		return super.onMenuItemSelected(featureId, item);
	}

}
