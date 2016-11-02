package com.example.tools.utils;

import java.io.File;

import android.os.Environment;
import android.util.Log;

//用alt+shift+R修改所有相同的变量名
public class MyLogutils {
	private static final String DEFAULT_TAG = "MyLogutils";
	public static boolean LOG_ENABLE;
	private static String TAG;

	static {
		MyLogutils.LOG_ENABLE = true;
		MyLogutils.TAG = "AmazingLog";
	}

	public MyLogutils() {
		super();
	}

	public static void d(String msg) {
		if (MyLogutils.LOG_ENABLE) {
			Log.d(MyLogutils.TAG, msg);
		}
	}

	public static void d(String tag, String msg) {
		if (MyLogutils.LOG_ENABLE) {
			Log.d(tag, msg);
		}
	}

	public static void e(String msg) {
		if (MyLogutils.LOG_ENABLE) {
			Log.e(MyLogutils.TAG, msg);
		}
	}

	public static void e(String tag, String msg) {
		if (MyLogutils.LOG_ENABLE) {
			Log.e(tag, msg);
		}
	}

	public static void e(String tag, String msg, Throwable tr) {
		if (MyLogutils.LOG_ENABLE) {
			Log.e(tag, msg, tr);
		}
	}

	public static void i(String msg) {
		if (MyLogutils.LOG_ENABLE) {
			Log.i(MyLogutils.TAG, msg);
		}
	}

	public static void i(String tag, String msg) {
		if (MyLogutils.LOG_ENABLE) {
			Log.i(tag, msg);
		}
	}

	public static void initLog(String switchFile, String gloableTag) {
		MyLogutils.LOG_ENABLE = new File(String.valueOf(Environment
				.getExternalStorageDirectory().getPath()) + "/" + switchFile)
				.exists() ? true : false;
		MyLogutils.TAG = gloableTag;
	}

	public static void initLog(String switcherName) {
		MyLogutils.initLog(switcherName, "AmazingLog");
	}

	public static void w(String msg) {
		if (MyLogutils.LOG_ENABLE) {
			Log.w(MyLogutils.TAG, msg);
		}
	}

	public static void w(String tag, String msg) {
		if (MyLogutils.LOG_ENABLE) {
			Log.w(tag, msg);
		}
	}
}
