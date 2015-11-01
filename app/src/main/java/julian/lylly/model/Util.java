/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package julian.lylly.model;


import android.widget.EditText;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class Util {

	public static final int START_OF_DAY = 0;
	public static final int MILLIS_PER_DAY = 86400000;

	public static int millisToDay(long millis) {
		return (int) ((millis - START_OF_DAY) / MILLIS_PER_DAY);
	}

	public static long daysToMillis(int days) {
		return days * MILLIS_PER_DAY + START_OF_DAY;
	}

	public static long cutMillis(long millis) {
		return millis % MILLIS_PER_DAY;
	}

	public static String millisToHourMinuteString(long millis) {
		//calc hours
		long h = millis/1000/60/60;
		//calc minutes
		long m = millis%(1000*60*60)/1000/60;

		return longTo2DigitString(h) + ":" + longTo2DigitString(m);
	}

	public static List<Integer> calcWeights(String s) {
		List<Integer> result = new ArrayList<>();
		for (char c : s.toCharArray()) {
			if(Character.isDigit(c)) {
				result.add(Integer.parseInt("" + c));
			} else {
				throw new IllegalArgumentException("c is no digit");
			}
		}
		return result;
	}

	public static String daysToDate(int days) {
		int month = getMonthFromDays(days);
		int day = getDayFromDays(days);
		return longTo2DigitString(month) + "-" + longTo2DigitString(day);
	}

	public static String longTo2DigitString(long src) {
		if (src < 10) {
			return "0" + src;
		} else {
			return Long.toString(src);
		}
	}

	public static int getYearFromDays(int days) {
		Calendar date = new GregorianCalendar();
		date.setTimeInMillis(daysToMillis(days));
		return date.get(Calendar.YEAR);
	}

	public static int getMonthFromDays(int days) {
		Calendar date = new GregorianCalendar();
		date.setTimeInMillis(daysToMillis(days));
		return date.get(Calendar.MONTH);
	}

	public static int getDayFromDays(int days) {
		Calendar date = new GregorianCalendar();
		date.setTimeInMillis(daysToMillis(days));
		return date.get(Calendar.DATE);
	}

	public static int getHourFromMillis(long millis) {
		Calendar date = new GregorianCalendar();
		date.setTimeInMillis(millis);
		return date.get(Calendar.HOUR_OF_DAY);
	}

	public static int getMinuteFromMillis(long millis) {
		Calendar date = new GregorianCalendar();
		date.setTimeInMillis(millis);
		return date.get(Calendar.MINUTE);
	}

	public static String intListToString(List<Integer> weights) {
		String res = "";
		for (int i : weights) {
			res = res + i;
		}
		return res;
	}
}
