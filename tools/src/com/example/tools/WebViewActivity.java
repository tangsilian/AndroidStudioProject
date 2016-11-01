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
//点击登录url
public void sendsms(View v){
String url=edit.getText().toString().trim();
	   easyload(url);
	
}
//加载指定网页
private void easyload(String string) {
	webview.getSettings().setJavaScriptEnabled(true);
	//用webviewclient加载会在当前页面
	webview.setWebViewClient((new WebViewClient()));
	//加载webview的接口
	webview.addJavascriptInterface(new WebAppInterface(this), "Android");  
	webview.loadUrl("http://"+string);

}
//创建webappinterface
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
