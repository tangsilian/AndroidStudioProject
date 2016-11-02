package com.example.tools.services;

import java.util.List;

import android.accessibilityservice.AccessibilityService;
import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.PowerManager;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

public class HijackQqhongbao extends AccessibilityService {

	static final String TAG = "QiangHongBao";

	/** 微信的包名 */
	static final String WECHAT_PACKAGENAME = "com.tencent.mm";
	/** 红包消息的关键字 */
	static final String WX_HONGBAO_TEXT_KEY = "[微信红包]";
	static final String QQ_HONGBAO_TEXT_KEY = "[QQ红包]";
	private boolean caihongbao = false;

	Handler handler = new Handler();

	@Override
	public void onAccessibilityEvent(AccessibilityEvent event) {
		final int eventType = event.getEventType(); // ClassName:
													// com.tencent.mm.ui.LauncherUI

		// 检测通知栏事件发生问题
		if (eventType == AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED) {
			List<CharSequence> texts = event.getText();
			if (!texts.isEmpty()) {
				for (CharSequence text : texts) {
					String content = text.toString();
					Log.i("demo", "text:" + content);
					Toast.makeText(this, "打印", Toast.LENGTH_SHORT).show();
					if (content.contains("[QQ红包]")) {
						Toast.makeText(this, "查询到通知栏变化", Toast.LENGTH_SHORT)
								.show();
						// 模拟打开通知栏消息
						if (event.getParcelableData() != null
								&& event.getParcelableData() instanceof Notification) {
							Notification notification = (Notification) event
									.getParcelableData();
							PendingIntent pendingIntent = notification.contentIntent;
							try {
								pendingIntent.send();
							} catch (CanceledException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		} else if (eventType == AccessibilityEvent.CONTENT_CHANGE_TYPE_SUBTREE) {
			// 从微信主界面进入聊天界面
			Toast.makeText(this, "从qq主界面进入聊天界面", Toast.LENGTH_SHORT).show();
			openHongBao(event);
		} else if (eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
			// 处理微信聊天界面
			openHongBao(event);
		}
	}

	@Override
	public void onInterrupt() {
		Toast.makeText(this, "中断抢红包服务", Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onServiceConnected() {
		super.onServiceConnected();
		Toast.makeText(this, "连接抢红包服务", Toast.LENGTH_SHORT).show();
	}

	/** 打开通知栏消息 */
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	private void openNotify(AccessibilityEvent event) {
		Toast.makeText(getApplicationContext(), "进行到打开步骤了", 0).show();
		if (event.getParcelableData() == null
				|| !(event.getParcelableData() instanceof Notification)) {
			return;
		}
		// 将微信的通知栏消息打开
		Notification notification = (Notification) event.getParcelableData();
		PendingIntent pendingIntent = notification.contentIntent;
		try {
			pendingIntent.send();
		} catch (PendingIntent.CanceledException e) {
			e.printStackTrace();
		}
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	private void openHongBao(AccessibilityEvent event) {
		CharSequence className = event.getClassName();

		checkScreen(getApplicationContext());

		if ("com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyReceiveUI"
				.equals(className)) {
			Toast.makeText(getApplicationContext(), "点中了红包", 0).show();
			// 点中了红包，下一步就是去拆红包
			checkKey1();
		} else if ("com.tencent.mm.ui.LauncherUI".equals(className)
				|| "com.tencent.mobileqq.activity.ChatActivity"
						.equals(className)) {
			// 在聊天界面,去点中红包
			Toast.makeText(getApplicationContext(), "在聊天界面,去点中红包", 0).show();
			checkKey2();
		} else {
			// 在聊天界面,去点中红包
			Toast.makeText(getApplicationContext(), "其他", 0).show();
			checkKey2();
		}
	}

	/**
	 * 
	 * @description: 检查屏幕是否亮着并且唤醒屏幕
	 * @date: 2016-1-29 下午2:08:25
	 * @author: yems
	 */
	private void checkScreen(Context context) {
		// TODO Auto-generated method stub
		PowerManager pm = (PowerManager) context
				.getSystemService(Context.POWER_SERVICE);
		if (!pm.isScreenOn()) {
			wakeUpAndUnlock(context);
		}

	}

	private void wakeUpAndUnlock(Context context) {
		KeyguardManager km = (KeyguardManager) context
				.getSystemService(Context.KEYGUARD_SERVICE);
		KeyguardManager.KeyguardLock kl = km.newKeyguardLock("unLock");
		// 解锁
		kl.disableKeyguard();
		// 获取电源管理器对象
		PowerManager pm = (PowerManager) context
				.getSystemService(Context.POWER_SERVICE);
		// 获取PowerManager.WakeLock对象,后面的参数|表示同时传入两个值,最后的是LogCat里用的Tag
		PowerManager.WakeLock wl = pm.newWakeLock(
				PowerManager.ACQUIRE_CAUSES_WAKEUP
						| PowerManager.SCREEN_DIM_WAKE_LOCK, "bright");
		// 点亮屏幕
		wl.acquire();
		// 释放
		wl.release();
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
	private void checkKey1() {
		AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
		if (nodeInfo == null)
			return;

		List<AccessibilityNodeInfo> list = nodeInfo
				.findAccessibilityNodeInfosByText("拆红包");

		if (list == null || list.size() == 0) {
			list = nodeInfo
					.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/b2c");
		}

		for (AccessibilityNodeInfo n : list) {
			n.performAction(AccessibilityNodeInfo.ACTION_CLICK);
		}
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	private void checkKey2() {
		AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
		if (nodeInfo == null) {
			Log.w(TAG, "rootWindow为空");
			return;
		}
		List<AccessibilityNodeInfo> wxList = nodeInfo
				.findAccessibilityNodeInfosByText("领取红包");
		List<AccessibilityNodeInfo> qqList = nodeInfo
				.findAccessibilityNodeInfosByText("QQ红包");

		if (!qqList.isEmpty()) {
			qqList = nodeInfo.findAccessibilityNodeInfosByText("QQ红包");
			for (AccessibilityNodeInfo n : qqList) {
				n.performAction(AccessibilityNodeInfo.ACTION_CLICK);
				break;
			}
		}

		if (!wxList.isEmpty()) {
			// 界面上的红包总个数
			int totalCount = wxList.size();
			// 领取最近发的红包
			for (int i = totalCount - 1; i >= 0; i--) {
				// 如果为领取过该红包，则执行点击、
				AccessibilityNodeInfo parent = wxList.get(i).getParent();
				if (parent != null) {
					parent.performAction(AccessibilityNodeInfo.ACTION_CLICK);
					break;
				}
			}
		} else if (!qqList.isEmpty()) {

			// 界面上的红包总个数
			int totalCount = qqList.size();
			// 领取最近发的红包
			for (int i = totalCount - 1; i >= 0; i--) {
				// 如果为领取过该红包，则执行点击、
				AccessibilityNodeInfo parent = qqList.get(i).getParent();
				Log.i(TAG, "-->领取红包:" + parent);
				Toast.makeText(getApplicationContext(), "-->领取红包:", 0).show();
				if (parent != null) {
					parent.performAction(AccessibilityNodeInfo.ACTION_CLICK);
					break;
				}
			}

		}

	}

}
