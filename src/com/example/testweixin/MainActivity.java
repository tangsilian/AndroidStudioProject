package com.example.testweixin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	private Button loginbtn;
	private EditText edtname;
	private EditText edtpassword;
	private String name;
	private String password;
	private CheckBox checkbox;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login);
		loginbtn = (Button) findViewById(R.id.loginbtn);
		edtname = (EditText) findViewById(R.id.edtlogin);
		edtpassword = (EditText) findViewById(R.id.edtpassword);
		checkbox = (CheckBox) findViewById(R.id.checkbox);
		checkbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					edtpassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
				} else {
					edtpassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
				}

			}
		});
		loginbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				name = edtname.getText().toString();
				password = edtpassword.getText().toString();
				// TODO Auto-generated method stub
				/*if (name.equals("jaytang") && password.equals("4011")) {
*/
					Intent intent = new Intent(MainActivity.this, Weixin.class);
					startActivity(intent);
					finish();
				/*} else {

					Toast.makeText(MainActivity.this, "false", Toast.LENGTH_SHORT).show();

				}*/
			}
		});

	}

}
