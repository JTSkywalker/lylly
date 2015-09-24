/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.util.GregorianCalendar;
import static model.ProspectStatus.*;


public class Prospect {

	private final String name;
	private final GregorianCalendar start, end;
	private final int minEffort, maxEffort;
	private ProspectStatus status;

	public Prospect(String name, GregorianCalendar start, GregorianCalendar end, int minEffort, int maxEffort) {
		this.name = name;
		this.start = start;
		this.end = end;
		this.minEffort = minEffort;
		this.maxEffort = maxEffort;
		this.status = ACTIVE;
	}

}
