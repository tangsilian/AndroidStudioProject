package com.example.fragment;

import java.util.ArrayList;

import java.util.List;


import com.example.testweixin.R;
import com.example.uitl.MyAdapter;
import com.example.uitl.MyListView;
import com.example.uitl.MyListView.OnDeleteListener;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
public class ChatmainFragement extends Fragment {
	
	private MyListView myListView;
	private View view;
	private MyAdapter adapter;

	private List<String> contentList = new ArrayList<String>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.tab01, container, false);
		initList();
		 
		myListView = (MyListView) view.findViewById(R.id.my_list_view);
		 registerForContextMenu(myListView);
		myListView.setOnDeleteListener(new OnDeleteListener() {
			@Override
			public void onDelete(int index) {
				contentList.remove(index);
				adapter.notifyDataSetChanged();
			}
		});

		adapter = new MyAdapter(getActivity(), 0, contentList);
		myListView.setAdapter(adapter);
		return view;
	}
/*@Override
public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
	// TODO Auto-generated method stub
	super.onCreateContextMenu(menu, v, menuInfo);
	.getMenuInflater().inflate(R.menu.main, menu);
}*/
	private void initList() {
		contentList.add("uzi1");
		contentList.add("uzi2");
		contentList.add("uzi3");
		contentList.add("uzi4");
		contentList.add("uzi5");
		contentList.add("uzi6");
		contentList.add("uzi7");
		contentList.add("uzi8");
		contentList.add("uzi9");
		contentList.add("uzi10");
		contentList.add("uzi11");
		contentList.add("uzi12");
		contentList.add("uzi13");
		contentList.add("uzi14");
		contentList.add("uzi15");

	}

}
