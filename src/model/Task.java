/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.util.List;


public class Task {

	/*
	context condition: fragment <==>      descr == null ||   tag == null
								  || importance == -1 || urgency == -1
	*/

	//status:
	private boolean fragment = true;
	private boolean active   = false;
	private boolean done     = false;


	// essential:
	private String descr;
	private Tag tag;
	private int importance = -1,
			    urgency    = -1;

	//optional:
	private long startDate;
	private long expieryDate;


	//variable
	private List<Long> starttimes;
	private List<Long> stoptimes;


	/*
	status operators:
	*/

	public void start() {
		if (active) {
			throw new IllegalStateException("already active");
		}
		starttimes.add(System.currentTimeMillis());
		active = true;
	}

	public void pause() {
		if (!active) {
			throw new IllegalStateException("already non-active");
		}
		stoptimes.add(System.currentTimeMillis());
		active = false;
	}

	public void finish() {
		if (active) {
			pause();
		}
		done = true;
	}



	/*
	status getter:
	*/

	public boolean isFragment() {
		return fragment;

	}

	public boolean isActive() {
		return active;
	}

	public boolean isDone() {
		return done;
	}



	/*
	get & set properties:
	*/

	void checkCompleteness() {
		fragment
			= descr == null || tag == null || importance == -1 || urgency == -1;
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

	public long getExpieryDate() {
		return expieryDate;
	}

	public void setExpieryDate(long expieryDate) {
		this.expieryDate = expieryDate;
	}

	public long getStartDate() {
		return startDate;
	}

	public void setStartDate(long startDate) {
		this.startDate = startDate;
	}



	/*
	getter for timetracking:
	*/

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

	public List<Long> getStarttimes() {
		return starttimes;
	}

	public List<Long> getStoptimes() {
		return stoptimes;
	}

	public long getTimeSpentInInterval(long iStart, long iEnd) {
		long sum = 0;
		for (int i=0; i <= stoptimes.size(); i++) {
			sum +=  Math.max(0,  Math.min(iEnd,   stoptimes.get(i))
								-Math.max(iStart, starttimes.get(i)));
		}
		if (starttimes.size() > stoptimes.size()) {
			sum += Math.max(0, Math.min(iEnd,   System.currentTimeMillis())
							  -Math.max(iStart, starttimes.get(starttimes.size()-1)));
		}
		return sum;
	}

}