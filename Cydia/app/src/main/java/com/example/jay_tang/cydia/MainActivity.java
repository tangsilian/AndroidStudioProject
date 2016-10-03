package com.example.jay_tang.cydia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findtheclassz();
    }

    public void findtheclassz(){
        //先找到
         Class clazz=this.getClass();
        Method[] methods=clazz.getMethods();//獲取共有可直接使用的方法
        Method[] medelcaredmethods=clazz.getDeclaredMethods();//獲取聲明的所有方法
        Log.d("TAG", "-----------------------");
        for(Method m:methods){
            Log.d("TAG",m.getName());
        }

    }
}
