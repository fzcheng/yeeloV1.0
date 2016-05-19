/*
 * Copyright (C) 2012 Guangzhou CooguoSoft Co.,Ltd.
 * cn.douwan.sdk.entityJsonParseInterface.java
 */
package com.yeecloud.util;

import java.util.Map;

import org.json.JSONObject;

/** 
 * @Description: TODO(添加描述) 
 * @author Jerry   @date 2012-9-5 上午09:46:44 
 * @version 1.0 
 * @JDK  1.6
 */

public interface JsonParseInterface {
	JSONObject buildJson();
	void parseJson(JSONObject json);
	String getShortName();
	Map<String, Object> buildMap();
}
