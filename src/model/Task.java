/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.util.Calendar;
import static model.TaskStatus.*;


public class Task extends Tree<Task> {

	/*
	context conditions:

		FRAGMENT <=> at least one of the essential properties is unset

		RECEIVABLE => actDur = 0

	*/

	public Task() {
		status = FRAGMENT;
		importance = -1;
		urgency = -1;
	}


	private TaskStatus status;
	private boolean mngmnt;

	// essential:
	private String descr;
	private Tag tag;
	private int importance, urgency;

	//optional:
	private Calendar expires;

	//variable
	private int actDur;

	void checkCompleteness() {
		if (descr == null || tag == null || importance == -1 || urgency == -1) {
			status = FRAGMENT;
		} else {
			if (status == FRAGMENT) {
				status = RECEIVABLE;//TODO: add checking of expiry, etc
			}
		}
	}

	boolean hasTag(Tag tag) {
		return this.tag == tag;
	}

}
