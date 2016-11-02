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
		// ��ʼ��
		mIat = SpeechRecognizer.createRecognizer(getActivity(), mInitListener);
		text1 = (TextView) view.findViewById(R.id.text1);
		btn1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setParam();
				int ret = mIat.startListening(mRecognizerListener);
				Log.d("ʶ��ʧ��,������: ", "" + ret);
				Toast.makeText(getActivity(), "��˵��", Toast.LENGTH_SHORT).show();
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
				if(z.equals("Сë¿")) {
					Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:13349859467"));
					startActivity(intent);
					text1.setText("");
				}
				if(z.equals("����")) {
					Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:13545906534"));
					startActivity(intent);
					text1.setText("");
				}
				if(z.equals("��ʦ")) {
					Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:13387536239"));
					startActivity(intent);
					text1.setText("");
				}
			}
		});

		return view;
	}

	// ��ʼ��������
	private InitListener mInitListener = new InitListener() {

		@Override
		public void onInit(int code) {
			text1.setText("");
			Log.d(TAG, "SpeechRecognizer init() code = " + code);
			if (code != ErrorCode.SUCCESS) {
				Log.d("��ʼ��ʧ�ܣ������룺", "" + code);
			}
		}
	};

	public void setParam() {

		// ��������
		mIat.setParameter(SpeechConstant.LANGUAGE, "en_us");

		// ��������
		mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
		// ������������
		mIat.setParameter(SpeechConstant.ACCENT, "mandarin");

	}

	private RecognizerListener mRecognizerListener = new RecognizerListener() {

		@Override
		public void onBeginOfSpeech() {
			// �˻ص���ʾ��sdk�ڲ�¼�����Ѿ�׼�����ˣ��û����Կ�ʼ��������
			Log.d("TAG", "��ʼ˵��");
		}

		@Override
		public void onError(SpeechError error) {
			// Tips��
			// �����룺10118(��û��˵��)��������¼����Ȩ�ޱ�������Ҫ��ʾ�û���Ӧ�õ�¼��Ȩ�ޡ�
			// ���ʹ�ñ��ع��ܣ���ǣ���Ҫ��ʾ�û�������ǵ�¼��Ȩ�ޡ�
		}

		@Override
		public void onEndOfSpeech() {
			// �˻ص���ʾ����⵽��������β�˵㣬�Ѿ�����ʶ����̣����ٽ�����������
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
				// ��ȡjson����е�sn�ֶ�
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
			Log.d(TAG, "������Ƶ���ݣ�" + data.length);
		}

		@Override
		public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
			// ���´������ڻ�ȡ���ƶ˵ĻỰid����ҵ�����ʱ���Ựid�ṩ������֧����Ա�������ڲ�ѯ�Ự��־����λ����ԭ��
			// ��ʹ�ñ����������ỰidΪnull
			// if (SpeechEvent.EVENT_SESSION_ID == eventType) {
			// String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
			// Log.d(TAG, "session id =" + sid);
			// }
		}
	};
}
