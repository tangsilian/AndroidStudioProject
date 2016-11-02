package com.example.tools;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListViewActivity extends Activity {
	private ListView listview;
	private String[] data = null;
	private ArrayAdapter<String> adapter;
	private ArrayList<String> list = new ArrayList<String>();
	private String[] array;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listviewtest);
		listview = (ListView) findViewById(R.id.listview);
		PackageManager packageManager = this.getPackageManager();
		List<PackageInfo> packageInfoList = packageManager
				.getInstalledPackages(0);

		for (PackageInfo packageInfo : packageInfoList) {
			list.add(packageInfo.packageName);
		}
		int size = list.size();
		array = new String[size];
		for (int i = 0; i < size; i++) {
			array[i] = list.get(i);
		}
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, array);
		listview.setAdapter(adapter);
	}

	public void getAllApp(Context context) {
		PackageManager packageManager = context.getPackageManager();
		List<PackageInfo> packageInfoList = packageManager
				.getInstalledPackages(0);

		for (PackageInfo packageInfo : packageInfoList) {
			list.add(packageInfo.packageName);
		}
		int size = list.size();
		array = new String[size];
		for (int i = 0; i < size; i++) {
			array[i] = list.get(i);
		}
		// list.toArray();
	}
}
