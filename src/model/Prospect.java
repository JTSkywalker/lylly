/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.util.ArrayList;
import java.util.List;


public class Prospect {

	//TODO: consider moving this restriction to the organizer layer (otherwise it is always possible to delete)
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
		return Util.daysToMillis(start);
	}

	public int getEnd() {
		return end;
	}

	public long getEndInMillis() {
		return Util.daysToMillis(end);
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

	public List<Pair<Long,Long>> getBudgets(int absDay, long timespent) {
		int relDay = absDay - start;
		if (relDay >= end-start || relDay < 0) {
			throw new IllegalArgumentException("day is out of range");
		}
		if (timespent < 0) {
			throw new IllegalArgumentException("timespent < 0");
		}
		List<Integer> subl = prios.subList(relDay, prios.size());
		int sum = 0;
		for (int k : subl) {
			sum += k;
		}

		long minleft = Math.max(0, min - timespent);
		long maxleft = Math.max(0, max - timespent);
		List<Pair<Long,Long>> res = new ArrayList<>();
		for (int k : subl) {
			res.add(new Pair(k * minleft / sum,
							 k * maxleft / sum));
		}
		return res;
	}

	public Pair<Long,Long> getNextBudget(int absDay, long timespent) {
		return getBudgets(absDay, timespent).get(0);
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

	public void setPrios(List<Integer> prios) {
		if (prios.size() != end-start) {
			throw new IllegalArgumentException("prios.size() != end-stärt");
		}
		this.prios = prios;
	}

	private void checkBeforeStart() {
		if(!isBeforeStart()) {
			throw new UnsupportedOperationException("activeprospects are final");
		}
	}

}
