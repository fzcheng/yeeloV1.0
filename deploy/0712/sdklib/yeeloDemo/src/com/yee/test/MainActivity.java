package com.yee.test;

import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.yeecloud.pay.CallSdkListener;
import com.yeecloud.pay.QueryPayResultListener;
import com.yeecloud.pay.YEEPay;

public class MainActivity extends Activity {

	private EditText appidEt, totalFeeEt, waresIdEt, waresEt, extEt, noitfy_urlEt, wxappidEt;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button btn = (Button)findViewById(R.id.submitPay);
		
		appidEt = (EditText)findViewById(R.id.appid);
        
		totalFeeEt = (EditText)findViewById(R.id.totalFee);
        
		waresIdEt = (EditText)findViewById(R.id.waresId);
        
		waresEt = (EditText)findViewById(R.id.wares);
        
		extEt = (EditText)findViewById(R.id.ext);
        
		noitfy_urlEt = (EditText)findViewById(R.id.noitfy_url);
        
		wxappidEt = (EditText)findViewById(R.id.wxappid);
        
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				final Bundle extras = new Bundle();
				String appid = appidEt.getText().toString();
				String market = "401";
				extras.putString("appid", appid);//必填，appid
				extras.putString("market", market);//必填，渠道编号，默认400
				extras.putString("userId", String.valueOf(System.currentTimeMillis()));//必填,用户编号
				extras.putString("nickname", "nickname"+new Random().nextInt(1000));//必填，昵称
				extras.putString("waresId", waresIdEt.getText().toString());//必填，商品编号
				extras.putString("wares", waresEt.getText().toString());//必填，商品名称
				extras.putString("cpOrderId", "cpOrderId"+new Random().nextInt(10000));//可选，CP订单编号，不重复
				extras.putString("ext", extEt.getText().toString());//可选，透传参数，原样返回
				extras.putString("totalFee", totalFeeEt.getText().toString());//必填，支付金额，单位分
				extras.putString("noitfy_url", noitfy_urlEt.getText().toString());//可选。服务器通知地址
				extras.putString("wxappid", wxappidEt.getText().toString());//可选，微信APPID，启用自有微信app支付时有用
				
				YEEPay.getInstance().onCallSdk(appid, market, new CallSdkListener() {
					
					@Override
					public void onResult(int sdkId) {
						//Toast.makeText(MainActivity.this, String.valueOf(sdkId), Toast.LENGTH_SHORT).show();
						switch (sdkId) {
						case CallSdkListener.YEE_SDK:
							YEEPay.getInstance().pay(MainActivity.this, extras);
							break;
						case CallSdkListener.MSDK:
							//call MSDK
							Toast.makeText(MainActivity.this, "call MSDK", Toast.LENGTH_SHORT).show();
							break;
						case CallSdkListener.PARAM_ERROR:
							Toast.makeText(MainActivity.this, "appid或者market设置错误", Toast.LENGTH_SHORT).show();
							break;
						case CallSdkListener.UNKNOWN:
							Toast.makeText(MainActivity.this, "未知错误", Toast.LENGTH_SHORT).show();
							break;
						default:
							break;
						}
						
					}
				});
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 20000 && resultCode == 20000) {
    		String orderId = data.getStringExtra("orderId");
			YEEPay.getInstance().onPayResult(orderId, new QueryPayResultListener() {
				
				@Override
				public void onResult(int result) {
					switch(result) {
						case 200:
							Toast.makeText(MainActivity.this, "支付成功"+result, Toast.LENGTH_SHORT).show();
							break;
						case 201:
							Toast.makeText(MainActivity.this, "创建订单"+result, Toast.LENGTH_SHORT).show();
							break;
						case 202:
							Toast.makeText(MainActivity.this, "待处理"+result, Toast.LENGTH_SHORT).show();
							break;
						case 203:
							Toast.makeText(MainActivity.this, "支付失败"+result, Toast.LENGTH_SHORT).show();
							break;
						case -2:
							Toast.makeText(MainActivity.this, "支付取消"+result, Toast.LENGTH_SHORT).show();
							break;
						case -1:
							Toast.makeText(MainActivity.this, "未知错误"+result, Toast.LENGTH_SHORT).show();
							break;
						case 0:
							Toast.makeText(MainActivity.this, "网络异常"+result, Toast.LENGTH_SHORT).show();
							break;
					}
				}
			});
    	}
	}
}