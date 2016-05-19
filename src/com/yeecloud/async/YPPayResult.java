package com.yeecloud.async;public class YPPayResult implements YPResult {	private String result;	private Integer errCode;	private String errMsg;	private String detailInfo;	private String id;	private int payId;		public static final int APP_PAY_SUCC_CODE = 0;	public static final int APP_PAY_CANCEL_CODE = -1;	public static final int APP_PAY_FAIL_CODE = -2;	public static final int APP_INTERNAL_PARAMS_ERR_CODE = -10;	public static final int APP_INTERNAL_NETWORK_ERR_CODE = -11;	public static final int APP_INTERNAL_THIRD_CHANNEL_ERR_CODE = -12;	public static final int APP_INTERNAL_EXCEPTION_ERR_CODE = -13;	public static final int APP_OTHER_ERR_CODE = -14;	public static final String RESULT_SUCCESS = "SUCCESS";	public static final String RESULT_CANCEL = "CANCEL";	public static final String RESULT_FAIL = "FAIL";	public static final String RESULT_UNKNOWN = "UNKNOWN";	public static final String RESULT_OTHER = "OTHER";		public YPPayResult(int payId, String result, Integer errCode, String errMsg,			String detailInfo) {		this.payId = payId;		this.result = result;		this.id = null;		this.errCode = errCode;		this.errMsg = errMsg;		this.detailInfo = detailInfo;	}	public YPPayResult(int payId, String result, Integer errCode, String errMsg,			String detailInfo, String id) {		this.payId = payId;		this.result = result;		this.errCode = errCode;		this.errMsg = errMsg;		this.detailInfo = detailInfo;		this.id = id;	}	public String getResult() {		return this.result;	}	public Integer getErrCode() {		return this.errCode;	}	public String getErrMsg() {		return this.errMsg;	}	public String getDetailInfo() {		return this.detailInfo;	}	public String getId() {		return this.id;	}	public int getPayId() {		return payId;	}	public void setPayId(int payId) {		this.payId = payId;	}}