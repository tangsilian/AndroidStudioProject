package com.example.fragment;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.testweixin.MessageActivity;
import com.example.testweixin.R;
import com.example.uitl.JsonParser;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechUtility;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ContactmainFragement extends Fragment {
	private static TextView text1;
	private static String TAG = "IatDemo";
	private static SpeechRecognizer mIat;
	private Button btn1;
	private Button btn2;
	private Button btn3;
	private View view;
	private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.tab02, container, false);
		SpeechUtility.createUtility(getActivity(), "appid=570bcd61");
		btn1 = (Button) view.findViewById(R.id.btn1);
		btn2 = (Button) view.findViewById(R.id.btn2);
		btn3 = (Button) view.findViewById(R.id.btn3);
		// 初始化
		mIat = SpeechRecognizer.createRecognizer(getActivity(), mInitListener);
		text1 = (TextView) view.findViewById(R.id.text1);
		btn1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setParam();
				int ret = mIat.startListening(mRecognizerListener);
				Log.d("识别失败,错误码: ", "" + ret);
				Toast.makeText(getActivity(), "请说话", Toast.LENGTH_SHORT).show();
			}
		});
		btn2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), MessageActivity.class);
				startActivity(intent);
			}
		});
		btn3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String z = text1.getText().toString();
				if(z.equals("小毛驴")) {
					Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:13349859467"));
					startActivity(intent);
					text1.setText("");
				}
				if(z.equals("罗旺")) {
					Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:13545906534"));
					startActivity(intent);
					text1.setText("");
				}
				if(z.equals("老师")) {
					Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:13387536239"));
					startActivity(intent);
					text1.setText("");
				}
			}
		});

		return view;
	}

	// 初始化监听器
	private InitListener mInitListener = new InitListener() {

		@Override
		public void onInit(int code) {
			text1.setText("");
			Log.d(TAG, "SpeechRecognizer init() code = " + code);
			if (code != ErrorCode.SUCCESS) {
				Log.d("初始化失败，错误码：", "" + code);
			}
		}
	};

	public void setParam() {

		// 设置语言
		mIat.setParameter(SpeechConstant.LANGUAGE, "en_us");

		// 设置语言
		mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
		// 设置语言区域
		mIat.setParameter(SpeechConstant.ACCENT, "mandarin");

	}

	private RecognizerListener mRecognizerListener = new RecognizerListener() {

		@Override
		public void onBeginOfSpeech() {
			// 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
			Log.d("TAG", "开始说话");
		}

		@Override
		public void onError(SpeechError error) {
			// Tips：
			// 错误码：10118(您没有说话)，可能是录音机权限被禁，需要提示用户打开应用的录音权限。
			// 如果使用本地功能（语记）需要提示用户开启语记的录音权限。
		}

		@Override
		public void onEndOfSpeech() {
			// 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
		}

		@Override
		public void onResult(RecognizerResult results, boolean isLast) {
			Log.d("result", results.getResultString());
			if (!isLast) {
				/*
				 * String s=results.getResultString(); Pattern pattern =
				 * Pattern.compile("^[u4e00-u9fa5]|[^x00-xff]"); Matcher matcher
				 * = pattern.matcher(s); if(matcher.find()){
				 * text1.setText(matcher.group());}
				 */
				String text = JsonParser.parseIatResult(results.getResultString());

				String sn = null;
				// 读取json结果中的sn字段
				try {
					JSONObject resultJson = new JSONObject(results.getResultString());
					sn = resultJson.optString("sn");
				} catch (JSONException e) {
					e.printStackTrace();
				}

				mIatResults.put(sn, text);

				StringBuffer resultBuffer = new StringBuffer();
				for (String key : mIatResults.keySet()) {
					resultBuffer.append(mIatResults.get(key));
				}

				text1.setText(resultBuffer.toString());
			}

		}

		@Override
		public void onVolumeChanged(int volume, byte[] data) {
			Log.d(TAG, "返回音频数据：" + data.length);
		}

		@Override
		public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
			// 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
			// 若使用本地能力，会话id为null
			// if (SpeechEvent.EVENT_SESSION_ID == eventType) {
			// String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
			// Log.d(TAG, "session id =" + sid);
			// }
		}
	};
}
