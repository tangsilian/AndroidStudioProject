package com.example.tools.utils;

import java.util.List;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
//遍歷app然後拿到app的版本信息
public class Getappversion {

	/** 
	     * 获取微信的版本号 
	     * @param context 
	     * @return 
	     */ 
	    public  String getVersion(Context context){
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
