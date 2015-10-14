/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.util.Calendar;
import java.util.List;


public class Task extends Tree<Task> {

	/*
	context condition: fragment <==>      descr == null ||   tag == null
								  || importance == -1 || urgency == -1
	*/

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
	private Calendar expieryDate;
	private Calendar startDate;

	//variable
	private List<Long> starttimes;
	private List<Long> stoptimes;


	public void start() {
		if (active) {
			throw new IllegalStateException("already active");
		}
		starttimes.add(System.currentTimeMillis());
		active = true;
	}

	public void stop() {
		if (!active) {
			throw new IllegalStateException("already non-active");
		}
		stoptimes.add(System.currentTimeMillis());
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

	public long evalDurationSum() {
		int n = starttimes.size();
		int m = stoptimes.size();
		assert(n==m || n==m+1);
		long sum = 0;
		for (int i=0; i < m; i++) {
			sum += stoptimes.get(i) - starttimes.get(i);
		}
		if (n > m) {
			sum += System.currentTimeMillis() - starttimes.get(n);
		}
		return sum;
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

	public List<Long> getStarttimes() {
		return starttimes;
	}

	public List<Long> getStoptimes() {
		return stoptimes;
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

	public Calendar getExpieryDate() {
		return expieryDate;
	}

	public void setExpieryDate(Calendar expieryDate) {
		this.expieryDate = expieryDate;
	}

	public Calendar getStartDate() {
		return startDate;
	}

	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}

}