/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.util.Calendar;


public class Appointment extends Task {

	private Calendar startDate;

	public Appointment(String descr) {
		super(descr);
	}

	public Appointment(String descr, Interest interest, int importance, int urgency,
			int minDur, int maxDur, Calendar startDate) {
		super(descr, interest, importance, urgency, minDur, maxDur);
		this.startDate = startDate;
	}

	public Calendar getStartDate() {
		return startDate;
	}

	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}

}
