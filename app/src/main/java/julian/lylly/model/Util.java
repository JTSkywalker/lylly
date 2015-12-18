/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package julian.lylly.model;


import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.Calendar;
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

	public static String durationToHourMinuteString(Duration duration) {
		long h = duration.getStandardHours();
		long m = duration.getStandardMinutes() % 60;
		return longTo2DigitString(h) + ":" + longTo2DigitString(m);
	}

	public static String dateTimeToString(DateTime dt) {
		String y = dt.getYear() + "";
		String m = longTo2DigitString(dt.getMonthOfYear());
		String d = longTo2DigitString(dt.getDayOfMonth());
		String h = longTo2DigitString(dt.getHourOfDay());
		String mi= longTo2DigitString(dt.getMinuteOfHour());
		return y + "-" + m + "-" + d + " " + h + ":" + mi;
	}

	public static String durationToHourMinuteSecondString(Duration duration) {
		long h = duration.getStandardHours();
		long m = duration.getStandardMinutes() % 60;
		long s = duration.getStandardSeconds() % 60;
		return longTo2DigitString(h) + ":" + longTo2DigitString(m) + ":" + longTo2DigitString(s);
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

	public static String daysToDate(LocalDate date) {//TODO: weak
		int y = date.getYear();
		int m = date.getMonthOfYear();
		int d = date.getDayOfMonth();
		return y + "-" + longTo2DigitString(m) + "-" + longTo2DigitString(d);
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

	public static <T extends Comparable<? super T>> T max(T... objects) {
		T curr = null;
		for (T t : objects) {
			if (curr == null || t.compareTo(curr) > 0) {
				curr = t;
			}
		}
		return curr;
	}

	public static <T extends Comparable<? super T>> T min(T... objects) {
		T curr = null;
		for (T t : objects) {
			if (curr == null || t.compareTo(curr) < 0) {
				curr = t;
			}
		}
		return curr;
	}

	public static Duration maxDuration(Duration... durations) {
		return max(durations);
	}
}
