/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.util.Calendar;


public class Task extends Tree<Task> {


	public Task() {
		fragment = true;
		active = false;
		done = false;
		importance = -1;
		urgency = -1;
		mngmnt = false;
	}

	//status:
	private boolean fragment;
	private boolean active;
	private boolean done;

	private boolean mngmnt;

	// essential:
	private String descr;
	private Tag tag;
	private int importance, urgency;

	//optional:
	private Calendar expires;

	//variable
	private long actDur;

	private long starttime;

	public void start() {
		if (active) {
			throw new IllegalStateException("already active");
		}
		starttime = System.currentTimeMillis();
		active = true;
	}

	private void stop() {
		if (!active) {
			throw new IllegalStateException("already non-active");
		}
		actDur += System.currentTimeMillis() - starttime;
		active = false;
	}

	public void finish() {
		if (active) {
			stop();
		}
		done = true;
	}

	public boolean isFragment() {
		return fragment;
	}

	public boolean isActive() {
		return active;
	}

	public boolean isDone() {
		return done;
	}

	public long getActDur() {
		return actDur;
	}


	void checkCompleteness() {
		fragment
			= descr == null || tag == null || importance == -1 || urgency == -1;
	}

	public boolean isMngmnt() {
		return mngmnt;
	}

	public void setMngmnt(boolean mngmnt) {
		this.mngmnt = mngmnt;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
		checkCompleteness();
	}

	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
		checkCompleteness();
	}

	public int getImportance() {
		return importance;
	}

	public void setImportance(int importance) {
		this.importance = importance;
		checkCompleteness();
	}

	public int getUrgency() {
		return urgency;
	}

	public void setUrgency(int urgency) {
		this.urgency = urgency;
		checkCompleteness();
	}

	public Calendar getExpires() {
		return expires;
	}

	public void setExpires(Calendar expires) {
		this.expires = expires;
	}

}