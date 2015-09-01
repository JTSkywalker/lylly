/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.util.Calendar;
import java.util.Collection;
import java.util.List;


public class Task extends Tree<Task> {

	/*
	context conditions:

		FRAGMENT <=> at least one of the essential properties is unset

		RECEIVABLE => actDur = 0

	*/

	private TaskStatus status;
	private boolean mngmnt;

	// essential:
	private String descr;
	private Interest interest;
	private int minDur, maxDur;
	private int importance, urgency;
	private Collection<Context> contexts;

	//optional:
	private Calendar expires;
	private Calendar waitOnTime;
	private List<Task> waitOnTasks;
	//there will be no field about reacurring tasks in here!

	//variable
	private List<String> delHierachy;
	private int actDur, efficiency;

}
