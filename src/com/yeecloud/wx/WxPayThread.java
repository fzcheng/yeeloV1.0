package com.yeecloud.wx;import android.os.Handler;import android.os.Looper;import android.os.Message;import com.yeecloud.a.CallResult;import com.yeecloud.a.HttpUtil;import java.util.Map;class WxPayThread extends Thread{  private String b;  private Map c;  private Handler d;  public WxPayThread(WXAPIEventHandler parama, Handler paramHandler, String paramString, Map paramMap)  {    this.b = paramString;    this.c = paramMap;    this.d = paramHandler;  }  public void run()  {	  CallResult localb = HttpUtil.a(this.b, null, this.c);    Looper.prepare();    Message localMessage = this.d.obtainMessage();    int i = -1;    String str = "";    if (localb != null)    {      i = localb.a();      str = localb.b();    }    localMessage.what = i;    localMessage.obj = str;    this.d.sendMessage(localMessage);    Looper.loop();  }}