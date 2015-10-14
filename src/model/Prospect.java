/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;


public class Prospect {

	private String name;
	private Tag tag;
	private long start, end;
	private long min, max;

	public Prospect(String name, Tag tag, long start, long end,
			int min, int max) {
		if (start >= end) {
			throw new IllegalArgumentException("start must be less than end");
		}
		if (min >= max) {
			throw new IllegalArgumentException("min must be less or equal than max");
		}
		if (name == null || tag == null) {
			throw new IllegalArgumentException("name and tag must not be null");
		}
		this.name = name;
		this.tag = tag;
		this.start = start;
		this.end = end;
		this.min = min;
		this.max = max;
	}

	/**
	 *
	 * @return true then and only then {@code status} equals {@code ACTIVE}
	 */
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

	public long getStart() {
		return start;
	}
	public long getEnd() {
		return end;
	}

	public long getMin() {
		return min;
	}

	public long getMax() {
		return max;
	}

	public void setName(String name) {
		if (isBeforeStart()) {
			if (name == null) {
				throw new IllegalArgumentException("argument must not be null");
			}
			this.name = name;
		} else {
			throw new UnsupportedOperationException("active prospect is final");
		}
	}

	public void setTag(Tag tag) {
		if (isBeforeStart()) {
			if (tag == null) {
				throw new IllegalArgumentException("argument must not be null");
			}
			this.tag = tag;
		} else {
			throw new UnsupportedOperationException("active prospect is final");
		}
	}

	public void setStartEnd(long start, long end) {
		if (isBeforeStart()) {
			if (start >= end) {
				throw new IllegalArgumentException("start must be less than end");
			}
			this.start = start;
			this.end = end;
		} else {
			throw new UnsupportedOperationException("active prospect is final");
		}
	}

	public void setMinMax(long min, long max) {
		if (isBeforeStart()) {
			if (min > max) {
				throw new IllegalArgumentException("min must be less or equal max");
			}
			this.min = min;
			this.max = max;
		} else {
			throw new UnsupportedOperationException("active prospect is final");
		}
	}

}
