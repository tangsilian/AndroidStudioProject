package com.example.tools;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.Toast;

public class WebViewActivity extends Activity {

    WebView webview;
    EditText edit;
@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.webviewlayout);
	webview=(WebView) findViewById(R.id.webview);
	edit=(EditText) findViewById(R.id.edit1);

}
//�����¼url
public void sendsms(View v){
String url=edit.getText().toString().trim();
	   easyload(url);
	
}
//����ָ����ҳ
private void easyload(String string) {
	webview.getSettings().setJavaScriptEnabled(true);
	//��webviewclient���ػ��ڵ�ǰҳ��
	webview.setWebViewClient((new WebViewClient()));
	//����webview�Ľӿ�
	webview.addJavascriptInterface(new WebAppInterface(this), "Android");  
	webview.loadUrl("http://"+string);

}
//����webappinterface
public class WebAppInterface {  
  Context mContext;  

  /** Instantiate the interface and set the context */  
  WebAppInterface(Context c) {  
      mContext = c;  
  }  

  /** Show a toast from the web page */  
  @JavascriptInterface  
  public void showToast(String toast) {  
      Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();  
  }  
}  

}
