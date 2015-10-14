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

	public Prospect(String name, GregorianCalendar start, GregorianCalendar end,
			int minEffort, int maxEffort) {
		this.name = name;
		this.start = start;
		this.end = end;
		this.minEffort = minEffort;
		this.maxEffort = maxEffort;
		this.status = ACTIVE;
	}

	/**
	 *
	 * @return true then and only then {@code status} equals {@code ACTIVE}
	 */
	public boolean isActive() {
		switch (status) {
			case ACTIVE:
				return true;
			default:
				return false;
		}
	}

	public boolean isSucceeded() {
		switch (status) {
			case SUCCEEDED:
				return true;
			default:
				return false;
		}
	}

	public boolean isFailed() {
		switch (status) {
			case FAILLOW:
			case FAILHIGH:
				return true;
			default:
				return false;
		}
	}
	/**
	 * This function compares the give time spent with the borders of the
	 * prospect. When {@code status != ACTIVE}, an IllegalStateException is
	 * thrown.
	 * @param result
	 */
	public void timeOver(int result) {
		if (status != ACTIVE) {
			throw new IllegalStateException("prospect status is"
													+ status);
		}
		if (result >= minEffort) {
			if (result <= maxEffort) {
				status = SUCCEEDED;
			} else {
				status = FAILHIGH;
			}
		} else {
			status = FAILLOW;
		}
	}
}
