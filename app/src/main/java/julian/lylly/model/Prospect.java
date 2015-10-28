/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package julian.lylly.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Prospect implements Serializable {

	/*
	It has to be possible to turn off and/or down/up a prospect.
	User should be motivated to get as near as possible even if the original
	bounds are not reachable any more.
	*/

	/*
	context conditions:
		start < end && min <= max
		weights.size() == end-start
	*/

	private String name;
	private Tag tag;
	private int start, end;//epoch = 0; start inclusive, end exclusive
	private long min, max;
	private List<Integer> weights;

	public Prospect(String name, Tag tag, int start, int end,
			long min, long max, List<Integer> weights) {
		if (start >= end) {
			throw new IllegalArgumentException("start must be less than end");
		}
		if (min > max) {
			throw new IllegalArgumentException("min must be less or equal than max");
		}
		if (name == null || tag == null) {
			throw new IllegalArgumentException("name and tag must not be null");
		}
		if (weights.size() != end-start) {
			throw new IllegalArgumentException("weights.length != end-start");
		}
		this.name = name;
		this.tag = tag;
		this.start = start;
		this.end = end;
		this.min = min;
		this.max = max;
		this.weights = weights;
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

	public List<Integer> getWeights() {
		return weights;
	}

	public List<Pair<Long,Long>> getBudgets(int absDay, long timespent) {
		int relDay = absDay - start;
		if (relDay >= end-start || relDay < 0) {
			throw new IllegalArgumentException("day is out of range");
		}
		if (timespent < 0) {
			throw new IllegalArgumentException("timespent < 0");
		}
		List<Integer> subl = weights.subList(relDay, weights.size());
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
			throw new IllegalArgumentException("weights.size() != end-start");
		}
		this.start = start;
		this.end = end;
		this.weights = prios;
	}

	public void setMinMax(long min, long max) {
		checkBeforeStart();
		if (min > max) {
			throw new IllegalArgumentException("min must be less or equal max");
		}
		this.min = min;
		this.max = max;
	}

	public void setWeights(List<Integer> weights) {
		if (weights.size() != end-start) {
			throw new IllegalArgumentException("weights.size() != end-stärt");
		}
		this.weights = weights;
	}

	private void checkBeforeStart() {
		if(!isBeforeStart()) {
			throw new UnsupportedOperationException("active prospects are final");
		}
	}

}
