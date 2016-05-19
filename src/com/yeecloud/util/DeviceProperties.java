/*
 * Copyright (C) 2012 Guangzhou CooguoSoft Co.,Ltd.
 * cn.douwan.sdk.entityDeviceProperties.java
 */
package com.yeecloud.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;


/** 
 * @Description: 设备环境参数
 * @author Jerry   @date 2012-8-21 上午09:58:49 
 * @version 1.0 
 * @JDK  1.6
 */

public class DeviceProperties implements Serializable, JsonParseInterface {
	
	/**
	 * 1.0.1
	 * 手机系统版本
	 */
	public String sdkVersion;
	
	
	/**	
	 * 1.0.1
	 * 手机型号
	 */
	public String product;
	
	/**
	 * 1.0.1
	 * Sim卡序列号
	 */
	public String imsi;
	
	/**
	 * 1.0.1 
	 * phonenum
	 */
	//public String phonenum;
	
	/**
	 * 1.0.1
	 * 手机序列号
	 */
	public String imei;
	
	/**
	 * 1.0.1
	 * 应用版本名称
	 */
	public String versionName;
	
	/**
	 * 1.0.1
	 * 应用版本号
	 */
	public int versionCode;
	
	/**
	 * 1.0.1
	 * 手机屏幕密码
	 */
	public int densityDpi;
	
	/**
	 * 	1.0.1
	 * 手机屏幕宽度
	 */
	public int displayScreenWidth;

	/**
	 * 1.0.1
	 * 手机屏幕高度
	 */
	public int displayScreenHeight;

	/**
	 * 1.0.1
	 * 网络类型
	 */
	public String networkInfo;

	/**
	 * 1.0.1
	 * key
	 */
	public String key;
	
	/**
	 * 1.0.1
	 * 后台自动生成唯一标识
	 */
	public String app_id;
	
	/**
	 * 1.0.1 
	 * 渠道号        
	 */
	public String channelId;
	/**
	 * 1.0.1 
	 * 游戏包名
	 */
	public String packageName;
	/**
	 * 1.0.1 
	 * sdk协议版本号
	 */
	public String protocol = "2.0.6";
	
	/**
	 * 1.0.1 
	 * 自定义手机唯一标识
	 */
	public String deviceParams;
	
