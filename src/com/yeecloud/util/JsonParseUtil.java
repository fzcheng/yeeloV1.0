package com.yeecloud.util;

import java.lang.reflect.Array;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class JsonParseUtil {

	public static JsonParseInterface parseJSonObjectNotShortName(Class<?> clz,
			String jsonString) {
		if (jsonString == null)
			return null;
		try {
			JSONObject jo = new JSONObject(jsonString);
			JsonParseInterface jInterface = (JsonParseInterface) clz
					.newInstance();
			jInterface.parseJson(jo);

			return jInterface;
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static JsonParseInterface[] parseJSonArrayNotShortName(Class<?> clz,
			String jsonArray) {
		if (jsonArray == null)
			return null;
		JsonParseInterface ji = null;
		try {
			JSONArray ja = new JSONArray(jsonArray);

			JsonParseInterface[] interfaces = (JsonParseInterface[]) Array
					.newInstance(clz, ja.length());
			for (int i = 0; i < ja.length(); i++) {
				ji = (JsonParseInterface) clz.newInstance();
				ji.parseJson(ja.getJSONObject(i));
				interfaces[i] = ji;
			}
			return interfaces;

		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
		return null;
	}
	
//	public static JsonParseInterface[] parseJSonArrayNotShortName(Class<?> clz,
//			String jsonArray, String shortName) {
//		if (jsonArray == null)
//			return null;
//		JsonParseInterface ji = null;
//		try {
//			JSONObject jo = new JSONObject(jsonArray);
//			
//			JSONArray ja = jo.getJSONArray(shortName);
//
//			JsonParseInterface[] interfaces = (JsonParseInterface[]) Array
//					.newInstance(clz, ja.length());
//			for (int i = 0; i < ja.length(); i++) {
//				ji = (JsonParseInterface) clz.newInstance();
//				ji.parseJson(ja.getJSONObject(i));
//				interfaces[i] = ji;
//			}
//			return interfaces;
//
//		} catch (JSONException e) {
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			e.printStackTrace();
//		} catch (InstantiationException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}

	public static JsonParseInterface parseJSonObject(Class<?> clz,
			String jsonString) {
		if (jsonString == null)
			return null;
		try {
			JSONObject jo = new JSONObject(jsonString);
			JsonParseInterface jInterface = (JsonParseInterface) clz
					.newInstance();
			if (!jo.isNull(jInterface.getShortName())) {
				jInterface
						.parseJson(jo.getJSONObject(jInterface.getShortName()));
				return jInterface;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static JsonParseInterface[] parseJSonArray(Class<?> clz,
			String jsonString) {
		if (jsonString == null)
			return null;
		try {
			JSONObject jo = new JSONObject(jsonString);
			JsonParseInterface ji = (JsonParseInterface) clz.newInstance();
			if (!jo.isNull(ji.getShortName())) {
				JSONArray ja = jo.getJSONArray(ji.getShortName());
				if (ja != null) {
					JsonParseInterface[] interfaces = (JsonParseInterface[]) Array
							.newInstance(clz, ja.length());
					for (int i = 0; i < ja.length(); i++) {
						ji = (JsonParseInterface) clz.newInstance();
						ji.parseJson(ja.getJSONObject(i));
						interfaces[i] = ji;
					}
					return interfaces;
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
		return null;
	}

}
