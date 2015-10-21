/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.util.LinkedList;
import java.util.List;


public class Prospect {

	/*
	It has to be possible to turn off and/or down/up a prospect.
	User should be motivated to get as near as possible even if the original
	bounds are not reachable any more.
	*/

	/*
	context conditions:
		start < end && min <= max
		prios.size() == end-start
	*/

	private String name;
	private Tag tag;
	private int start, end;//epoch = 1; start inclusive, end exclusive
	private long min, max;
	private List<Integer> prios;

	public Prospect(String name, Tag tag, int start, int end,
			int min, int max, List<Integer> prios) {
		if (start >= end) {
			throw new IllegalArgumentException("start must be less than end");
		}
		if (min > max) {
			throw new IllegalArgumentException("min must be less or equal than max");
		}
		if (name == null || tag == null) {
			throw new IllegalArgumentException("name and tag must not be null");
		}
		if (prios.size() != end-start) {
			throw new IllegalArgumentException("prios.length != end-start");
		}
		this.name = name;
		this.tag = tag;
		this.start = start;
		this.end = end;
		this.min = min;
		this.max = max;
		this.prios = prios;
	}

	public boolean isActive() {
		long now = System.currentTimeMillis();
		return start <= now && now <= end;
	}

	public boolean isBeforeStart() {
		return System.currentTimeMillis() <= start;
	}

	public boolean isOver() {
		return System.currentTimeMillis() >= end;
	}

	public boolean isSucceeded(long timespent) {
		return isOver() && min <= timespent && timespent <= max;
	}

	public boolean tooLow(long timespent) {
		return isOver() && timespent <= min;
	}

	public boolean tooHigh(long timespent) {
		return isOver() && timespent >= max;
	}

	public String getName() {
		return name;
	}

	public Tag getTag() {
		return tag;
	}

	public int getStart() {
		return start;
	}

	public long getStartInMillis() {
		return start*1000*60*60*24 + OrganizerImpl.START_OF_DAY;
	}

	public int getEnd() {
		return end;
	}

	public long getEndInMillis() {
		return end	*1000*60*60*24 + OrganizerImpl.START_OF_DAY;
	}

	public long getMin() {
		return min;
	}

	public long getMax() {
		return max;
	}

	public List<Integer> getPrios() {
		return prios;
	}

	//TODO: maybe generalize! and think about future budgets
	public Pair<Long,Long> getBudget(List<Integer> timespent) {
		throw new UnsupportedOperationException();//TODO: implement
	}

	public void setName(String name) {
		checkBeforeStart();
		if (name == null) {
			throw new IllegalArgumentException("argument must not be null");
		}
		this.name = name;
	}

	public void setTag(Tag tag) {
		checkBeforeStart();
		if (tag == null) {
			throw new IllegalArgumentException("argument must not be null");
		}
		this.tag = tag;
	}

	public void setStartEnd(int start, int end, List<Integer> prios) {
		checkBeforeStart();
		if (start >= end) {
			throw new IllegalArgumentException("start must be less than end");
		}
		if (prios.size() != end-start) {
			throw new IllegalArgumentException("prios.size() != end-start");
		}
		this.start = start;
		this.end = end;
		this.prios = prios;
	}

	public void setMinMax(long min, long max) {
		checkBeforeStart();
		if (min > max) {
			throw new IllegalArgumentException("min must be less or equal max");
		}
		this.min = min;
		this.max = max;
	}

	private void checkBeforeStart() {
		if(!isBeforeStart()) {
			throw new UnsupportedOperationException("activeprospects are final");
		}
	}

}