	public DeviceProperties(Context ctx) {
		
		product = android.os.Build.MODEL;
		//sdkVersion = android.os.Build.VERSION.SDK;
		sdkVersion = "1.0";
		
		TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
		imsi = tm.getSubscriberId();
		imei = tm.getDeviceId();
//		imsi = Utils.getIMSI(ctx);
		Logger.d("imsi --> " + imsi);
		
		//phonenum = getNativePhoneNumber(ctx);
		
		//--获取手机分辨率
		WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics metrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(metrics);
		densityDpi = metrics.densityDpi;
		displayScreenWidth = metrics.widthPixels;
		displayScreenHeight = metrics.heightPixels;
		
		PackageManager pm = ctx.getPackageManager();
		packageName = ctx.getPackageName();
		PackageInfo info;
		try {
			info = pm.getPackageInfo(ctx.getPackageName(), 0);
			versionName = info.versionName;
			versionCode = info.versionCode;
		} catch (NameNotFoundException e1) {
		}
		
		try {
			ApplicationInfo appinfo = pm.getApplicationInfo(ctx.getPackageName(), PackageManager.GET_META_DATA);
			Bundle metaData = appinfo.metaData;
			key = String.valueOf(metaData.getString("MBIRD_APP_KEY"));
			app_id = String.valueOf(metaData.getInt("MBIRD_APP_ID"));
//			payId = Utils.getPayId(ctx);
			Logger.d("mbirdpay", "payId -> " + key);
//			channelId = metaData.getString("QUDOO_CHANNEL_ID");
			channelId = String.valueOf(metaData.get("MBIRD_CHANNEL_ID")) ;
//			channelId = String.valueOf(metaData.getInt("QUDOO_CHANNEL_ID"));
			Logger.d("cmbirdpay","channelId = " + channelId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		networkInfo = NetworkImpl.getNetworkTypeName(ctx);
		if (networkInfo == null) {
			networkInfo = "unknown";
		}
		// 获取硬件参数
		deviceParams = getDeviceParams(ctx);
	}
	
	/**
     * Role:获取当前设置的电话号码
     * <BR>Date:2012-3-12
     * <BR>@author CODYY)peijiangping
     */
    public String getNativePhoneNumber(Context context) {
    	TelephonyManager telephonyManager;
    	telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
    	
        String NativePhoneNumber=null;
        NativePhoneNumber=telephonyManager.getLine1Number();
        if(NativePhoneNumber == null)
        	NativePhoneNumber = "";
        return NativePhoneNumber;
    }
	
	/**
	 * 获取UA
	 * 
	 * @return
	 */
	public static String getUa() 
	{
		String ua = Build.BRAND + "-" + Build.MODEL;
		return ua;
	}
	
	
	private String getDeviceParams(Context ctx) {
		StringBuilder deviceParams = new StringBuilder();
		deviceParams.append(imei);
    	//在wifi未开启状态下，仍然可以获取MAC地址，但是IP地址必须在已连接状态下否则为0
    	WifiManager wifiMgr = (WifiManager)ctx.getSystemService(Context.WIFI_SERVICE);
    	WifiInfo info = (null == wifiMgr ? null : wifiMgr.getConnectionInfo());
    	if (null != info) {
    	    String macAddress = info.getMacAddress();
    	    if (macAddress != null) 
    	    	deviceParams.append(macAddress);
    	}
    	//return Utils.md5Encode(deviceParams.toString());
    	return deviceParams.toString();
    }

	@Override
	public String toString() {
		return "DeviceProperties [sdkVersion=" + sdkVersion + ", product="
				+ product + ", imsi=" + imsi + ", imei=" + imei
				+ ", versionName=" + versionName + ", versionCode="
				+ versionCode + ", densityDpi=" + densityDpi
				+ ", displayScreenWidth=" + displayScreenWidth
				+ ", displayScreenHeight=" + displayScreenHeight
				+ ", networkInfo=" + networkInfo + ", key=" + key
				+ ", channelId=" + channelId + ", packageName=" + packageName
				+ ", protocol=" + protocol + ", deviceParams=" + deviceParams
				+ "]";
	}

	@Override
	public JSONObject buildJson() {
		try {
			JSONObject json = new JSONObject();
			
			json.put("imsi", imsi);
			json.put("imei", imei);
			json.put("vname", versionName);
			json.put("vcode", versionCode);
			json.put("dpi", densityDpi);
			json.put("width", displayScreenWidth);
			json.put("height", displayScreenHeight);
			json.put("nettype", networkInfo);
			json.put("package", packageName);
			json.put("product", product);
			json.put("channel", channelId);
			json.put("key", key);
			json.put("app_id", app_id);
			
			Random rand = new Random();
			rand.setSeed(System.currentTimeMillis());
			
			json.put("time", System.currentTimeMillis());
			json.put("rand", rand.nextInt(9999));
			
			return json;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public Map<String, Object> buildMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("imsi", imsi);
		map.put("imei", imei);
		map.put("vname", versionName);
		map.put("vcode", versionCode);
		map.put("dpi", densityDpi);
		map.put("width", displayScreenWidth);
		map.put("height", displayScreenHeight);
		map.put("nettype", networkInfo);
		map.put("package", packageName);
		map.put("sdk", sdkVersion);
		map.put("product", product);
		map.put("channel", channelId);
		map.put("app_id", app_id);
		
		Random rand = new Random();
		rand.setSeed(System.currentTimeMillis());
		
		map.put("time", System.currentTimeMillis());
		map.put("rand", rand.nextInt(9999));
		
		return map;
	}
	
	@Override
	public void parseJson(JSONObject json) {
		if (json == null)
			return;
		try {
			product = json.isNull("product") ? null : json.getString("product");
			imsi = json.isNull("imsi") ? null : json.getString("imsi");
			imei = json.isNull("imei") ? null : json.getString("imei");
			versionName = json.isNull("vname") ? null : json.getString("vname");
			versionCode = json.isNull("vcode") ? -1 : json.getInt("vcode");
			densityDpi = json.isNull("dpi") ? 240 : json.getInt("dpi");
			displayScreenWidth = json.isNull("width") ? 0 : json.getInt("width");
			displayScreenHeight = json.isNull("height") ? 0 : json.getInt("height");
			networkInfo = json.isNull("nettype") ? null : json.getString("nettype");
			key = json.isNull("key") ? null : json.getString("key");
			channelId = json.isNull("channel") ? null : json.getString("channel");
			packageName = json.isNull("package") ? null : json.getString("package");
			app_id = json.isNull("app_id") ? null : json.getString("app_id");

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getShortName() {
		return "a";
	}

}
