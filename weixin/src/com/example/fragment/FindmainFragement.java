package com.example.fragment;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;

import com.example.testweixin.MainActivity;
import com.example.testweixin.R;
import com.example.uitl.GetUtil;
import com.iflytek.cloud.SpeechSynthesizer;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class FindmainFragement extends Fragment {
	private View view;
	private Button btn1;
	// 0����ִ��ץȡ
	boolean flag = false;
	private final Lock lock = new ReentrantLock();
	private final Condition cond = lock.newCondition();
	Thread thread;
	TextView London;
	// �����������Ӧ���ַ���
	String response;
	String[] gold = new String[2];
	Handler handler = new Handler() {
		@TargetApi(Build.VERSION_CODES.LOLLIPOP)
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 0x123) {
				if (response == "") {
					
					Toast.makeText(getActivity(), "�����������������⣬������", Toast.LENGTH_SHORT).show();
				} else {
					London.setText(gold[0]);
				}
			}
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.tab03, container, false);
		London = (TextView) view.findViewById(R.id.gold);
		btn1 = (Button) view.findViewById(R.id.search_gold);
		btn1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Thread thread = new Thread() {
					@Override
					public void run() {
						while (true) {
							try {
								lock.lock(); // �Ը��߳̽��м���
								int i = 0;
								gold = new String[2];
								response = GetUtil.sendGet("http://www.sincerehk.com/zh-hk/index.aspx");
								// �����ȡ����ʧ��
								if (response == "") {
									handler.sendEmptyMessage(0x123);
									break; // ֱ������ѭ��
								}
								// ������ƥ������
								Pattern p = Pattern.compile("<.*?>\\w*��<.*?><.*?> <.*?><.*?>(.*?)<!--.*?/.*?--><.*?>");
								Matcher m = p.matcher(response);
								while (m.find()) {
									MatchResult mr = m.toMatchResult();
									gold[i] = mr.group(1);
									i++;
								}
								// ����֪ͨ���������߳�ˢ������
								handler.sendEmptyMessage(0x123);
								// �������̣߳��ȴ�֪ͨ
								cond.await();
							} catch (InterruptedException e) {
								e.printStackTrace();
							} finally {
								lock.unlock();
							}
						}
					}

				};
				thread.start();

			}
		});

		return view;
	}

}
