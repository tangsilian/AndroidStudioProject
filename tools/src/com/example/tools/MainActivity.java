package com.example.tools;

import com.example.tools.services.InspectWechatFriendService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
		Intent intent=new Intent(this,InspectWechatFriendService.class);
		startService(intent);
		Toast.makeText(getApplicationContext(), "start sucess", 0).show();
	}


}
