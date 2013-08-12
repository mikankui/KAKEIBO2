package com.mikankui.hanasite.kakeibo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class InputDataCheck {

	private static DateFormat df = new SimpleDateFormat("yyyy/MM/dd");

	/**
	 * ??????????????????????????????
	 * 
	 * @param yyyyMMdd
	 * @return yyyy
	 */
	public static int parseYear(String yyyyMMdd) {
		String[] date = null;
		try {
			date = df.format(df.parse(yyyyMMdd)).split("/");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return Integer.parseInt(date[0]);
	}

	/**
	 * ??????????????????????????????
	 * 
	 * @param yyyyMMdd
	 * @return MM
	 */
	public static int parseMonth(String yyyyMMdd) {
		String[] date = null;
		try {
			date = df.format(df.parse(yyyyMMdd)).split("/");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return Integer.parseInt(date[1]) - 1;
	}

	/**
	 * ?????????????????????????????????
	 * 
	 * @param yyyyMMdd
	 * @return dd
	 */
	public static int parseDay(String yyyyMMdd) {
		String[] date = null;
		try {
			date = df.format(df.parse(yyyyMMdd)).split("/");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return Integer.parseInt(date[2]);
	}
}
