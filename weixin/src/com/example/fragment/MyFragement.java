package com.example.fragment;

import com.example.testweixin.R;
import com.example.uitl.RefreshableView;
import com.example.uitl.RefreshableView.PullToRefreshListener;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MyFragement extends Fragment {
	private ListView list1;
	private String[] Data = { "ÂÞÍú1", "ÂÞÍú2", "ÂÞÍú3", "ÂÞÍú4", "ÂÞÍú5", "ÂÞÍú6", "ÂÞÍú7", "ÂÞÍú8",
			 "ÂÞÍú9", "ÂÞÍú10", "ÂÞÍú11", "ÂÞÍú12",};
	private ArrayAdapter adapter;
	private RefreshableView refreshableView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_dropdown_item_1line, Data);
		View view = inflater.inflate(R.layout.tab04, container, false);
		list1 = (ListView) view.findViewById(R.id.list1);
		list1.setAdapter(adapter);
		refreshableView = (RefreshableView) view.findViewById(R.id.refreshable_view);
		refreshableView.setOnRefreshListener(new PullToRefreshListener() {
			@Override
			public void onRefresh() {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				refreshableView.finishRefreshing();
			}
		}, 0);

		return view;
	}
}
