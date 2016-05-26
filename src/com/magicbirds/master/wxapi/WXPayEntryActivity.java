package com.magicbirds.master.wxapi;

import android.os.Bundle;

import com.yeecloud.wx.WxPayActivity;

/**
 * 重要：开发者只需要修改为对应的包名，微信appid即可，
 * 
 * AndroidManifest.xml中的配置如下：
 * 
 *  <activity android:name=".wxapi.WXPayEntryActivity" android:exported="true"
 *           android:launchMode="singleTop"/> 
 *          
 */
public class WXPayEntryActivity extends WxPayActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.init("wxd9c0c13bacc6d9b0");// 微信appID
	}
}