/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;


public class Util {

	public static int START_OF_DAY = 0;

	public static int millisToDay(long millis) {
		return (int) ((millis - START_OF_DAY) / 1000 / 60 / 60 / 24);
	}

	public static long daysToMillis(int days) {
		return days * 24 * 60 * 60 * 1000 + START_OF_DAY;
	}

}
