/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package julian.lylly.model;


import java.io.StringReader;
import java.util.ArrayList;
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
		String h = Long.toString(millis/1000/60/60);
		//calc minutes
		String m = Long.toString(millis%(1000*60*60)/1000/60);

		return h + ":" + m;
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
}
