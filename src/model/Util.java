/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;


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

}
