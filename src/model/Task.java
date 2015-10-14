/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.util.Calendar;


public class Task extends Tree<Task> {

	/*
	context conditions:

		FRAGMENT <=> at least one of the essential properties is unset

		RECEIVABLE => actDur = 0

	*/

	public Task() {
		importance = -1;
		urgency = -1;
	}

	//status:
	private boolean fragment;
	private Activity activitiy;
	private boolean done;

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
		fragment
			= descr == null || tag == null || importance == -1 || urgency == -1;
	}

	boolean hasTag(Tag tag) {
		return this.tag == tag;
	}

}

enum Activity {
	BEFORE,
	ACTIVE,
	PAUSED,
	STOPPED;
}
