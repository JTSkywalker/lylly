/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.util.LinkedList;
import java.util.List;


public class Task extends Tree<Task> {

	/*
	context conditions:

		FRAGMENT <=> at least one of the essential properties is unset

		RECEIVABLE => actDur = 0

	*/

	private PreData  preData;
	private VarData  varData;
	private PostData postData;


	private TaskStatus status;

	/*
	essential properties:
	*/
	private String descr;
	private Interest interest;
	private int importance, urgency;
	private int minDur, maxDur;
	private List<String> delHierachy;

	/*
	always set:
	*/
	private boolean mngmnt;

	/*

	*/

	private int actDur, efficiency;

	public Task(String descr) {
		this.descr = descr;
		this.status = TaskStatus.FRAGMENT;
	}

	public Task(String descr, Interest interest, int importance, int urgency,
			int minDur, int maxDur) {
		this.descr = descr;
		this.interest = interest;
		this.importance = importance;
		this.urgency = urgency;
		this.minDur = minDur;
		this.maxDur = maxDur;
		this.delHierachy = new LinkedList<>();
		this.mngmnt = false;
	}

	public Task(String descr, Interest interest, int importance, int urgency,
			int minDur, int maxDur, int actDur, TaskStatus status,
			List<String> delHierachy, boolean mngmnt) {
		this.descr = descr;
		this.interest = interest;
		this.importance = importance;
		this.urgency = urgency;
		this.minDur = minDur;
		this.maxDur = maxDur;
		this.actDur = actDur;
		this.status = status;
		this.delHierachy = delHierachy;
		this.mngmnt = mngmnt;
	}

	public String getDescr() {
		return descr;
	}

	public Interest getInterest() {
		return interest;
	}

	public int getImportance() {
		return importance;
	}

	public int getUrgency() {
		return urgency;
	}

	public int getMinDur() {
		return minDur;
	}

	public int getMaxDur() {
		return maxDur;
	}

	public int getActDur() {
		return actDur;
	}

	public TaskStatus getStatus() {
		return status;
	}

	public List<String> getDelHierachy() {
		return delHierachy;
	}

	public boolean isMngmnt() {
		return mngmnt;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public void setInterest(Interest interest) {
		this.interest = interest;
	}

	public void setImportance(int importance) {
		this.importance = importance;
	}

	public void setUrgency(int urgency) {
		this.urgency = urgency;
	}

	public void setMinDur(int minDur) {
		this.minDur = minDur;
	}

	public void setMaxDur(int maxDur) {
		this.maxDur = maxDur;
	}

	public void setActDur(int actDur) {
		this.actDur = actDur;
	}

	public void setStatus(TaskStatus status) {
		this.status = status;
	}

	public void setDelHierachy(List<String> delHierachy) {
		this.delHierachy = delHierachy;
	}

	public void setMngmnt(boolean mngmnt) {
		this.mngmnt = mngmnt;
	}


}
