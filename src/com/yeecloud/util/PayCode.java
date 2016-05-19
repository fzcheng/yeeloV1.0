package com.yeecloud.util;

public class PayCode {
	// 1.1
	private static int[] k = new int[] { 22, 29, 39, 41, 46, 70, 9, 37, 82, 78,
			82, 26, 43, 31, 9, 12, 93, 72, 74, 12, 20, 92, 33, 8, 17, 30, 77,
			85, 67, 65, 75, 23, 82, 61, 1, 83, 7, 24, 84, 79, 10, 71, 71, 65,
			11, 54, 95, 37, 77, 14, 81, 10, 60, 58, 95, 61, 88, 92, 71, 78, 35,
			91, 9, 52, 45, 59, 41, 19, 66, 25, 0, 78, 68, 68, 6, 21, 97, 33,
			33, 64, 12, 78, 41, 7, 21, 36, 38, 20, 82, 16, 7, 47, 7, 44, 58,
			16, 40, 43, 55, 35, 48, 63, 44, 31, 32, 83, 25, 18, 74, 83, 62, 40,
			40, 68, 18, 79, 10, 34, 31, 23, 77, 78, 24, 94, 24, 13, 0, 45, 88,
			9, 1, 59, 56, 95, 85, 0, 70, 84, 15, 60, 55, 49, 60, 15, 68, 18,
			59, 62, 4, 60, 91, 29, 57, 9, 99, 64, 46, 85, 17, 28, 78, 27, 20,
			85, 71, 41, 4, 5, 64, 83, 55, 35, 27, 11, 31, 16, 73, 96, 22, 92,
			71, 9, 89, 48, 99, 99, 9, 63, 64, 39, 89, 26, 42, 43, 70, 29, 65,
			47, 24, 11, };

	public static String a(String str) {
		if (str == null) {
			return "";
		}

		StringBuilder sb = new StringBuilder();
		sb.append("s");
		char[] result = str.toCharArray();
		try {
			for (int i = 0; i < result.length; i++) {
				char c = result[i];
				char cc = (char) (c + k[i % k.length]);
				sb.append(cc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		sb.append("e");
		return sb.toString();
	}

	public static String e(String code) {
		if (code == null) {
			return "";
		}
		if (code.startsWith("s") && code.endsWith("e")) {
			
			StringBuilder sb = new StringBuilder(code);
			sb.deleteCharAt(0);
			sb.deleteCharAt(sb.length() - 1);
			char[] result = sb.toString().toCharArray();
			sb = new StringBuilder();
			try {
				for (int i = 0; i < result.length; i++) {
					char c = result[i];
					char cc = (char) (c - k[i % k.length]);
					sb.append(cc);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			return sb.toString();
		}
		return "";
	}
}
