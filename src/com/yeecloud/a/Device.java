package com.yeecloud.a;import android.app.Activity;import android.os.Build;import android.telephony.TelephonyManager;public class Device{  private Activity a;  private TelephonyManager b;  public Device(Activity paramActivity)  {    this.a = paramActivity;    this.b = ((TelephonyManager)this.a.getSystemService("phone"));  }  public String a()  {    return this.b.getDeviceId();  }  public String b()  {    return Build.MODEL;  }  public String c()  {    return Build.BRAND;  }  public String d()  {    return this.b.getLine1Number();  }}