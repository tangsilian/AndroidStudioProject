package com.example.tools;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		startaccessactivity();
	}

	private void startaccessactivity() {
		// TODO Auto-generated method stub
		/*
		 * Intent intent=new Intent(this,InspectWechatFriendService.class);
		 * startService(intent); Toast.makeText(getApplicationContext(),
		 * "start sucess", 0).show();
		 */
		Intent intent = new Intent(
				android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS);
		startActivity(intent);
		Toast.makeText(this, "�ҵ����������ҡ���Ȼ�������񼴿�", Toast.LENGTH_LONG).show();
	}

}
