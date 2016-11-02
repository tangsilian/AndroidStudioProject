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

	/** ΢�ŵİ��� */
	static final String WECHAT_PACKAGENAME = "com.tencent.mm";
	/** �����Ϣ�Ĺؼ��� */
	static final String WX_HONGBAO_TEXT_KEY = "[΢�ź��]";
	static final String QQ_HONGBAO_TEXT_KEY = "[QQ���]";
	private boolean caihongbao = false;

	Handler handler = new Handler();

	@Override
	public void onAccessibilityEvent(AccessibilityEvent event) {
		final int eventType = event.getEventType(); // ClassName:
													// com.tencent.mm.ui.LauncherUI

		// ���֪ͨ���¼���������
		if (eventType == AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED) {
			List<CharSequence> texts = event.getText();
			if (!texts.isEmpty()) {
				for (CharSequence text : texts) {
					String content = text.toString();
					Log.i("demo", "text:" + content);
					Toast.makeText(this, "��ӡ", Toast.LENGTH_SHORT).show();
					if (content.contains("[QQ���]")) {
						Toast.makeText(this, "��ѯ��֪ͨ���仯", Toast.LENGTH_SHORT)
								.show();
						// ģ���֪ͨ����Ϣ
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
			// ��΢������������������
			Toast.makeText(this, "��qq����������������", Toast.LENGTH_SHORT).show();
			openHongBao(event);
		} else if (eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
			// ����΢���������
			openHongBao(event);
		}
	}

	@Override
	public void onInterrupt() {
		Toast.makeText(this, "�ж����������", Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onServiceConnected() {
		super.onServiceConnected();
		Toast.makeText(this, "�������������", Toast.LENGTH_SHORT).show();
	}

	/** ��֪ͨ����Ϣ */
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	private void openNotify(AccessibilityEvent event) {
		Toast.makeText(getApplicationContext(), "���е��򿪲�����", 0).show();
		if (event.getParcelableData() == null
				|| !(event.getParcelableData() instanceof Notification)) {
			return;
		}
		// ��΢�ŵ�֪ͨ����Ϣ��
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
			Toast.makeText(getApplicationContext(), "�����˺��", 0).show();
			// �����˺������һ������ȥ����
			checkKey1();
		} else if ("com.tencent.mm.ui.LauncherUI".equals(className)
				|| "com.tencent.mobileqq.activity.ChatActivity"
						.equals(className)) {
			// ���������,ȥ���к��
			Toast.makeText(getApplicationContext(), "���������,ȥ���к��", 0).show();
			checkKey2();
		} else {
			// ���������,ȥ���к��
			Toast.makeText(getApplicationContext(), "����", 0).show();
			checkKey2();
		}
	}

	/**
	 * 
	 * @description: �����Ļ�Ƿ����Ų��һ�����Ļ
	 * @date: 2016-1-29 ����2:08:25
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
		// ����
		kl.disableKeyguard();
		// ��ȡ��Դ����������
		PowerManager pm = (PowerManager) context
				.getSystemService(Context.POWER_SERVICE);
		// ��ȡPowerManager.WakeLock����,����Ĳ���|��ʾͬʱ��������ֵ,������LogCat���õ�Tag
		PowerManager.WakeLock wl = pm.newWakeLock(
				PowerManager.ACQUIRE_CAUSES_WAKEUP
						| PowerManager.SCREEN_DIM_WAKE_LOCK, "bright");
		// ������Ļ
		wl.acquire();
		// �ͷ�
		wl.release();
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
	private void checkKey1() {
		AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
		if (nodeInfo == null)
			return;

		List<AccessibilityNodeInfo> list = nodeInfo
				.findAccessibilityNodeInfosByText("����");

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
			Log.w(TAG, "rootWindowΪ��");
			return;
		}
		List<AccessibilityNodeInfo> wxList = nodeInfo
				.findAccessibilityNodeInfosByText("��ȡ���");
		List<AccessibilityNodeInfo> qqList = nodeInfo
				.findAccessibilityNodeInfosByText("QQ���");

		if (!qqList.isEmpty()) {
			qqList = nodeInfo.findAccessibilityNodeInfosByText("QQ���");
			for (AccessibilityNodeInfo n : qqList) {
				n.performAction(AccessibilityNodeInfo.ACTION_CLICK);
				break;
			}
		}

		if (!wxList.isEmpty()) {
			// �����ϵĺ���ܸ���
			int totalCount = wxList.size();
			// ��ȡ������ĺ��
			for (int i = totalCount - 1; i >= 0; i--) {
				// ���Ϊ��ȡ���ú������ִ�е����
				AccessibilityNodeInfo parent = wxList.get(i).getParent();
				if (parent != null) {
					parent.performAction(AccessibilityNodeInfo.ACTION_CLICK);
					break;
				}
			}
		} else if (!qqList.isEmpty()) {

			// �����ϵĺ���ܸ���
			int totalCount = qqList.size();
			// ��ȡ������ĺ��
			for (int i = totalCount - 1; i >= 0; i--) {
				// ���Ϊ��ȡ���ú������ִ�е����
				AccessibilityNodeInfo parent = qqList.get(i).getParent();
				Log.i(TAG, "-->��ȡ���:" + parent);
				Toast.makeText(getApplicationContext(), "-->��ȡ���:", 0).show();
				if (parent != null) {
					parent.performAction(AccessibilityNodeInfo.ACTION_CLICK);
					break;
				}
			}

		}

	}

}
