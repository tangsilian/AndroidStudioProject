package com.example.uitl;

import com.example.testweixin.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ListView;

public class MyListView extends ListView implements OnTouchListener, OnGestureListener {

	private GestureDetector gestureDetector;

	private OnDeleteListener listener;

	private View deleteButton;

	private ViewGroup itemLayout;

	private int selectedItem;

	private boolean isDeleteShown;

	public MyListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		gestureDetector = new GestureDetector(getContext(), this);
		setOnTouchListener((OnTouchListener) this);
	}

	public void setOnDeleteListener(OnDeleteListener l) {
		listener = l;
	}

	public boolean onTouch(View v, MotionEvent event) {
		/*
		 * if (isDeleteShown) { itemLayout.removeView(deleteButton);
		 * deleteButton = null; isDeleteShown = false; return false; } else {
		 * return gestureDetector.onTouchEvent(event); }
		 */

		return gestureDetector.onTouchEvent(event);

	}

	@Override
	public boolean onDown(MotionEvent e) {
		if (!isDeleteShown) {
			selectedItem = pointToPosition((int) e.getX(), (int) e.getY());
		}
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		if (!isDeleteShown && Math.abs(velocityX) > Math.abs(velocityY)) {

		}
		return false;
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {

	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		Dialog dialog = new AlertDialog.Builder(getContext()).setView(R.layout.dialog).create();
		dialog.show();

	}

	public interface OnDeleteListener {

		void onDelete(int index);

	}

}