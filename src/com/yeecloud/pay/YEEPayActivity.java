package com.yeecloud.pay;import android.app.Activity;import android.content.Intent;import android.os.Bundle;import android.os.Handler;import android.os.Message;import android.text.TextUtils;import android.util.Log;import android.view.KeyEvent;import android.view.View;import android.view.View.OnClickListener;import android.widget.ImageView;import android.widget.LinearLayout;import android.widget.TextView;import android.widget.Toast;import java.text.DecimalFormat;import com.yeecloud.wx.WXAPIEventHandler;public class YEEPayActivity extends Activity implements View.OnClickListener {	private Bundle b;	private com.yeecloud.a.F c;	private String orderId;	final Handler a = new Handler() {		@Override		public void handleMessage(Message paramMessage) {			int i = paramMessage.what;			String str = (String) paramMessage.obj;			Intent localIntent = new Intent();			switch (i) {			case -1:				localIntent.putExtra("code", 0);				localIntent.putExtra("orderId", orderId);				setResult(20000, localIntent);				break;			case 200:				int j = Integer.parseInt(str);				localIntent.putExtra("code", j);				localIntent.putExtra("orderId", orderId);				setResult(20000, localIntent);				break;			default:				localIntent.putExtra("code", -1);				localIntent.putExtra("orderId", orderId);				setResult(20000, localIntent);				break;			}			finish();		}	};	protected void onCreate(Bundle paramBundle) {		super.onCreate(paramBundle);		this.c = new com.yeecloud.a.F(this);		this.b = getIntent().getExtras();		setContentView(this.c.c("yee_pay_main"));		LinearLayout localLinearLayout = (LinearLayout) findViewById(this.c				.b("yeePayBackBtn"));		localLinearLayout.setOnClickListener(this);		if (!b()) {			finish();			return;		}		com.yeecloud.a.Device locala = new com.yeecloud.a.Device(this);		this.b.putString("imei", locala.a());		this.b.putString("model", locala.c() + "-" + locala.b());		this.b.putString("mobile", locala.d());		this.b.putString("version", String.valueOf(1));		TextView localTextView1 = (TextView) findViewById(this.c				.b("yee_pay_account"));		localTextView1.setText(this.b.getString("nickname"));		TextView localTextView2 = (TextView) findViewById(this.c				.b("yee_pay_wares"));		localTextView2.setText(this.b.getString("wares"));		TextView localTextView3 = (TextView) findViewById(this.c				.b("yee_pay_money"));		String str1 = (String) this.b.get("totalFee");		double d1 = Integer.parseInt(str1) / 100.0D;		DecimalFormat localDecimalFormat = new DecimalFormat("#0.00");		String str2 = localDecimalFormat.format(d1);		localTextView3.setText(str2);		ImageView localImageView1 = (ImageView) findViewById(this.c				.b("yeePayWxBtn"));		localImageView1.setOnClickListener(new OnClickListener() {			@Override			public void onClick(View v) {				// a.a().a(this.a, yeePayActivity.b(this.a), new e(this));				WXAPIEventHandler.a().a(YEEPayActivity.this, b, new k() {					@Override					public void a(String paramString) {						YEEPayActivity.this.a(paramString);						YEEPayActivity.this.a();					}					@Override					public void a() {						Intent localIntent = new Intent();						localIntent.putExtra("code", -1);						setResult(20000, localIntent);					}				});			}		});		ImageView localImageView2 = (ImageView) findViewById(this.c				.b("yeePayAlipayBtn"));		localImageView2.setOnClickListener(new OnClickListener() {			@Override			public void onClick(View v) {				 com.yeecloud.pay.a.b.a().a(YEEPayActivity.this, b, new k() {						@Override						public void a(String paramString) {							YEEPayActivity.this.a(paramString);							YEEPayActivity.this.a();						}						@Override						public void a() {							Intent localIntent = new Intent();							localIntent.putExtra("code", -1);							setResult(20000, localIntent);						}					});			}		});	}	protected void onActivityResult(int paramInt1, int paramInt2,			Intent paramIntent) {		Log.e("YeePayActivity", "requestCode=" + paramInt1 + ";resultCode="				+ paramInt2);		if ((paramInt1 == 20002) && (paramInt2 == 20002)) {			Bundle localBundle = paramIntent.getExtras();			int i = localBundle.getInt("code");			Object localObject;			if (i == 200) {				localObject = localBundle.getString("orderId");				a((String) localObject);				a();			} else {				Log.i("YeePayActivity", "onFailure");				localObject = new Intent();				paramIntent.putExtra("code", 0);				setResult(20000, (Intent) localObject);			}		}	}	private void a(String paramString) {		this.orderId = paramString;		Log.i("YeePayActivity", "onSuccess");	}	private void a() {		Intent localIntent = new Intent();		localIntent.putExtra("orderId", this.orderId);		setResult(20000, localIntent);		finish();	}	private boolean b() {		String str1 = this.b.getString("appid");		if ((TextUtils.isEmpty(str1)) || (str1.length() != 18)) {			Toast.makeText(this, "appid为空或设置错误", 0).show();			return false;		}		String str2 = this.b.getString("market");		if (TextUtils.isEmpty(str2)) {			Toast.makeText(this, "market为空或设置错误", 0).show();			return false;		}		String str3 = this.b.getString("userId");		if (TextUtils.isEmpty(str3)) {			Toast.makeText(this, "userId为空或设置错误", 0).show();			return false;		}		String str4 = this.b.getString("nickname");		if (TextUtils.isEmpty(str4)) {			Toast.makeText(this, "nickname为空或设置错误", 0).show();			return false;		}		String str5 = this.b.getString("waresId");		if (TextUtils.isEmpty(str5)) {			Toast.makeText(this, "waresId为空或设置错误", 0).show();			return false;		}		String str6 = this.b.getString("wares");		if (TextUtils.isEmpty(str6)) {			Toast.makeText(this, "wares为空或设置错误", 0).show();			return false;		}		Object localObject = this.b.get("totalFee");		if (localObject == null) {			Toast.makeText(this, "totalFee为空", 0).show();			return false;		}		int i = 0;		try {			i = Integer.parseInt(localObject.toString());		} catch (Exception localException) {			Toast.makeText(this, "totalFee必须为整数", 0).show();			return false;		}		if (i <= 0) {			Toast.makeText(this, "totalFee必须大于0", 0).show();			return false;		}		this.b.remove("totalFee");		this.b.putString("totalFee", String.valueOf(i));		String str7 = this.b.getString("wxappid");		if (TextUtils.isEmpty(str7)) {			Toast.makeText(this, "wxappid为空或设置错误", 0).show();			return false;		}		return true;	}	public void onClick(View paramView) {		if (paramView.getId() == this.c.b("yeePayBackBtn"))			a();	}	public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent) {		if (paramInt == 4)			a();		return super.onKeyDown(paramInt, paramKeyEvent);	}}