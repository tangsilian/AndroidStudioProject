package com.example.tools.getpackage;

import java.util.List;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

//��listview��ʾapp����Ϣ
public class Getpackage {
	    /** 
	     * ��ȡ΢�ŵİ汾�� 
	     * @param context 
	     * @return 
	     */ 
	    public static String getVersion(Context context){
	        PackageManager packageManager = context.getPackageManager();
	        List<PackageInfo> packageInfoList = packageManager.getInstalledPackages(0);
	 
	        for(PackageInfo packageInfo:packageInfoList){
	            if("com.tencent.mm".equals(packageInfo.packageName)){
	                return packageInfo.versionName;
	            } 
	        } 
	        return "6.3.25"; 
	    } 
}
