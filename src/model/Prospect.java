/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.util.GregorianCalendar;


public class Prospect {

	private final String descr;
	private final GregorianCalendar start, end;
	private final int expEffort;

	public Prospect(String descr, GregorianCalendar start, GregorianCalendar end, int expEffort) {
		this.descr = descr;
		this.start = start;
		this.end = end;
		this.expEffort = expEffort;
	}

	public GregorianCalendar getStart() {
		return start;
	}

	public GregorianCalendar getEnd() {
		return end;
	}

	public int getExpEffort() {
		return expEffort;
	}

}
