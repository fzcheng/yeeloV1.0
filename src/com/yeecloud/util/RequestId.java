package com.yeecloud.util;

public class RequestId {
//	/** 游戏支付 */
//	public static final byte ID_CHARGE = 0x00;
//	/** 支付方式 */
//	public static final byte ID_PAYMENT_LIST = 0x01;
//	/**返回支付结果*/
//	public static final byte ID_PAYRESULT = 0x02;
//	/** 游戏登入 */
//	public static final byte ID_ONLINE = 0x03;
	
	/** 初始化 */
	public static final String URL_INIT = "/mbpay.do?cmd=init";
	/** 请求计费点 */
	//public static final String URL_REQUEST = "/v1/pay/request";
	public static final String URL_REQUEST = "/mbpay.do?cmd=requestCode";
	/** 准备发短信 */
	public static final String URL_CHARGE = "/v1/pay/sms";
	/** 短信结果通知 */
	//public static final String URL_PAYRESULT = "/v1/pay/sms";
	public static final String URL_PAYRESULT = "/mbpay.do?cmd=uploadPayresult";
	/** 计费成功通知 */
	public static final String URL_VERIFY = "/v1/pay/verify";
}
