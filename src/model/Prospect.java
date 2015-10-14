/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;


public class Prospect {

	private final String name;
	private final long start, end;
	private final long min, max;

	public Prospect(String name, long start, long end,
			int min, int max) {
		this.name = name;
		this.start = start;
		this.end = end;
		this.min = min;
		this.max = max;
	}

	/**
	 *
	 * @return true then and only then {@code status} equals {@code ACTIVE}
	 */
	public boolean isActive() {
		long now = System.currentTimeMillis();
		return start <= now && now <= end;
	}

	public boolean isOver() {
		return System.currentTimeMillis() >= end;
	}

	public boolean isSucceeded(long timespent) {
		return isOver() && min <= timespent && timespent <= max;
	}

	public boolean tooLow(long timespent) {
		return isOver() && timespent <= min;
	}

	public boolean tooHigh(long timespent) {
		return isOver() && timespent >= max;
	}

}
