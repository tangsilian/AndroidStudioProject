package com.example.tools.getpackage;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.example.tools.R;

public class GetRunningActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void GetRunning(Context context) {

		// Iterator v1 = ((Object) context.getSystemService("activity"))
		// .getRunningTasks(200).iterator();
		// Object v0 = v1.next();

	}
}
